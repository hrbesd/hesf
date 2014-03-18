/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.company;

import com.esd.hesf.model.PrimaryKey_String;

/*
 * 基本的档案参数类
 */
public class CompanyParamModel extends PrimaryKey_String {

	private String year; // 年度
	private String companyCode = ""; // 档案号码
	private String companyTaxCode = "";// 税务编码
	private String companyOrganizationCode = "";// 组织机构代码证号
	private Integer companyProperty; // 公司性质 _
	private Integer companyEconomyType; // 企业经济类型
	private String area = ""; // 公司所属地区

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getCompanyEconomyType() {
		return companyEconomyType;
	}

	public void setCompanyEconomyType(Integer companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	private Integer companyEmpTotal_1;// 员工总数
	private Integer companyEmpTotal_2;// 员工总数
	private String companyName; // 企业名称
	private String companyAddress;// 企业地址
	private String companyLegal; // 企业法人
	private Integer companyHandicapTotal_1;// 残疾员工总数
	private Integer companyHandicapTotal_2;// 残疾员工总数
	private Integer page;
	private Integer rows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyTaxCode() {
		return companyTaxCode;
	}

	public void setCompanyTaxCode(String companyTaxCode) {
		this.companyTaxCode = companyTaxCode;
	}

	public String getCompanyOrganizationCode() {
		return companyOrganizationCode;
	}

	public void setCompanyOrganizationCode(String companyOrganizationCode) {
		this.companyOrganizationCode = companyOrganizationCode;
	}

	public Integer getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(Integer companyProperty) {
		this.companyProperty = companyProperty;
	}

	public Integer getCompanyEmpTotal_1() {
		return companyEmpTotal_1;
	}

	public void setCompanyEmpTotal_1(Integer companyEmpTotal_1) {
		this.companyEmpTotal_1 = companyEmpTotal_1;
	}

	public Integer getCompanyEmpTotal_2() {
		return companyEmpTotal_2;
	}

	public void setCompanyEmpTotal_2(Integer companyEmpTotal_2) {
		this.companyEmpTotal_2 = companyEmpTotal_2;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyLegal() {
		return companyLegal;
	}

	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}

	public Integer getCompanyHandicapTotal_1() {
		return companyHandicapTotal_1;
	}

	public void setCompanyHandicapTotal_1(Integer companyHandicapTotal_1) {
		this.companyHandicapTotal_1 = companyHandicapTotal_1;
	}

	public Integer getCompanyHandicapTotal_2() {
		return companyHandicapTotal_2;
	}

	public void setCompanyHandicapTotal_2(Integer companyHandicapTotal_2) {
		this.companyHandicapTotal_2 = companyHandicapTotal_2;
	}

	@Override
	public String toString() {
		return "CompanyParamModel [year=" + year + ", companyCode=" + companyCode + ", companyTaxCode=" + companyTaxCode + ", companyOrganizationCode=" + companyOrganizationCode + ", companyProperty=" + companyProperty + ", companyEconomyType=" + companyEconomyType + ", area=" + area + ", companyEmpTotal_1=" + companyEmpTotal_1 + ", companyEmpTotal_2=" + companyEmpTotal_2 + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress + ", companyLegal=" + companyLegal + ", companyHandicapTotal_1=" + companyHandicapTotal_1 + ", companyHandicapTotal_2=" + companyHandicapTotal_2 + ", page=" + page + ", rows=" + rows + "]";
	}

}
