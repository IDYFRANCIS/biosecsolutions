package com.francis.biosectest.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.francis.biosectest.model.Product;



@Repository
public interface ProductRepository extends CrudRepository<Product, String>{
	
	Product findByProductId(String productId);
		
	Product findByProductName(String productName);
	
	Product findByPrice(double price);
	
	Product findByProductAvailablity(boolean available);
	
	Collection<Product> findByUsers_UsersId(long usersId);
	
	Collection<Product> findByProductCategory_CategoryId(String categoryId);
	
	Collection<Product> findAll();

	
}
