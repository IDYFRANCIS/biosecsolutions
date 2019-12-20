# biosecsolutions
An E-Commerce REST API For BiosecSolutions

THIS IS A TEST REST API FOR USERS TO ADD/PRUCHASE PRODUCTS AND GET NOTIFICATIONS FOR THE PRODUCTS ADDED TO THEIR CART.

THE FOLLOWING STEPS WILL HELP SET UP THE PROJECT.

(a) After successfully clonning the project, run the code to initialize the application.

(b) Once the code is running, you can access the swagger documentation at "http://localhost:8080/swagger-ui.html#/"

(c) To register as a user on the system, use the "USER REGISTERATION" endpoint on the swagger documentation and fill all the 
    required fields.
    
(d) On successful registeration, an email is sent to the user with an "Activation code" generated from from the system.
    Copy this activation code and head over to the verification endpoint and paste the activation code as well as provide your
    password for login and security access to the system.
    
(e) On completing the verification process, a mail is sent to the user notifying the user of a successful account activation.

(d) Now the user is fully registered and can access the system by loging in through the "Logoin Endpoint" on the swagger documentation.

(f) On login, an access token is generated. Copy the token and head on to the "PRODUCT CATEGORY Endpoint" to create categories for products.
    Products will be registered under these categories based on where each products belong so that users/customers can easily add products to their cart from these categories.
    On completing this, a unique code is generated from the system for each created product category.
    
(g) At the Product's endpoint, provide your system generated "CategoryCode" and create products under each category created. On completion of 
    this, a "ProductCode" is generated for each product as created under a category.
    
(h) Now you can visit the "AddProductToUserCart Endpoint" with your "ProductCode" and "UserId"  to add products to               users/customers cart. 
    On successfully adding a products to customers/users cart, a mail notification is sent to the users/customers notifying     them about details of the products they added to their carts.
    
(i) Just as customers/users are able to add products, you can head over to the "RemoveFromUsersCart Endpoint" to remove         products from users cart and a mail notification as well will be sent to 
    the users notifying them of the removal as well.
    
(j) Also, products can be fetched using "ProductsId" and users can increase or reduce the quantity of products as the           desire.

(k) Products can be updated using product's Id and products can equally be fetched through categories.
    
   This application will be hosted on line hopefully when an abit chance to upload it and the url will be made available as    well as the test cases. Thank you.
   I can be contacted for any additional information required via idongesitukut25@gmail.com
   
