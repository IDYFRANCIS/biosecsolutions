package com.francis.biosectest.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francis.biosectest.constants.AppConstants;
import com.francis.biosectest.constants.ServerResponseStatus;
import com.francis.biosectest.dto.AddedProductResponseDto;
import com.francis.biosectest.dto.ProductAdditionDto;
import com.francis.biosectest.dto.ProductDto;
import com.francis.biosectest.dto.ProductRemovalDto;
import com.francis.biosectest.dto.ProductResponseDto;
import com.francis.biosectest.dto.RemovedProductResponseDto;
import com.francis.biosectest.dto.ServerResponse;
import com.francis.biosectest.dto.UpdateProductDto;
import com.francis.biosectest.mail.EmailService;
import com.francis.biosectest.mail.Mail;
import com.francis.biosectest.model.Product;
import com.francis.biosectest.model.ProductCategory;
import com.francis.biosectest.model.Users;
import com.francis.biosectest.repository.CategoryRepository;
import com.francis.biosectest.repository.ProductRepository;
import com.francis.biosectest.repository.UsersRepository;
import com.francis.biosectest.service.ProductService;



@Transactional
@Service
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	AppConstants appConstants;
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public Product findByProductId(String productId) {
		
		try {
			return productRepo.findByProductId(productId);
			
		} catch(Exception e){
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	public Collection<Product> findAll() {
		
		try {
			return productRepo.findAll();
					
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	public Product findByProductName(String productName) {
		
		try {
			return productRepo.findByProductName(productName);
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	
	@Override
	public Product findByPrice(double productPrice) {
		
		try {
			return productRepo.findByPrice(productPrice);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public Collection<Product> findByProductCategory(String categoryId) {
		
		try {
			return (Collection<Product>) productRepo.findByProductCategory_CategoryId(categoryId);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ServerResponse create(ProductDto request) {
		
		ServerResponse response = new ServerResponse();
		
       Product product = null;
		
        String categoryId = request.getCategoryId() != null ? request.getCategoryId() : request.getCategoryId()
;		String productName = request.getProductName() != null ? request.getProductName() : request.getProductName();
		double productPrice = request.getProductPrice() != 0 ? request.getProductPrice() : request.getProductPrice();
		long   productQuantity = request.getProductQuantity() != 0 ? request.getProductQuantity() : request.getProductQuantity();
		
		
      if (categoryId == null || categoryId.isEmpty()) {
			
    		response.setData("");
			response.setMessage("Please enter product category Id");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		
		if (productName == null || productName.isEmpty()) {
			
			response.setData("");
			response.setMessage("Please enter product name");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		if (productPrice == 0) {
			
			response.setData("");
			response.setMessage("Please  provide product price");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return  response;
		}
		
		
       if (productQuantity == 0) {
			
			response.setData("");
			response.setMessage("Please  provide product quantity");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return  response;
		}
		
		
		try {
			
			ProductCategory category = categoryRepo.findByCategoryId(categoryId);
			
			if(category == null) {
				response.setData("");
				response.setMessage("product category not found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
		
			
			Product requestProduct = productRepo.findByProductName(productName);
			
			if(requestProduct != null) {
				response.setData("");
				response.setMessage("Product already exist");
            	response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
		
			
		 product =  new Product();
		 
		 product.setProductName(productName);
		 product.setProductCategory(category);
		 product.setPrice(productPrice);
		 product.setQuauntity(productQuantity);
		 product.setDateCreated(new Date());
		 product.setProductAvailablity(true);
		 
		 productRepo.save(product);
		 
		    response.setData(product);
			response.setMessage("Product created successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.UPDATED);
		
		} catch (Exception e) {
			
			response.setData("");
			response.setMessage("Failed to create product");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
			return response;
		}
	
		return response;
	}

	@Override
	public ServerResponse update(String productId, UpdateProductDto request) {
		
		ServerResponse response = new ServerResponse();
		
		Product product = null;
		
		String productName = request.getProductName() != null ? request.getProductName() : request.getProductName();
		double productPrice = request.getProductPrice() != 0 ? request.getProductPrice() : request.getProductPrice();
		long productQuantity = request.getProductQuantity() != 0 ? request.getProductQuantity() : request.getProductQuantity();

		
		if (productName == null) {
			
			response.setData("");
			response.setMessage("Please provide product name");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
      if (productPrice == 0) {
			
			response.setData("");
			response.setMessage("Please provide product price");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
      
      if (productQuantity == 0) {
			
			response.setData("");
			response.setMessage("Please provide product quantity");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
      
		
		try {
			
			Product productUpdate = productRepo.findByProductId(productId);
			
			if (productUpdate == null) {
				response.setData("");
				response.setMessage("Product does not exist");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
			product = productRepo.findByProductId(productId);
			
			if (productName != null)
				product.setProductName(productName);
			if (productPrice != 0)
				product.setPrice(productPrice);
			if (productQuantity != 0)
				product.setQuauntity(productQuantity);
			
			
			productRepo.save(product);
			
			response.setData(product);
			response.setMessage("Product updated successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.UPDATED);
			
		} catch (Exception e) {
			
			response.setData("");
			response.setMessage("Failed to update update");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
			return response;
		}
		
		return response;
	}

	@Override
	public ServerResponse addProductToUserCart(String productId, long usersId, ProductAdditionDto request) {
		
		ServerResponse response = new ServerResponse();
		
		
       if (productId == null || productId.isEmpty()) {
			
			response.setData("");
			response.setMessage("Please provide product details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		if (request.getProductQuantity() == 0) {
			
			response.setData("");
			response.setMessage("Please provide product quantity details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
       if (usersId == 0) {
			
			response.setData("");
			response.setMessage("Please provide user details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		try {
			Users users =  usersRepo.findByUsersId(usersId);
			 
			if (users == null) {
				response.setData("");
				response.setMessage("User not found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
			Product product = productRepo.findByProductId(productId);
			
			if (product == null) {
				response.setData("");
				response.setMessage("Product not found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
			
			Product	productCheck = productRepo.findByProductId(productId);
			
			if (productCheck.isProductAvailablity() == false) {
				response.setData("");
				response.setMessage("Product out of stock and not available");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.OK);
				
				return response;
			}
			
		
			users.getProduct().add(product);
			product.setUsers(users);
			product.setQuauntity(request.getProductQuantity());
			product.setProductAvailablity(false);
			product.setDateAddedToUserCart(new Date());
			product.setDateRemovedFromUserCart(null);
			
			SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ROOT);
			Date parsedDate = sdf.parse(String.valueOf(product.getDateAddedToUserCart()));
			SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
			
			Mail mail = new Mail();
            mail.setTo(appConstants.APP_ADMIN_EMAIL);
            mail.setFrom("idongesitukut25@gmail.com");
            mail.setSubject("Product Addition To Cart");

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("title", "Dear " + appConstants.APP_DEFAULT_ADMIN_NAME);
            model.put("category", product.getProductCategory().getCategoryName());
            model.put("name", product.getProductName());
            model.put("quantity",  request.getProductQuantity());
            model.put("phone", product.getUsers().getPhoneNumber());
            model.put("date", product.getDateAddedToUserCart().toLocaleString());
			model.put("email", product.getUsers().getEmailAddress());
            model.put("content", "Product Purchase from Biosec solution E-Commerce App. A product with number: <b>" + productId + "</b> has been added to <b>" + product.getUsers().getFirstName() +  "</b> , Thank you for your patronage.");
            mail.setModel(model);
            mail.setTemplate("assign_template.ftl");

            emailService.sendSimpleMessage(mail);

            model.put("content", "Hello, you have purchased a product with the following details below, Thank you for patronizing us, We appreciate you.");
            model.put("title", "Dear " + product.getUsers().getFirstName());
            mail.setModel(model);
            mail.setTo(product.getUsers().getEmailAddress());
            emailService.sendSimpleMessage(mail);
			
			productRepo.save(product);
			
			AddedProductResponseDto assign = new AddedProductResponseDto();
			assign.setProductId(productId);
			assign.setProductName(product.getProductName());
			assign.setProductPrice(product.getPrice());
			assign.setProductQuantity(request.getProductQuantity());
			assign.setStockAvailablity(product.isProductAvailablity());
			assign.setUserFirstName(users.getFirstName());
			assign.setUserLastName(users.getLastName());
			assign.setUserPhoneNumber(users.getPhoneNumber());
			assign.setUserEmailAddress(users.getEmailAddress());
			assign.setDateAddedToCustomerCart(product.getDateAddedToUserCart().toLocaleString());
			
			response.setData(assign);
			response.setMessage("Product added successfully to customer cart");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
			
		}catch(Exception e) {
			response.setData("");
			response.setMessage("Failed to add product to customer cart");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		return response;
	}
	
	
	
	@Override
	public ServerResponse removeProductFromUserCart(String productId, long usersId, ProductRemovalDto request) {
		ServerResponse response = new ServerResponse();
		
		
	       if (productId == null || productId.isEmpty()) {
				
				response.setData("");
				response.setMessage("Please provide product details");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
			if (usersId == 0) {
				
				response.setData("");
				response.setMessage("Please provide user details");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
            if (request.getProductQuantity() == 0) {
				
				response.setData("");
				response.setMessage("Please provide product quantity details");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
			}
			
			try {
				Users users =  usersRepo.findByUsersId(usersId);
				 
				if (users == null) {
					response.setData("");
					response.setMessage("User not found");
					response.setSuccess(false);
					response.setStatus(ServerResponseStatus.FAILED);
					
					return response;
				}
				
				Product product = productRepo.findByProductId(productId);
				
				if (product == null) {
					response.setData("");
					response.setMessage("product not found");
					response.setSuccess(false);
					response.setStatus(ServerResponseStatus.FAILED);
					
					return response;
				}
				
				
				 Product productCheck = productRepo.findByProductId(productId);
					
					if (productCheck.isProductAvailablity() == true) {
						response.setData("");
						response.setMessage("Product not in user cart");
						response.setSuccess(false);
						response.setStatus(ServerResponseStatus.OK);
						
						return response;
					}
				
			    product.setDateRemovedFromUserCart(new Date());
								
				Mail mail = new Mail();
	            mail.setTo(appConstants.APP_ADMIN_EMAIL);
	            mail.setFrom("idongesitukut25@gmail.com");
	            mail.setSubject("Product Removal From Cart");

	            Map<String, Object> model = new HashMap<String, Object>();

	            model.put("title", "Dear " + appConstants.APP_DEFAULT_ADMIN_NAME);
	            model.put("category", product.getProductCategory().getCategoryName());
	            model.put("name", product.getProductName());
	            model.put("quantity",  request.getProductQuantity());
	            model.put("phone", product.getUsers().getPhoneNumber());
	            model.put("date", product.getDateRemovedFromUserCart().toLocaleString());
				model.put("email", product.getUsers().getEmailAddress());
	            model.put("content", "Product Removal from Biosec E-Commerce App. A product with number: <b>" + productId + "</b> has been retrived from <b>" + product.getUsers().getFirstName() +  "</b> , Kindly ensure that this product is prepared for re-sale.");
	            mail.setModel(model);
	            mail.setTemplate("unassign_template.ftl");

	            emailService.sendSimpleMessage(mail);

	            model.put("content", "Hello, the product with the following details below earlier assigned to you has been retrived, thank you for turning in the product in good condition.");
	            model.put("title", "Dear " + product.getUsers().getFirstName());
	            mail.setModel(model);
	            mail.setTo(product.getUsers().getEmailAddress());
	            emailService.sendSimpleMessage(mail);
	            
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ROOT);
				Date parsedDate = sdf.parse(String.valueOf(product.getDateRemovedFromUserCart()));
				SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
	            
				
	            users.setProduct(null);
				product.setUsers(null);
				product.setQuauntity(request.getProductQuantity());
				product.setProductAvailablity(true);				
				product.setDateAddedToUserCart(null);
				
				
				RemovedProductResponseDto retrive = new RemovedProductResponseDto();
				retrive.setProductId(productId);
				retrive.setProductName(product.getProductName());
				retrive.setProductPrice(product.getPrice());
				retrive.setProductQuantity(request.getProductQuantity());
				retrive.setStockAvailablity(product.isProductAvailablity());
				retrive.setUserFirstName(users.getFirstName());
				retrive.setUserLastName(users.getLastName());
				retrive.setUserPhoneNumber(users.getPhoneNumber());
				retrive.setUserEmailAddress(users.getEmailAddress());
				retrive.setDateRemovedFromCustomerCart(product.getDateRemovedFromUserCart().toLocaleString());
	     
								
				response.setData(retrive);
				response.setMessage("Product successfully removed from customers cart");
				response.setSuccess(true);
				response.setStatus(ServerResponseStatus.OK);
				
			}catch(Exception e) {
				response.setData("");
				response.setMessage("Failed to remove product from customers cart");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.INTERNAL_SERVER_ERROR);
				return response;
			}
			
			return response;
	  }
	
	
	

	@Override
	public ServerResponse getProduct(String productId) {
		
		ServerResponse response = new ServerResponse();
		
		if(productId == null || productId.isEmpty()) {
			response.setData("");
			response.setMessage(" Please enter product details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		try {
			
			Product product = productRepo.findByProductId(productId);
			
			if (product == null) {
				response.setData("");
				response.setMessage("Product not found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.FAILED);
				
				return response;
				
			}
			
			response.setData(product);
			response.setMessage("Product found successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
			
		} catch (Exception e){
			
			response.setData("");
			response.setMessage("Failed to fetch Product");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
		
		
		return response;
	}


	@Override
	public ServerResponse viewAll() {
		
		ServerResponse response = new ServerResponse();
		
		try {
			Collection<Product> stock = productRepo.findAll();
			
			if (stock.size() < 1) {
				response.setData("");
				response.setMessage("Product list is empty");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.NO_CONTENT);
				
				return response;
			}
			
			response.setData(stock);
			response.setMessage("Products fetched successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
		} catch (Exception e){
			
			response.setData("");
			response.setMessage("Failed to fetch Products");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			return response;
		}
		
		return response;
	}


	@Override
	public ServerResponse getProductByCategory(String categoryId) {
		
       ServerResponse response = new ServerResponse();
		
		if(categoryId == null || categoryId.isEmpty()) {
			response.setData("");
			response.setMessage("Please enter Product Category details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			
			return response;
		}
		
		try {
			
			Collection<Product> catStock = productRepo.findByProductCategory_CategoryId(categoryId);
			
			if (catStock.size() < 1) {
				response.setData("");
				response.setMessage("No product found under this category");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.NO_CONTENT);
				
				return response;
				
			}
			
			
			Collection<ProductResponseDto> dtos = new HashSet<>();
			
			for(Product products: catStock) {
				ProductResponseDto dto = new ProductResponseDto();
				dto.setProductName(products.getProductName());
				dto.setProductPrice(products.getPrice());
				dto.setProductId(products.getProductId());
				dto.setStockAvailablity(products.isProductAvailablity());
				dto.setProductQuantity(products.getQuauntity());
				
				dtos.add(dto);
				
			}
			
			response.setData(dtos);
			response.setMessage("Products found successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
			
		} catch (Exception e){
			
			response.setData("");
			response.setMessage("Failed to fetch products by category");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			return response;
		}
		
		
		return response;
	}


	
}
