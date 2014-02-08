package com.esd.hesf.model;

/**
 * 主键id 为String型的类的父类
 * 
 * @author Administrator
 * 
 */
public class PrimaryKey_String extends CommonKey {

	private String id; // 主键ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
