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
import com.francis.biosectest.dto.ProductAdditionDto;
import com.francis.biosectest.dto.ProductDto;
import com.francis.biosectest.dto.ProductRemovalDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.UpdateProductDto;
import com.francis.biosectest.service.ProductService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "Product Management", description = "Endpoint")
@RequestMapping(value = "/product", produces = "application/json")
@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	private HttpHeaders responseHeaders = new HttpHeaders();
	
	/**
	 * CREATE PRODUCT
	 * @param authorization
	 * @param request
	 * @return
	 */
	
	@ApiOperation(value = "Create a product", response = ServerResponse.class)
	@RequestMapping(value = "/create-product", method = RequestMethod.POST)
//	@PreAuthorize("hasAuthority('superAdminRole')")
	@ResponseBody
	public ResponseEntity<?> create(@RequestHeader("Authorization")  String authorization, @RequestBody ProductDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = productService.create(request);
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
            response.setMessage("Failed to create product");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
            
		}
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}

	/**
	 * VIEW ALL PRODUCT
	 * @param authorization
	 * @return
	 */
	
	@ApiOperation(value = "Get all products", response = ServerResponse.class)
    @RequestMapping(value = "/get-all-products", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> viewAll(@RequestHeader("Authorization") String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.viewAll();
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while fetching products" + e.getMessage());
			response.setMessage("An error occured while fetching products");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	/**
	 * GET PRODUCT BY CATEGORY
	 * @param authorization
	 * @param categoryName
	 * @return
	 */
	
	@ApiOperation(value = "Get product by category", response = ServerResponse.class)
    @RequestMapping(value = "/get-product-by-category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getProductsByCategory(@RequestHeader("Authorization") String authorization, @PathVariable("categoryId") String categoryId){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.getProductByCategory(categoryId);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setData("An error occured while fetching products by category" + e.getMessage());
			response.setMessage("An error occured while fetching products by category");
	        response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
	
	
	/**
	 * UPDATE PRODUCT
	 * @param authorization
	 * @param stockId
	 * @param request
	 * @return
	 */
	
	@ApiOperation(value = "Update product", response = ServerResponse.class)
	@RequestMapping(value = "/update-product/{productId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@RequestHeader("Authorization") String authorization, @PathVariable("productId") String productId, @RequestBody UpdateProductDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.update(productId, request);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to update product details");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	

	
	/**
	 * ADD PRODUCT TO CUSTOMER CART
	 * @param authorization
	 * @param stockId
	 * @param usersId
	 * @return
	 */
	@ApiOperation(value = "Add product to customer cart", response = ServerResponse.class)
	@RequestMapping(value = "/{productId}/users/{usersId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addProductToUserCart(@RequestHeader("Authorization") String authorization, @PathVariable("productId") String productId, @PathVariable("usersId") Long usersId, @RequestBody ProductAdditionDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.addProductToUserCart(productId, usersId, request);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to assign product to user");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
	@ApiOperation(value = "Remove product from customer cart", response = ServerResponse.class)
	@RequestMapping(value = "/{productId}/users/{usersId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> removeProductFromUserCart(@RequestHeader("Authorization") String authorization, @PathVariable("productId") String productId, @PathVariable("usersId") Long usersId, @RequestBody ProductRemovalDto request){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.removeProductFromUserCart(productId, usersId, request);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to assign stock to user");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	/**
	 * GET PRODUCT BY ID
	 * @param authorization
	 * @param stockId
	 * @return
	 */
	
	@ApiOperation(value = "Get product by ID", response = ServerResponse.class)
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getProduct(@RequestHeader("Authorization") String authorization, @PathVariable("productId") String productId){
		
		ServerResponse response = new ServerResponse();
		
		try {
			response = productService.getProduct(productId);
			
		} catch (Exception e) {
			response.setData("An error occured => " + e.getMessage());
			response.setMessage("Failed to fetch product details");
			response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
}
