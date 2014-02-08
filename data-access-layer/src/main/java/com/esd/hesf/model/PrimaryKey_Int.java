package com.esd.hesf.model;

/**
 * 主键id 为int型的类的父类
 * 
 * @author Administrator
 * 
 */
public class PrimaryKey_Int extends CommonKey {

	private int id; // 主键ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
