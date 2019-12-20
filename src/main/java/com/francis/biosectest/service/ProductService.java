package com.francis.biosectest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.francis.biosectest.dto.ProductAdditionDto;
import com.francis.biosectest.dto.ProductDto;
import com.francis.biosectest.dto.ProductRemovalDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.UpdateProductDto;
import com.francis.biosectest.model.Product;




@Service
public interface ProductService {
	
	public Collection<Product> findAll();
	
	public Product findByProductId(String productId);
	
	public Product findByProductName(String productName);
	
	public Product findByPrice(double price);
	
	public Collection<Product> findByProductCategory(String categoryId);
	
	ServerResponse create(ProductDto request); 
	
	ServerResponse update(String productId, UpdateProductDto request);
	
	ServerResponse addProductToUserCart(String productId, long usersId, ProductAdditionDto request);
	
	ServerResponse removeProductFromUserCart(String productId, long usersId, ProductRemovalDto request);
	
	ServerResponse getProduct(String productId);
	
	ServerResponse viewAll();
	
	ServerResponse getProductByCategory(String categoryId);

}
