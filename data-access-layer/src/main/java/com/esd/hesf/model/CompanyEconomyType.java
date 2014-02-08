package com.esd.hesf.model;

/**
 * 企业类型类
 * 
 * @author Administrator
 * 
 */
public class CompanyEconomyType extends PrimaryKey_Int {

	private String companyEconomyType; // 类型名称

	public CompanyEconomyType() {
	}

	public CompanyEconomyType(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "CompanyEconomyType [companyEconomyType=" + companyEconomyType + "]";
	}

	public String getCompanyEconomyType() {
		return companyEconomyType;
	}

	public void setCompanyEconomyType(String companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

}