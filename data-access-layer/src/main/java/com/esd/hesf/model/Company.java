package com.esd.hesf.model;

import java.math.BigDecimal;

/**
 * 公司类
 * 
 * @author Administrator
 * 
 */
public class Company extends PrimaryKey_String {

	private String year; // 年度
	private String companyCode; // 档案号
	private String companyName; // 企业名称
	private String companyLegal; // 企业法人
	private String companyContactPerson; // 企业联系人
	private String companyOrganizationCode;// 组织机构代码证号
	private String companyTaxCode;// 税务编码
	private String companyAddress;// 企业地址
	private String companyZipCode;// 企业邮政编码
	private String companyFax; // 传真
	private CompanyType companyType; // 企业类型
	private CompanyEconomyType companyEconomyType; // 企业经济类型
	private CompanyProperty companyProperty; // 公司属性
	private Area area; // 公司所属地区
	private String companyPhone=""; // 企业电话
	private String companyMobile="";// 企业联系人手机
	private String companyBank;// 开户银行
	private String companyBankAccount;// 银行账户
	private String companyRemark;// 备注
	private Integer companyEmpTotal;// 员工总数
	private Integer companyHandicapTotal;// 残疾员工总数 已录入数
	private Integer companyPredictTotal; // 预计残疾人数
	private BigDecimal companyShouldTotal; // 应安排残疾人数
	private Integer companyAlreadyTotal; // 已安排残疾人数

	@Override
	public String toString() {
		return "Company [year=" + year + ", companyCode=" + companyCode + ", companyName=" + companyName + ", companyLegal=" + companyLegal + ", companyContactPerson=" + companyContactPerson
				+ ", companyOrganizationCode=" + companyOrganizationCode + ", companyTaxCode=" + companyTaxCode + ", companyAddress=" + companyAddress + ", companyZipCode=" + companyZipCode
				+ ", companyType=" + companyType + ", companyEconomyType=" + companyEconomyType + ", companyProperty=" + companyProperty + ", area=" + area + ", companyPhone=" + companyPhone
				+ ", companyMobile=" + companyMobile + ", companyBank=" + companyBank + ", companyBankAccount=" + companyBankAccount + ", companyRemark=" + companyRemark + ", companyEmpTotal="
				+ companyEmpTotal + ", companyHandicapTotal=" + companyHandicapTotal + ", companyPredictTotal=" + companyPredictTotal + ", companyShouldTotal=" + companyShouldTotal
				+ ", companyAlreadyTotal=" + companyAlreadyTotal + ", getId()=" + getId() + "]";
	}

	public Company() {
	}

	public Company(String id) {
		super.setId(id);
	}

	public Company(String year, String companyCode) {
		this.year = year;
		this.companyCode = companyCode;
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

	public String getCompanyLegal() {
		return companyLegal;
	}

	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}

	public String getCompanyContactPerson() {
		return companyContactPerson;
	}

	public void setCompanyContactPerson(String companyContactPerson) {
		this.companyContactPerson = companyContactPerson;
	}

	public String getCompanyOrganizationCode() {
		return companyOrganizationCode;
	}

	public void setCompanyOrganizationCode(String companyOrganizationCode) {
		this.companyOrganizationCode = companyOrganizationCode;
	}

	public String getCompanyTaxCode() {
		return companyTaxCode;
	}

	public void setCompanyTaxCode(String companyTaxCode) {
		this.companyTaxCode = companyTaxCode;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyZipCode() {
		return companyZipCode;
	}

	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public CompanyEconomyType getCompanyEconomyType() {
		return companyEconomyType;
	}

	public void setCompanyEconomyType(CompanyEconomyType companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

	public CompanyProperty getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(CompanyProperty companyProperty) {
		this.companyProperty = companyProperty;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyMobile() {
		return companyMobile;
	}

	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}

	public String getCompanyBank() {
		return companyBank;
	}

	public void setCompanyBank(String companyBank) {
		this.companyBank = companyBank;
	}

	public String getCompanyBankAccount() {
		return companyBankAccount;
	}

	public void setCompanyBankAccount(String companyBankAccount) {
		this.companyBankAccount = companyBankAccount;
	}

	public String getCompanyRemark() {
		return companyRemark;
	}

	public void setCompanyRemark(String companyRemark) {
		this.companyRemark = companyRemark;
	}

	public Integer getCompanyEmpTotal() {
		return companyEmpTotal;
	}

	public void setCompanyEmpTotal(Integer companyEmpTotal) {
		this.companyEmpTotal = companyEmpTotal;
	}

	public Integer getCompanyHandicapTotal() {
		return companyHandicapTotal;
	}

	public void setCompanyHandicapTotal(Integer companyHandicapTotal) {
		this.companyHandicapTotal = companyHandicapTotal;
	}

	public Integer getCompanyPredictTotal() {
		return companyPredictTotal;
	}

	public void setCompanyPredictTotal(Integer companyPredictTotal) {
		this.companyPredictTotal = companyPredictTotal;
	}

	public BigDecimal getCompanyShouldTotal() {
		return companyShouldTotal;
	}

	public void setCompanyShouldTotal(BigDecimal companyShouldTotal) {
		this.companyShouldTotal = companyShouldTotal;
	}

	public Integer getCompanyAlreadyTotal() {
		return companyAlreadyTotal;
	}

	public void setCompanyAlreadyTotal(Integer companyAlreadyTotal) {
		this.companyAlreadyTotal = companyAlreadyTotal;
	}

}