/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

public class AuditParamModel {
	private String year; // 年度
	private String companyCode; // 档案号码
	private String companyTaxCode;// 税务编码
	private String companyOrganizationCode;// 组织机构代码证号
	private Integer companyProperty; // 公司性质 _
	private String companyEconomyType; // 企业经济类型
	private String area; // 公司所属地区
	private Integer companyEmpTotal_1;// 员工总数
	private Integer companyEmpTotal_2;// 员工总数
	private String companyName; // 企业名称
	private String companyAddress;// 企业地址
	private String companyLegal; // 企业法人
	private String companyContactPerson; // 企业联系人
	private String auditProcessStatus; // 流程状态
	private String paymentPerson; // 缴款人
	private String overYear; // 超过指定年度未初审的企业
	private String isExempt; // 是否免交
	private String firstTime;

	private String isDownLoadAll; // 是否下载全部查询出来的数据 yes or no
	private Integer page;
	private Integer rows;

	public String getIsDownLoadAll() {
		return isDownLoadAll;
	}

	public void setIsDownLoadAll(String isDownLoadAll) {
		this.isDownLoadAll = isDownLoadAll;
	}

	public String getPaymentPerson() {
		return paymentPerson;
	}

	public void setPaymentPerson(String paymentPerson) {
		this.paymentPerson = paymentPerson;
	}

	public String getCompanyContactPerson() {
		return companyContactPerson;
	}

	public void setCompanyContactPerson(String companyContactPerson) {
		this.companyContactPerson = companyContactPerson;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

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

	public String getCompanyEconomyType() {
		return companyEconomyType;
	}

	public void setCompanyEconomyType(String companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getAuditProcessStatus() {
		return auditProcessStatus;
	}

	public void setAuditProcessStatus(String auditProcessStatus) {
		this.auditProcessStatus = auditProcessStatus;
	}

	@Override
	public String toString() {
		return "AuditParamModel [year=" + year + ", companyCode=" + companyCode
				+ ", companyTaxCode=" + companyTaxCode
				+ ", companyOrganizationCode=" + companyOrganizationCode
				+ ", companyProperty=" + companyProperty
				+ ", companyEconomyType=" + companyEconomyType + ", area="
				+ area + ", companyEmpTotal_1=" + companyEmpTotal_1
				+ ", companyEmpTotal_2=" + companyEmpTotal_2 + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress
				+ ", companyLegal=" + companyLegal + ", auditProcessStatus="
				+ auditProcessStatus + ", paymentPerson=" + paymentPerson
				+ ", overYear=" + overYear + ", isExempt=" + isExempt
				+ ", page=" + page + ", rows=" + rows + "]";
	}

	public String getIsExempt() {
		return isExempt;
	}

	public void setIsExempt(String isExempt) {
		this.isExempt = isExempt;
	}

	public String getOverYear() {
		return overYear;
	}

	public void setOverYear(String overYear) {
		this.overYear = overYear;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

}
