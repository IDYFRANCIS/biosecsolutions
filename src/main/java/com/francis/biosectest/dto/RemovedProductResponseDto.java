package com.francis.biosectest.dto;

public class RemovedProductResponseDto {
	
	
	private String productId;
	private String productName;
	private double productPrice;
	private long productQuantity;
	private boolean stockAvailablity;
	private String userFirstName;
	private String userLastName;
	private String userPhoneNumber;
	private String userEmailAddress;
	private String dateRemovedFromCustomerCart;
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}
	public boolean isStockAvailablity() {
		return stockAvailablity;
	}
	public void setStockAvailablity(boolean stockAvailablity) {
		this.stockAvailablity = stockAvailablity;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserEmailAddress() {
		return userEmailAddress;
	}
	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}
	public String getDateRemovedFromCustomerCart() {
		return dateRemovedFromCustomerCart;
	}
	public void setDateRemovedFromCustomerCart(String dateRemovedFromCustomerCart) {
		this.dateRemovedFromCustomerCart = dateRemovedFromCustomerCart;
	}
	
}
