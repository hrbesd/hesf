/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.payment;

/**
 * 
 * @author Administrator
 * 
 */
public class PaymentParamModel {

	private String year; // 审核年度
	private String companyCode; // 公司code
	private String companyName; // 公司名称
	private Integer companyProperty; // 公司性质 id
	private Integer companyEconomyType; // 企业经济类型id
	private String area; // 企业所在地区code
	private Integer paymentPerson; // 缴款人id
	private String startDate; // 缴款开始时间
	private String endDate; // 缴款结束时间
	private Boolean billReturn; // 返票?
	private Boolean billObsolete; // 作废票号?
	private String belongsType; // 区分省残联和地税的单位
	private String minPaymentMoney; // 最小缴款额
	private String maxPaymentMoney; // 最大缴款额
	private Integer page;
	private Integer rows;
	private String isDownLoadAll; // 是否下载全部查询出来的数据 yes or no

	@Override
	public String toString() {
		return "PaymentParamModel [year=" + year + ", companyCode="
				+ companyCode + ", companyName=" + companyName
				+ ", companyProperty=" + companyProperty
				+ ", companyEconomyType=" + companyEconomyType + ", area="
				+ area + ", paymentPerson=" + paymentPerson + ", startDate="
				+ startDate + ", endDate=" + endDate + ", billReturn="
				+ billReturn + ", billObsolete=" + billObsolete + "]";
	}

	public String getMinPaymentMoney() {
		return minPaymentMoney;
	}

	public void setMinPaymentMoney(String minPaymentMoney) {
		this.minPaymentMoney = minPaymentMoney;
	}

	public String getMaxPaymentMoney() {
		return maxPaymentMoney;
	}

	public void setMaxPaymentMoney(String maxPaymentMoney) {
		this.maxPaymentMoney = maxPaymentMoney;
	}

	public String getBelongsType() {
		return belongsType;
	}

	public void setBelongsType(String belongsType) {
		this.belongsType = belongsType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(Integer companyProperty) {
		this.companyProperty = companyProperty;
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

	public Integer getPaymentPerson() {
		return paymentPerson;
	}

	public void setPaymentPerson(Integer paymentPerson) {
		this.paymentPerson = paymentPerson;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Boolean getBillReturn() {
		return billReturn;
	}

	public void setBillReturn(Boolean billReturn) {
		this.billReturn = billReturn;
	}

	public Boolean getBillObsolete() {
		return billObsolete;
	}

	public void setBillObsolete(Boolean billObsolete) {
		this.billObsolete = billObsolete;
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

	public String getIsDownLoadAll() {
		return isDownLoadAll;
	}

	public void setIsDownLoadAll(String isDownLoadAll) {
		this.isDownLoadAll = isDownLoadAll;
	}

}
