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
	private Integer companyId; // 公司id
	private String currentJob; // 当前工作

	@Override
	public String toString() {
		return "CompanyYearWorker [year=" + year + ", workerId=" + workerId + ", companyId=" + companyId + ", currentJob=" + currentJob + "]";
	}

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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

}