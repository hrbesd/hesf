package com.esd.hesf.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 审核参数 类
 * 
 * @author Administrator
 * 
 */
public class AuditParameter extends PrimaryKey_Int {
	private String year;
	private Area area; // 地区
	private BigDecimal putScale;// 安置比例
	private BigDecimal averageSalary;// 当年平均工资
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date auditStartDate; // 审核开始日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date auditCloseDate; // 审核截止日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date auditDelayDate; // 滞纳金开始日期
	private BigDecimal auditDelayRate; // 滞纳金征收比例 按天计算
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payCloseDate;// 支付截止日期
	private Integer payLimitDays;// 支付限制天数
	private Integer decimalCount;// 小数位数
	private Integer retireAgeMale; // 退休年龄--职工-男-默认为61
	private Integer retireAgeFemale; // 退休年龄-职工-女-默认为51
	private Integer retireAgeCadreMale;	// 退休年龄-干部-男-默认为61
	private Integer retireAgeCadreFemale; // 退休年龄-干部-女-默认为55
	private Integer eyeOne;
	private Integer eyeTwo;
	private Integer eyeThree;
	private Integer eyeFour;
	private Integer hearingOne;
	private Integer hearingTwo;
	private Integer hearingThree;
	private Integer hearingFour;
	private Integer bodyOne;
	private Integer bodyTwo;
	private Integer bodyThree;
	private Integer bodyFour;
	private Integer speakOne;
	private Integer speakTwo;
	private Integer speakThree;
	private Integer speakFour;
	private Integer intelligenceOne;
	private Integer intelligenceTwo;
	private Integer intelligenceThree;
	private Integer intelligenceFour;
	private Integer mentalOne;
	private Integer mentalTwo;
	private Integer mentalThree;
	private Integer mentalFour;
	private Integer multiOne;
	private Integer multiTwo;
	private Integer multiThree;
	private Integer multiFour;

	@Override
	public String toString() {
		return "AuditParameter [year=" + year + ", area=" + area + ", putScale=" + putScale + ", averageSalary=" + averageSalary + ", auditCloseDate=" + auditCloseDate + ", payCloseDate="
				+ payCloseDate + ", payLimitDays=" + payLimitDays + ", decimalCount=" + decimalCount + ", eyeOne=" + eyeOne + ", eyeTwo=" + eyeTwo + ", eyeThree=" + eyeThree + ", eyeFour=" + eyeFour
				+ ", hearingOne=" + hearingOne + ", hearingTwo=" + hearingTwo + ", hearingThree=" + hearingThree + ", hearingFour=" + hearingFour + ", bodyOne=" + bodyOne + ", bodyTwo=" + bodyTwo
				+ ", bodyThree=" + bodyThree + ", bodyFour=" + bodyFour + ", speakOne=" + speakOne + ", speakTwo=" + speakTwo + ", speakThree=" + speakThree + ", speakFour=" + speakFour
				+ ", intelligenceOne=" + intelligenceOne + ", intelligenceTwo=" + intelligenceTwo + ", intelligenceThree=" + intelligenceThree + ", intelligenceFour=" + intelligenceFour
				+ ", mentalOne=" + mentalOne + ", mentalTwo=" + mentalTwo + ", mentalThree=" + mentalThree + ", mentalFour=" + mentalFour + ", multiOne=" + multiOne + ", multiTwo=" + multiTwo
				+ ", multiThree=" + multiThree + ", multiFour=" + multiFour + "]";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public BigDecimal getPutScale() {
		return putScale;
	}

	public void setPutScale(BigDecimal putScale) {
		this.putScale = putScale;
	}

	public BigDecimal getAverageSalary() {
		return averageSalary;
	}

	public void setAverageSalary(BigDecimal averageSalary) {
		this.averageSalary = averageSalary;
	}

	public Date getAuditStartDate() {
		return auditStartDate;
	}

	public void setAuditStartDate(Date auditStartDate) {
		this.auditStartDate = auditStartDate;
	}

	public Date getAuditCloseDate() {
		return auditCloseDate;
	}

	public void setAuditCloseDate(Date auditCloseDate) {
		this.auditCloseDate = auditCloseDate;
	}

	public Date getAuditDelayDate() {
		return auditDelayDate;
	}

	public void setAuditDelayDate(Date auditDelayDate) {
		this.auditDelayDate = auditDelayDate;
	}

	public BigDecimal getAuditDelayRate() {
		return auditDelayRate;
	}

	public void setAuditDelayRate(BigDecimal auditDelayRate) {
		this.auditDelayRate = auditDelayRate;
	}

	public Date getPayCloseDate() {
		return payCloseDate;
	}

	public void setPayCloseDate(Date payCloseDate) {
		this.payCloseDate = payCloseDate;
	}

	public Integer getPayLimitDays() {
		return payLimitDays;
	}

	public void setPayLimitDays(Integer payLimitDays) {
		this.payLimitDays = payLimitDays;
	}

	public Integer getDecimalCount() {
		return decimalCount;
	}

	public void setDecimalCount(Integer decimalCount) {
		this.decimalCount = decimalCount;
	}

	public Integer getRetireAgeMale() {
		return retireAgeMale;
	}

	public void setRetireAgeMale(Integer retireAgeMale) {
		this.retireAgeMale = retireAgeMale;
	}

	public Integer getRetireAgeFemale() {
		return retireAgeFemale;
	}

	public void setRetireAgeFemale(Integer retireAgeFemale) {
		this.retireAgeFemale = retireAgeFemale;
	}

	public Integer getRetireAgeCadreMale() {
		return retireAgeCadreMale;
	}

	public void setRetireAgeCadreMale(Integer retireAgeCadreMale) {
		this.retireAgeCadreMale = retireAgeCadreMale;
	}

	public Integer getRetireAgeCadreFemale() {
		return retireAgeCadreFemale;
	}

	public void setRetireAgeCadreFemale(Integer retireAgeCadreFemale) {
		this.retireAgeCadreFemale = retireAgeCadreFemale;
	}

	public Integer getEyeOne() {
		return eyeOne;
	}

	public void setEyeOne(Integer eyeOne) {
		this.eyeOne = eyeOne;
	}

	public Integer getEyeTwo() {
		return eyeTwo;
	}

	public void setEyeTwo(Integer eyeTwo) {
		this.eyeTwo = eyeTwo;
	}

	public Integer getEyeThree() {
		return eyeThree;
	}

	public void setEyeThree(Integer eyeThree) {
		this.eyeThree = eyeThree;
	}

	public Integer getEyeFour() {
		return eyeFour;
	}

	public void setEyeFour(Integer eyeFour) {
		this.eyeFour = eyeFour;
	}

	public Integer getHearingOne() {
		return hearingOne;
	}

	public void setHearingOne(Integer hearingOne) {
		this.hearingOne = hearingOne;
	}

	public Integer getHearingTwo() {
		return hearingTwo;
	}

	public void setHearingTwo(Integer hearingTwo) {
		this.hearingTwo = hearingTwo;
	}

	public Integer getHearingThree() {
		return hearingThree;
	}

	public void setHearingThree(Integer hearingThree) {
		this.hearingThree = hearingThree;
	}

	public Integer getHearingFour() {
		return hearingFour;
	}

	public void setHearingFour(Integer hearingFour) {
		this.hearingFour = hearingFour;
	}

	public Integer getBodyOne() {
		return bodyOne;
	}

	public void setBodyOne(Integer bodyOne) {
		this.bodyOne = bodyOne;
	}

	public Integer getBodyTwo() {
		return bodyTwo;
	}

	public void setBodyTwo(Integer bodyTwo) {
		this.bodyTwo = bodyTwo;
	}

	public Integer getBodyThree() {
		return bodyThree;
	}

	public void setBodyThree(Integer bodyThree) {
		this.bodyThree = bodyThree;
	}

	public Integer getBodyFour() {
		return bodyFour;
	}

	public void setBodyFour(Integer bodyFour) {
		this.bodyFour = bodyFour;
	}

	public Integer getSpeakOne() {
		return speakOne;
	}

	public void setSpeakOne(Integer speakOne) {
		this.speakOne = speakOne;
	}

	public Integer getSpeakTwo() {
		return speakTwo;
	}

	public void setSpeakTwo(Integer speakTwo) {
		this.speakTwo = speakTwo;
	}

	public Integer getSpeakThree() {
		return speakThree;
	}

	public void setSpeakThree(Integer speakThree) {
		this.speakThree = speakThree;
	}

	public Integer getSpeakFour() {
		return speakFour;
	}

	public void setSpeakFour(Integer speakFour) {
		this.speakFour = speakFour;
	}

	public Integer getIntelligenceOne() {
		return intelligenceOne;
	}

	public void setIntelligenceOne(Integer intelligenceOne) {
		this.intelligenceOne = intelligenceOne;
	}

	public Integer getIntelligenceTwo() {
		return intelligenceTwo;
	}

	public void setIntelligenceTwo(Integer intelligenceTwo) {
		this.intelligenceTwo = intelligenceTwo;
	}

	public Integer getIntelligenceThree() {
		return intelligenceThree;
	}

	public void setIntelligenceThree(Integer intelligenceThree) {
		this.intelligenceThree = intelligenceThree;
	}

	public Integer getIntelligenceFour() {
		return intelligenceFour;
	}

	public void setIntelligenceFour(Integer intelligenceFour) {
		this.intelligenceFour = intelligenceFour;
	}

	public Integer getMentalOne() {
		return mentalOne;
	}

	public void setMentalOne(Integer mentalOne) {
		this.mentalOne = mentalOne;
	}

	public Integer getMentalTwo() {
		return mentalTwo;
	}

	public void setMentalTwo(Integer mentalTwo) {
		this.mentalTwo = mentalTwo;
	}

	public Integer getMentalThree() {
		return mentalThree;
	}

	public void setMentalThree(Integer mentalThree) {
		this.mentalThree = mentalThree;
	}

	public Integer getMentalFour() {
		return mentalFour;
	}

	public void setMentalFour(Integer mentalFour) {
		this.mentalFour = mentalFour;
	}

	public Integer getMultiOne() {
		return multiOne;
	}

	public void setMultiOne(Integer multiOne) {
		this.multiOne = multiOne;
	}

	public Integer getMultiTwo() {
		return multiTwo;
	}

	public void setMultiTwo(Integer multiTwo) {
		this.multiTwo = multiTwo;
	}

	public Integer getMultiThree() {
		return multiThree;
	}

	public void setMultiThree(Integer multiThree) {
		this.multiThree = multiThree;
	}

	public Integer getMultiFour() {
		return multiFour;
	}

	public void setMultiFour(Integer multiFour) {
		this.multiFour = multiFour;
	}

}