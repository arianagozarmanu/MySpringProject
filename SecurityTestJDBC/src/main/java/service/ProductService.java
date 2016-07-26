package service;

import java.util.List;

import model.Product;
import model.User;

public interface ProductService {
	
	public void addProduct(Product product, User user);
	public void deleteProductById(Product product, User user);
	public void updateProduct(Product product, User user) ;
	public Product findProductById(int id);
	public List<Product> findProductsByUserId(int id);
	public List<Product> findAllProducts();
	public boolean productIdIsUsed(Product product);
}
