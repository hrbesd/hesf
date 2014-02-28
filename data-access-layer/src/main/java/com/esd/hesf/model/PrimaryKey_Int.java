package com.esd.hesf.model;

/**
 * 主键id 为int型的类的父类
 * 
 * @author Administrator
 * 
 */
public class PrimaryKey_Int extends CommonKey {

	private Integer id; // 主键ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
