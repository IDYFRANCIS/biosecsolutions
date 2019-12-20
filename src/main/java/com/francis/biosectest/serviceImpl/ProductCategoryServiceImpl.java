package com.francis.biosectest.serviceImpl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ProductCategoryDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.model.ProductCategory;
import com.francis.biosectest.repository.CategoryRepository;
import com.francis.biosectest.repository.ProductRepository;
import com.francis.biosectest.service.ProductCategoryService;



@Transactional
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	ProductRepository stockRepo;
	
	@Autowired
	CategoryRepository catgeoryRepo;

	
	
	@Override
	public Collection<ProductCategory> findAll() {
		
		try {
			return (Collection<ProductCategory>) catgeoryRepo.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	@Override
	public ProductCategory findByCategoryId(String categoryId) {
		
		try {
			return catgeoryRepo.findByCategoryId(categoryId);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	@Override
	public ProductCategory findByCategoryName(String categoryName) {
		
		try {
			return catgeoryRepo.findByCategoryName(categoryName);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}

	
	
	@Override
	public ServerResponse create(ProductCategoryDto category) {
		
		ServerResponse response = new ServerResponse();
		
		ProductCategory category1 = null;
		
		 String categoryName = category.getCategoryName() != null ? category.getCategoryName() : category.getCategoryName();
		
		 if (categoryName == null || categoryName.isEmpty()) {
				
				response.setData("");
				response.setMessage("Please enter category name");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
		 
		 try {
				
				ProductCategory requestCategory = catgeoryRepo.findByCategoryName(categoryName);
				
				if(requestCategory != null) {
					response.setData("");
					response.setMessage("Product Category already exist");
	            	response.setSuccess(false);
					response.setStatus(ServerResponseStatus.FAILED);
					
					return response;
				}
				
				category1 = new ProductCategory();
				
				category1.setCategoryName(categoryName);
				
				catgeoryRepo.save(category1);
		 
				    response.setData(category);
					response.setMessage("Product Category created successfully");
					response.setSuccess(true);
					response.setStatus(ServerResponseStatus.CREATED);
				
				} catch (Exception e) {
					
					response.setData("");
					response.setMessage("Failed to create product category");
					response.setSuccess(false);
					response.setStatus(ServerResponseStatus.FAILED);
					e.printStackTrace();
					return response;
				}
			
				return response;
	}

	
	

	@Override
	public ServerResponse viewAll() {
		
       ServerResponse response = new ServerResponse();
		
		try {
			Collection<ProductCategory> stock = findAll();
			
			if (stock.size() < 1) {
				response.setData("");
				response.setMessage("ProductCategory list is empty");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.NO_CONTENT);
				
				return response;
			}
			
			response.setData(stock);
			response.setMessage("Data fetched successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
		} catch (Exception e){
			
			response.setData("");
			response.setMessage("Failed to fetch data");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			return response;
		}
		
		return response;
	}



	@Override
	public ServerResponse delete(String categoryId) {
		
		
		 ServerResponse response = new ServerResponse();
			
			if (categoryId == null || categoryId.isEmpty()) {
				
				response.setData("");
				response.setMessage("Product Category details cannot be empty");
				response.setStatus(ServerResponseStatus.FAILED);
				response.setSuccess(false);
					
				return response;
			}
			
			try {
				
				ProductCategory category = catgeoryRepo.findByCategoryId(categoryId);
				
				
				if (category == null) {
					response.setData(" Product Category not found");
					response.setStatus(ServerResponseStatus.FAILED);
					response.setSuccess(false);
					
					return response;
				}
				
				catgeoryRepo.delete(category);
				
				 response.setStatus(ServerResponseStatus.DELETED);
				 response.setData("Product Category has been successfully deleted");
				 response.setSuccess(true);

		        } catch (Exception e) {
		        	response.setStatus(ServerResponseStatus.FAILED);
		        	response.setData("Failed to delete Product category");
		        	response.setSuccess(false);
		            e.printStackTrace();
		            return response;
		        }
			
			return response;
			
		}
			

}
