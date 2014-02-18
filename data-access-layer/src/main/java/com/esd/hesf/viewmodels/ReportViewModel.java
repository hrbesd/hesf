package com.esd.hesf.viewmodels;

import java.math.BigDecimal;

/**
 * 统计报表 视图模型
 * 
 * @author Administrator
 * 
 */
public class ReportViewModel {

	private String reportName; // 报表依据类型名, 如单位类型, 地区, 经济类型等 11
	private Integer unitNum; // 单位总数 11
	private Integer empTotal; // 单位总人数11
	private Integer predictTotal; // 应安排总人数11
	private Integer unAudit; // 待初审单位数11
	private Integer unReAudit; // 已初审, 待复核单位数11
	private Integer auditOk;// 已复核, 达标单位数11
	private Integer unauditOk; // 已复核, 未达标单位数11
	private BigDecimal shouldTotal; // 应安排人数11
	private BigDecimal alreadyTotal; // 已经安排人数11
	private BigDecimal lessTotal; // 少安排人数
	private BigDecimal amountPayable; // 应缴金额11
	private BigDecimal reductionAmount; // 减免金额11
	private BigDecimal actualAmount; // 实际应缴金额11
	private BigDecimal alreadyAmount; // 已缴金额
	private Integer companyType; // 公司类型id, 不做为前台显示字段 xxx
	private String areaCode; // 地区code, 不作为前台显示字段 xxx
	private Integer companyEconomyType; // 经济类型id, 不做为前台显示字段 xxx

	@Override
	public String toString() {
		return "ReportViewModel [reportName=" + reportName + ", unitNum=" + unitNum + ", empTotal=" + empTotal + ", unAudit=" + unAudit + ", unReAudit=" + unReAudit + ", auditOk=" + auditOk
				+ ", unauditOk=" + unauditOk + ", shouldTotal=" + shouldTotal + ", alreadyTotal=" + alreadyTotal + ", lessTotal=" + lessTotal + ", amountPayable=" + amountPayable
				+ ", reductionAmount=" + reductionAmount + ", actualAmount=" + actualAmount + ", alreadyAmount=" + alreadyAmount + "]";
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		this.unitNum = unitNum;
	}

	public Integer getEmpTotal() {
		return empTotal;
	}

	public void setEmpTotal(Integer empTotal) {
		this.empTotal = empTotal;
	}

	public Integer getPredictTotal() {
		return predictTotal;
	}

	public void setPredictTotal(Integer predictTotal) {
		this.predictTotal = predictTotal;
	}

	public Integer getUnAudit() {
		return unAudit;
	}

	public void setUnAudit(Integer unAudit) {
		this.unAudit = unAudit;
	}

	public Integer getUnReAudit() {
		return unReAudit;
	}

	public void setUnReAudit(Integer unReAudit) {
		this.unReAudit = unReAudit;
	}

	public Integer getAuditOk() {
		return auditOk;
	}

	public void setAuditOk(Integer auditOk) {
		this.auditOk = auditOk;
	}

	public Integer getUnauditOk() {
		return unauditOk;
	}

	public void setUnauditOk(Integer unauditOk) {
		this.unauditOk = unauditOk;
	}

	public BigDecimal getShouldTotal() {
		return shouldTotal;
	}

	public void setShouldTotal(BigDecimal shouldTotal) {
		this.shouldTotal = shouldTotal;
	}

	public BigDecimal getAlreadyTotal() {
		return alreadyTotal;
	}

	public void setAlreadyTotal(BigDecimal alreadyTotal) {
		this.alreadyTotal = alreadyTotal;
	}

	public BigDecimal getLessTotal() {
		return lessTotal;
	}

	public void setLessTotal(BigDecimal lessTotal) {
		this.lessTotal = lessTotal;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public BigDecimal getReductionAmount() {
		return reductionAmount;
	}

	public void setReductionAmount(BigDecimal reductionAmount) {
		this.reductionAmount = reductionAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getAlreadyAmount() {
		return alreadyAmount;
	}

	public void setAlreadyAmount(BigDecimal alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getCompanyEconomyType() {
		return companyEconomyType;
	}

	public void setCompanyEconomyType(Integer companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

}
