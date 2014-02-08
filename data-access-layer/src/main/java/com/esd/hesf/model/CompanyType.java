package com.esd.hesf.model;

/**
 * 公司类型类
 * 
 * @author Administrator
 * 
 */
public class CompanyType extends PrimaryKey_Int {

	private String companyType; // 公司类型

	public CompanyType() {
	}

	public CompanyType(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "CompanyType [companyType=" + companyType + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

}