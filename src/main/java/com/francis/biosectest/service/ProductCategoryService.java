package com.francis.biosectest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.francis.biosectest.dto.ProductCategoryDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.model.ProductCategory;




@Service
public interface ProductCategoryService {

	public Collection<ProductCategory> findAll();
	
	public  ProductCategory findByCategoryId(String categoryId);
	
	public ProductCategory findByCategoryName(String categoryName);
	
	ServerResponse create(ProductCategoryDto category);
	
	ServerResponse viewAll();
	
	ServerResponse delete(String categoryId);

	
}
