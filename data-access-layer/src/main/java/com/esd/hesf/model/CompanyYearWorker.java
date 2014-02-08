package com.esd.hesf.model;

/**
 * 企业--残疾员工 关系表
 * 
 * @author Administrator
 * 
 */
public class CompanyYearWorker extends PrimaryKey_Int {

	private String year; // 年份
	private Integer workerId; // 员工id
	private String companyCode; // 公司档案号code
	private String currentJob; // 当前工作

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

}