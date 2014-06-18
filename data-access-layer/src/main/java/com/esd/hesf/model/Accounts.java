package com.esd.hesf.model;

import java.math.BigDecimal;

/**
 * 缴款账目类
 * 
 * @author Administrator
 * 
 */
public class Accounts extends PrimaryKey_Int {

	private String year; // 出账年份
	private Company company; // 公司对象
	private Audit audit; // 审核审核对象
	private BigDecimal totalMoney; // 总应缴款金额
	private Boolean isFinished; // 是否缴款完毕
	private AuditProcessStatus auditProcessStatus; // 审核进程状态 对象
	private Boolean isReceipt; // 是否已经开票了...

	@Override
	public String toString() {
		return "Accounts [year=" + year + ", company=" + company + ", audit="
				+ audit + ", totalMoney=" + totalMoney + ", isFinished="
				+ isFinished + ", auditProcessStatus=" + auditProcessStatus
				+ "]";
	}

	public Accounts() {
	}

	public Accounts(Integer id) {
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

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public AuditProcessStatus getAuditProcessStatus() {
		return auditProcessStatus;
	}

	public void setAuditProcessStatus(AuditProcessStatus auditProcessStatus) {
		this.auditProcessStatus = auditProcessStatus;
	}

	public Boolean getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(Boolean isReceipt) {
		this.isReceipt = isReceipt;
	}

}