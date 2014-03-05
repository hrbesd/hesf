package com.esd.hesf.model;

/**
 * 公司类
 * 
 * @author Administrator
 * 
 */
public class Company extends PrimaryKey_Int {

	private String companyCode; // 档案号
	private String companyManagement; // 主管部门
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
	private String companyPhone = ""; // 企业电话
	private String companyMobile = "";// 企业联系人手机
	private String companyBank;// 开户银行
	private String companyBankAccount;// 银行账户
	private String companyRemark;// 备注

	public Company() {
	}

	public Company(Integer id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Company [companyCode=" + companyCode + ", companyManagement=" + companyManagement + ", companyName=" + companyName + ", companyLegal=" + companyLegal + ", companyContactPerson="
				+ companyContactPerson + ", companyOrganizationCode=" + companyOrganizationCode + ", companyTaxCode=" + companyTaxCode + ", companyAddress=" + companyAddress + ", companyZipCode="
				+ companyZipCode + ", companyFax=" + companyFax + ", companyType=" + companyType + ", companyEconomyType=" + companyEconomyType + ", companyProperty=" + companyProperty + ", area="
				+ area + ", companyPhone=" + companyPhone + ", companyMobile=" + companyMobile + ", companyBank=" + companyBank + ", companyBankAccount=" + companyBankAccount + ", companyRemark="
				+ companyRemark + "]";
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyManagement() {
		return companyManagement;
	}

	public void setCompanyManagement(String companyManagement) {
		this.companyManagement = companyManagement;
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

}