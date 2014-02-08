package com.esd.hesf.model;

/**
 * 菜单类辅助类, 其中的一个属性
 * 
 * @author Administrator
 * 
 */
public class Attribute {

	private String url;
	private float price;

	public Attribute() {

	}

	public Attribute(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
