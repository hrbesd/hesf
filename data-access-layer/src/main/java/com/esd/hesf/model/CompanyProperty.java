package com.esd.hesf.model;

/**
 * 公司属性
 * 
 * @author Administrator
 * 
 */
public class CompanyProperty extends PrimaryKey_Int {
	private String companyProperty;

	public CompanyProperty() {
	}

	public CompanyProperty(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "CompanyProperty [companyProperty=" + companyProperty + ", getId()=" + getId() + "]";
	}

	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

}