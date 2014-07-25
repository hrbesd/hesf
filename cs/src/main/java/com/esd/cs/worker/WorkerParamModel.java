/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import com.esd.hesf.model.PrimaryKey_Int;

/**
 * 残疾工人参数类
 * 
 * @author Administrator
 * 
 */
public class WorkerParamModel extends PrimaryKey_Int {
	private String companyId;// 公司id
	private String companyCode; // 公司档案号
	private String year;// 年度
	private String workerName; // 员工名
	private String workerHandicapCode;// 残疾证号
	private String workerGender;// 性别
	private String workerAge_1;// 年龄
	private String workerAge_2;// 年龄
	private String workerBirth;// 生日
	private String careerCard;// 就业证号
	private String phone;// 电话
	private String currentJob;// 当前职位
	private String workerHandicapType;// 残疾类型
	private String workerHandicapLevel;// 残疾等级
	private Integer page;
	private Integer rows;
	private Integer userId; // 操作用户id
	private Boolean isExceed;// 是否超过退休年龄
	private Boolean isOk; // 是否ok; 用来销补预订人数时, 则表示是否干部!
	private Boolean isCadre; // 是否干部, 用作普通员工查询

	@Override
	public String toString() {
		return "WorkerParamModel [companyId=" + companyId + ", companyCode="
				+ companyCode + ", year=" + year + ", workerName=" + workerName
				+ ", workerHandicapCode=" + workerHandicapCode
				+ ", workerGender=" + workerGender + ", workerAge_1="
				+ workerAge_1 + ", workerAge_2=" + workerAge_2
				+ ", workerBirth=" + workerBirth + ", careerCard=" + careerCard
				+ ", phone=" + phone + ", currentJob=" + currentJob
				+ ", workerHandicapType=" + workerHandicapType
				+ ", workerHandicapLevel=" + workerHandicapLevel + ", page="
				+ page + ", rows=" + rows + ", isExceed=" + isExceed + "]";
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getWorkerHandicapCode() {
		return workerHandicapCode;
	}

	public void setWorkerHandicapCode(String workerHandicapCode) {
		this.workerHandicapCode = workerHandicapCode;
	}

	public String getWorkerGender() {
		return workerGender;
	}

	public void setWorkerGender(String workerGender) {
		this.workerGender = workerGender;
	}

	public String getWorkerAge_1() {
		return workerAge_1;
	}

	public void setWorkerAge_1(String workerAge_1) {
		this.workerAge_1 = workerAge_1;
	}

	public String getWorkerAge_2() {
		return workerAge_2;
	}

	public void setWorkerAge_2(String workerAge_2) {
		this.workerAge_2 = workerAge_2;
	}

	public String getWorkerBirth() {
		return workerBirth;
	}

	public void setWorkerBirth(String workerBirth) {
		this.workerBirth = workerBirth;
	}

	public String getCareerCard() {
		return careerCard;
	}

	public void setCareerCard(String careerCard) {
		this.careerCard = careerCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getWorkerHandicapType() {
		return workerHandicapType;
	}

	public void setWorkerHandicapType(String workerHandicapType) {
		this.workerHandicapType = workerHandicapType;
	}

	public String getWorkerHandicapLevel() {
		return workerHandicapLevel;
	}

	public void setWorkerHandicapLevel(String workerHandicapLevel) {
		this.workerHandicapLevel = workerHandicapLevel;
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

	public Boolean getIsExceed() {
		return isExceed;
	}

	public void setIsExceed(Boolean isExceed) {
		this.isExceed = isExceed;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public Boolean getIsCadre() {
		return isCadre;
	}

	public void setIsCadre(Boolean isCadre) {
		this.isCadre = isCadre;
	}

}
