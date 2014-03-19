/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.statistics;

/**
 * 报表模型
 * 
 * @author Administrator
 * 
 */
public class ReportModel {

	private String Title;// 标题
	private String CreateCompany;// 制表单位
	private String CreateData;// 创建日期
	private String CreatePeople;// 制表人
	private String Type;// 类型

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getCreateCompany() {
		return CreateCompany;
	}

	public void setCreateCompany(String createCompany) {
		CreateCompany = createCompany;
	}

	public String getCreateData() {
		return CreateData;
	}

	public void setCreateData(String createData) {
		CreateData = createData;
	}

	public String getCreatePeople() {
		return CreatePeople;
	}

	public void setCreatePeople(String createPeople) {
		CreatePeople = createPeople;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
}
