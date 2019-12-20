package com.francis.biosectest.dto;


public class ProductResponseDto {
	
	private String productId;
	private String productName;
	private double productPrice;
	private long productQuantity;
	private boolean stockAvailablity;
	
	
	
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
	
}
