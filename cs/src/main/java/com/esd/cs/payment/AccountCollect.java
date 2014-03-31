package com.esd.cs.payment;

import java.math.BigDecimal;

import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;

public class AccountCollect {
	private String year; // 审核年度
	private Company company; // 公司对象
	private Audit audit; // 审核审核对象
	private BigDecimal totalMoney; // 总应缴款金额
	private Boolean isFinished; // 是否缴款完毕
	private AuditProcessStatus auditProcessStatus; // 审核进程状态 对象

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

}
