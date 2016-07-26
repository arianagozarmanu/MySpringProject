package controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.*;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.*;
import model.*;
import service.*;

@Controller
public class HomeController {

	@Autowired
	private ProductDao productDaoImpl;

	@Autowired
	private UserDao userDaoImpl;
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	
	
	// registration
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration( Map<String, Object> m) {
		ModelAndView model = new ModelAndView();
		User userForm = new User();
		m.put("userForm", userForm);
		model.setViewName("register");
		return model;
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView doRegistration(@ModelAttribute("SpringWeb") User user, BindingResult result,
			RedirectAttributes redirectAttrs) {
		ModelAndView model = new ModelAndView();
		
		if(result.getErrorCount() == 0 && usernameIsValid(user.getUsername()) && emailIsValid(user.getEmail()) && user.getAge()>0){
			Set<Integer> usersIds = getUsersId(userDaoImpl.findAll());
			System.out.println(usersIds.toArray()[usersIds.size()-1]);
			user.setEnabled(true);
			user.setIduders((int)usersIds.toArray()[usersIds.size()-1]+1);
			user.setLastOperationDate(getCurrentDate());
			userDaoImpl.add(user);
			userDaoImpl.addRole(user.getUsername(),"ROLE_USER");
			model.addObject("successRegisterMessage", "Your username and password were successfully registered!");
			model.setViewName("login");
		} else {
			if(!usernameIsValid(user.getUsername()))
				redirectAttrs.addFlashAttribute("errorRegisterMessage", "This username was already taken. Please try another one!");
			else redirectAttrs.addFlashAttribute("errorRegisterMessage", "Introduce a valid email or age!");
			model.setViewName("redirect:/registration");
		}

		return model;
	}
	
	// update peroduct requests
	@RequestMapping(value = "/welcome/update/{id}", method = RequestMethod.GET)
	public ModelAndView updateProduct(@PathVariable("id") int id, Map<String, Object> m) {

		ModelAndView model = new ModelAndView();
		Product prod = productDaoImpl.findById(id);
		model.addObject("product", prod);

		Product productForm = new Product();
		m.put("productForm", productForm);
		model.setViewName("updateProduct");

		return model;
	}

	@RequestMapping(value = "/welcome/update/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(@ModelAttribute("SpringWeb") Product product, BindingResult result,
			RedirectAttributes redirectAttrs) {
		//tranzactii stuff
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("updateNewProductAndDateAction");
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		
		ModelAndView model = new ModelAndView();
		if (result.getErrorCount() > 0 || product.getPrice() < 0) {
			redirectAttrs.addFlashAttribute("invalidData", "Please introduce a valid price!");
			model.setViewName("redirect:/welcome/update/" + product.getIdproduct());
		} else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName();
			User user = userDaoImpl.findByName(name);
			java.util.Date date = getCurrentDate();
			try {
				userServiceImpl.insertLastActionDate(date, user.getIduders());
				//userDaoImpl.insertLastActionDate(date, user.getIduders());
				productDaoImpl.update(product);
				//productDaoImpl.updateProductTransaction(product, date, user.getIduders());
				redirectAttrs.addFlashAttribute("successAddingProduct", "Your product was successfully updated!");
				platformTransactionManager.commit(status);
			} catch (Exception e) {
				System.out.println(e);
				redirectAttrs.addFlashAttribute("errorAddingProduct", "Your product was not updated, some errors occurred!");
				platformTransactionManager.rollback(status);
			}
			
			model.setViewName("redirect:/welcome");
		}

		return model;
	}
	// ----------------------------------------------------------------
	// Delete requests
	@RequestMapping(value = "/welcome/delete", method = RequestMethod.POST)
	public ModelAndView deleteProduct(@RequestParam("id") int id, RedirectAttributes redirectAttrs) {
		
		//tranzactii stuff
		TransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		User user = userDaoImpl.findByName(name);
		Product product = productDaoImpl.findById(id);

		java.util.Date date = getCurrentDate();

		try {
			userDaoImpl.insertLastActionDate(date, user.getIduders());
			productDaoImpl.deleteById(product);
			redirectAttrs.addFlashAttribute("successAddingProduct", "Your product was successfully deleted!");
			platformTransactionManager.commit(status);
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("errorAddingProduct", "Your product was not deleted, some errors occurred!");
			platformTransactionManager.rollback(status);
		}

		model.setViewName("redirect:/welcome");

		return model;
	}
	// ---------------------------------------------------------------------

	// Add product requests
	@RequestMapping(value = "/welcome/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(Map<String, Object> m) {

		List<User> users = userDaoImpl.findAll();
		Set<Integer> userids = getUsersId(users);
		ModelAndView model = new ModelAndView();
		Product productForm = new Product();
		m.put("productForm", productForm);
		model.addObject("userids", userids);
		model.setViewName("addProduct");

		return model;
	}

	@RequestMapping(value = "/welcome/register", method = RequestMethod.POST)
	public ModelAndView registerProduct(SecurityContextHolderAwareRequestWrapper request,
			@ModelAttribute("productForm") Product product, BindingResult result, RedirectAttributes redirectAttrs) {
		//tranzactii stuff
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("addNewProductAndDateAction");
		TransactionStatus status = platformTransactionManager.getTransaction(defaultTransactionDefinition);
		
		ModelAndView model = new ModelAndView();

		if (result.getErrorCount() > 0 || product.getPrice() < 0 || product.getIdproduct() < 0) {
			model.setViewName("redirect:/welcome/addProduct");
			redirectAttrs.addFlashAttribute("invalidData", "Please introduce a valid price or a valid product ID!");
		} else {
			
			List<Product> products;
			boolean exists=productIdIsUsed(product);
			
			if (exists)
				//model.addObject("errorProductExists", "A product whith the same ID already exists!");
				redirectAttrs.addFlashAttribute("errorProductExists", "A product whith the same ID already exists!");
			else {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String name = authentication.getName();
				User user = userDaoImpl.findByName(name);
				java.util.Date date = getCurrentDate();		
				try {
					
					userDaoImpl.insertLastActionDate(date, user.getIduders());
					productDaoImpl.add(product);					
					//productDaoImpl.addProductTransaction(product, date, user.getIduders());
					//model.addObject("successAddingProduct", "Your product was successfully added!"); /*****/
					redirectAttrs.addFlashAttribute("successAddingProduct", "Your product was successfully added!"); /****/
					platformTransactionManager.commit(status);
				} catch (Exception e) {
					//model.addObject("errorAddingProduct", "Your product was not inserted, some errors occurred!");
					redirectAttrs.addFlashAttribute("errorAddingProduct", "Your product was not inserted, some errors occurred!"); /***/
					platformTransactionManager.rollback(status);
				}
				
			}
			String authorities = getAuthorities(request);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName();
			User user = userDaoImpl.findByName(name);

			if (authorities.contains("Administrator")) {
				products = productDaoImpl.findAll();
			} else {
				products = productDaoImpl.findByUserId(user.getIduders());
			}

//			model.addObject("user", user);
//			model.addObject("products", products);
//			model.addObject("authorities", authorities);
//			model.setViewName("welcome");
			redirectAttrs.addFlashAttribute("user", user);
			redirectAttrs.addFlashAttribute("products", products);
			redirectAttrs.addFlashAttribute("authorities", authorities);
			model.setViewName("redirect:/welcome");


		}
		return model;
	}
	// -----------------------------------------------------------------


	// login and home page request
	@RequestMapping(value = { "/", "/hello", "/index" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");

		return model;

	}

	@RequestMapping(value = "/showLogin", method = RequestMethod.GET)
	public String login(Model m) {
		return "login";
	}

	@RequestMapping(value = "/welcome**", method = RequestMethod.GET)
	public ModelAndView adminPage(SecurityContextHolderAwareRequestWrapper request) {

		List<Product> products = null;

		String authorities = getAuthorities(request);
		ModelAndView model = new ModelAndView();

		// preluare id user curent
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		User user = userDaoImpl.findByName(name);

		if (authorities.contains("Administrator")) {
			products = productDaoImpl.findAll();
		} else {
			products = productDaoImpl.findByUserId(user.getIduders());
		}

		model.addObject("user", user);
		model.addObject("products", products);
		model.addObject("authorities", authorities);
		model.setViewName("welcome");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	// for 403 access denied page - user guest
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("nopermis", "Hi " + user.getName() + ", you do not have permission to access this page!");
		} else {
			model.addObject("nopermis", "You do not have permission to access this page!");
		}

		model.setViewName("login");
		return model;

	}
	// -----------------------------------------------------------------------------

	// aditional methods
	// realizare string cu autoritatile user-ului
	private String getAuthorities(SecurityContextHolderAwareRequestWrapper request) {
		String authorities = "";
		if (request.isUserInRole("ROLE_ADMIN")) {
			authorities += " Administrator ";
		}
		if (request.isUserInRole("ROLE_USER") && request.isUserInRole("ROLE_ADMIN")) {
			authorities += "and User ";
		} else
			authorities += " User ";

		return authorities;
	}

	private Set<Integer> getUsersId(List<User> users) {		
		Set<Integer> result=users.stream().map(User::getIduders).collect(Collectors.toSet());		
		return result;
	}

	private boolean productIdIsUsed(Product product){
		try{
			productDaoImpl.findById(product.getIdproduct());
			return true;
		}catch(EmptyResultDataAccessException e){
			return false;
		}
	}

	private static java.util.Date getCurrentDate() {
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	private boolean usernameIsValid(String username){
		try{
			userDaoImpl.findByName(username);
			return false;
		} catch(EmptyResultDataAccessException e){
			return true;
		}
	}
	
	private boolean emailIsValid(String email){
		if(email.contains("@") && email.contains(".")){
			return true;
		} else return false;
	}
	
}
