package com.francis.biosectest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.francis.biosectest.model.ProductCategory;



@Repository
public interface CategoryRepository extends CrudRepository<ProductCategory, String>{
	
	ProductCategory findByCategoryName(String categoryName);
	
	ProductCategory findByCategoryId(String categoryId);
		
	ProductCategory findByProduct_ProductCategory(String categoryId);

}
