package com.esd.cs.payment;

import java.util.Date;

import org.springframework.format.annotation.NumberFormat;

public class Payments {

	private Integer id;
	@NumberFormat(pattern = "yyy-MM-dd")
	private String paymentDate;
	private String paymentMoney;
	private String paymentPerson; // 缴款操作人
	private String paymentBill; // 付款票据号
	private String paymentType; // 缴款方式 关联缴款类型表
	private String remark;

	private Integer auditId; // 审核表 id
	private String companyId; // 公司ID

	@Override
	public String toString() {
		return "Payments [id=" + id + ", paymentDate=" + paymentDate + ", paymentMoney=" + paymentMoney + ", paymentPerson=" + paymentPerson + ", paymentBill=" + paymentBill + ", paymentType="
				+ paymentType + ", remark=" + remark + ", auditId=" + auditId + ", companyId=" + companyId + "]";
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(String paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public String getPaymentPerson() {
		return paymentPerson;
	}

	public void setPaymentPerson(String paymentPerson) {
		this.paymentPerson = paymentPerson;
	}

	public String getPaymentBill() {
		return paymentBill;
	}

	public void setPaymentBill(String paymentBill) {
		this.paymentBill = paymentBill;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
