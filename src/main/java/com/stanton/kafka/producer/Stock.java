package com.stanton.kafka.producer;

public class Stock {
	private String EAN;
	private String location;
	private double qty;
	private String storeCode;
	
	public Stock() {
		
	}

	
	public String getEAN() {
		return EAN;
	}

	public void setEAN(String ean) {
		EAN = ean;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	
}
