package com.francis.biosectest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.ProductCategoryDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.service.ProductCategoryService;

//import com.bizzdesk.inventory.constant.ServerResponseStatus;
//import com.bizzdesk.inventory.dto.CategoryDto;
//import com.bizzdesk.inventory.dto.ServerResponse;
//import com.bizzdesk.inventory.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/category", produces = "application/json")
@Api(tags = "Product Category Management", description = "Endpoint")
//@PreAuthorize("hasAuthourity('CREATE')")
public class CategoryController {

	@Autowired
	ProductCategoryService categoryService;
	
	private HttpHeaders responseHeaders = new HttpHeaders();
	
	
	@ApiOperation(value = "Create a product category", response = ServerResponse.class)
	@RequestMapping(value = "/create-product-category", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@RequestHeader("Authorization")  String authorization, @RequestBody ProductCategoryDto category){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = categoryService.create(category);
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to create product category");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
	@ApiOperation(value = "Get all product category", response = ServerResponse.class)
    @RequestMapping(value = "/get-all-product-category", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> viewAll(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = categoryService.viewAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while fetching product category" + e.getMessage());
			response.setMessage("An error occured while fetching product category");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	@ApiOperation(value = "Delete product category", response = ServerResponse.class)
	@RequestMapping(value = "/delete-product-category/{categoryId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteCategory(@RequestHeader("Authorization") String authorization, @PathVariable("categoryId") String categoryId){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = categoryService.delete(categoryId);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to delete product category");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
	
}
