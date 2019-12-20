package com.francis.biosectest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("serial")
@Entity
@Table(name = "product")
public class Product implements Serializable{
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "product_id")
	private String productId;
		
	@JsonIgnore
	@Column(name = "date_created", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@JsonIgnore
	@Column(name = "date_added", nullable = true, insertable = true, updatable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateAddedToUserCart;
	
	
	@JsonIgnore
	@Column(name = "date_removed", nullable = true, insertable = true, updatable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateRemovedFromUserCart;
	
	@Column(name = "product_price")
	private double price;
	
	@Column(name = "product_quantity", nullable = true, insertable = true, updatable = true)
	private long quauntity;

	@Column(name = "product_name", nullable = false)
	private String productName;
	
	@JsonIgnore
	@Column(name = "is_available", nullable = false)
	private boolean productAvailablity;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users users;

	
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	private ProductCategory productCategory;

    

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateAddedToUserCart() {
		return dateAddedToUserCart;
	}
	public void setDateAddedToUserCart(Date dateAddedToUserCart) {
		this.dateAddedToUserCart = dateAddedToUserCart;
	}
	public Date getDateRemovedFromUserCart() {
		return dateRemovedFromUserCart;
	}
	public void setDateRemovedFromUserCart(Date dateRemovedFromUserCart) {
		this.dateRemovedFromUserCart = dateRemovedFromUserCart;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getQuauntity() {
		return quauntity;
	}
	public void setQuauntity(long quauntity) {
		this.quauntity = quauntity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public boolean isProductAvailablity() {
		return productAvailablity;
	}
	public void setProductAvailablity(boolean productAvailablity) {
		this.productAvailablity = productAvailablity;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

}
