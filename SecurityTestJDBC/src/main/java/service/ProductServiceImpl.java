package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.*;
import model.*;
import util.*;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductDao productDaoImpl;
	
	@Autowired 
	UserDao userDaoImpl;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addProduct(Product product, User user) {
		userDaoImpl.insertLastActionDate(serviceUtils.getCurrentDate(), user.getIduders());
		productDaoImpl.add(product);		
	}

	@Override
	@Transactional
	public void deleteProductById(Product product, User user) {
		// TODO Auto-generated method stub
		userDaoImpl.insertLastActionDate(serviceUtils.getCurrentDate(), user.getIduders());
		productDaoImpl.deleteById(product);
	}

	@Override
	@Transactional
	public void updateProduct(Product product, User user) {
		// TODO Auto-generated method stub
		userDaoImpl.insertLastActionDate(serviceUtils.getCurrentDate(), user.getIduders());
		productDaoImpl.update(product);		
	}

	@Override
	public Product findProductById(int id) {
		// TODO Auto-generated method stub
		return  productDaoImpl.findById(id);
	}

	@Override
	public List<Product> findProductsByUserId(int id) {
		// TODO Auto-generated method stub
		return productDaoImpl.findByUserId(id);
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return productDaoImpl.findAll();
	}

	@Override
	public boolean productIdIsUsed(Product product) {
		try{
			productDaoImpl.findById(product.getIdproduct());
			return true;
		}catch(EmptyResultDataAccessException e){
			return false;
		}
	}

}
