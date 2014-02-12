package com.esd.hesf.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 年审表
 * 
 * @author Administrator
 * 
 */
public class Audit extends PrimaryKey_Int {

	private String year;
	private Company company; // 关联公司
	private Area area; // 地区
	private AuditProcessStatus auditProcessStatus; // 审核状态
	private BigDecimal amountPayable; // 应缴金额
	private BigDecimal reductionAmount;// 减缴金额
	private BigDecimal actualAmount;// 实际应缴金额
	private BigDecimal payAmount;// 实缴总金额
	private BigDecimal remainAmount;// 上年度未缴金额
	private BigDecimal complementAmount; // 补缴金额
	private BigDecimal delayPayAmount; // 滞纳金
	private Integer initAuditUserId;// 初审人id
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date initAuditDate;// 初审日期
	private String initAuditComment; // 初审人意见
	private Integer verifyAuditUserId;// 复审人用户ID
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date verifyAuditDate;// 复审日期
	private String verifyAuditComment; // 初审人意见
	private String remark;// 备注
	private Boolean isExempt; // 是否免缴 false--免除, true--不免除
	private Integer reductionType; // 减免缓类型
	private String reducionApplyUser;// 减免申请人
	private Date reductionDate;// 减免申请时间
	private String reductionReason;// 减免原因
	private String reductionAnswerUser;// 减免申请答复人
	private String reductionAnswerDate;// 答复日期
	private String reductionAnswerOption;// 答复意见
	private String reductionRemark;// 减免缓 备注

	public Audit() {
	}

	public Audit(int id) {
		super.setId(id);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public AuditProcessStatus getAuditProcessStatus() {
		return auditProcessStatus;
	}

	public void setAuditProcessStatus(AuditProcessStatus auditProcessStatus) {
		this.auditProcessStatus = auditProcessStatus;
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

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public BigDecimal getComplementAmount() {
		return complementAmount;
	}

	public void setComplementAmount(BigDecimal complementAmount) {
		this.complementAmount = complementAmount;
	}

	public BigDecimal getDelayPayAmount() {
		return delayPayAmount;
	}

	public void setDelayPayAmount(BigDecimal delayPayAmount) {
		this.delayPayAmount = delayPayAmount;
	}

	public Integer getInitAuditUserId() {
		return initAuditUserId;
	}

	public void setInitAuditUserId(Integer initAuditUserId) {
		this.initAuditUserId = initAuditUserId;
	}

	public Date getInitAuditDate() {
		return initAuditDate;
	}

	public void setInitAuditDate(Date initAuditDate) {
		this.initAuditDate = initAuditDate;
	}

	public String getInitAuditComment() {
		return initAuditComment;
	}

	public void setInitAuditComment(String initAuditComment) {
		this.initAuditComment = initAuditComment;
	}

	public Integer getVerifyAuditUserId() {
		return verifyAuditUserId;
	}

	public void setVerifyAuditUserId(Integer verifyAuditUserId) {
		this.verifyAuditUserId = verifyAuditUserId;
	}

	public Date getVerifyAuditDate() {
		return verifyAuditDate;
	}

	public void setVerifyAuditDate(Date verifyAuditDate) {
		this.verifyAuditDate = verifyAuditDate;
	}

	public String getVerifyAuditComment() {
		return verifyAuditComment;
	}

	public void setVerifyAuditComment(String verifyAuditComment) {
		this.verifyAuditComment = verifyAuditComment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsExempt() {
		return isExempt;
	}

	public void setIsExempt(Boolean isExempt) {
		this.isExempt = isExempt;
	}

	public Integer getReductionType() {
		return reductionType;
	}

	public void setReductionType(Integer reductionType) {
		this.reductionType = reductionType;
	}

	public String getReducionApplyUser() {
		return reducionApplyUser;
	}

	public void setReducionApplyUser(String reducionApplyUser) {
		this.reducionApplyUser = reducionApplyUser;
	}

	public Date getReductionDate() {
		return reductionDate;
	}

	public void setReductionDate(Date reductionDate) {
		this.reductionDate = reductionDate;
	}

	public String getReductionReason() {
		return reductionReason;
	}

	public void setReductionReason(String reductionReason) {
		this.reductionReason = reductionReason;
	}

	public String getReductionAnswerUser() {
		return reductionAnswerUser;
	}

	public void setReductionAnswerUser(String reductionAnswerUser) {
		this.reductionAnswerUser = reductionAnswerUser;
	}

	public String getReductionAnswerDate() {
		return reductionAnswerDate;
	}

	public void setReductionAnswerDate(String reductionAnswerDate) {
		this.reductionAnswerDate = reductionAnswerDate;
	}

	public String getReductionAnswerOption() {
		return reductionAnswerOption;
	}

	public void setReductionAnswerOption(String reductionAnswerOption) {
		this.reductionAnswerOption = reductionAnswerOption;
	}

	public String getReductionRemark() {
		return reductionRemark;
	}

	public void setReductionRemark(String reductionRemark) {
		this.reductionRemark = reductionRemark;
	}

	@Override
	public String toString() {
		return "Audit [year=" + year + ", company=" + company + ", area=" + area + ", auditProcessStatus=" + auditProcessStatus + ", amountPayable=" + amountPayable + ", reductionAmount="
				+ reductionAmount + ", actualAmount=" + actualAmount + ", payAmount=" + payAmount + ", remainAmount=" + remainAmount + ", initAuditUserId=" + initAuditUserId + ", initAuditDate="
				+ initAuditDate + ", verifyAuditUserId=" + verifyAuditUserId + ", verifyAuditDate=" + verifyAuditDate + ", remark=" + remark + ", isExempt=" + isExempt + ", reductionType="
				+ reductionType + ", reducionApplyUser=" + reducionApplyUser + ", reductionDate=" + reductionDate + ", reductionReason=" + reductionReason + ", reductionAnswerUser="
				+ reductionAnswerUser + ", reductionAnswerDate=" + reductionAnswerDate + ", reductionAnswerOption=" + reductionAnswerOption + ", reductionRemark=" + reductionRemark + "]";
	}

}