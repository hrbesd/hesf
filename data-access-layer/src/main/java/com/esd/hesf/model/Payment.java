package com.esd.hesf.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 付款信息
 * 
 * @author Administrator
 * 
 */
public class Payment extends PrimaryKey_Int {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate;
	private BigDecimal paymentMoney;
	private User paymentPerson; // 缴款操作人
	private Company paymentCompany; // 付款公司
	private String paymentBill; // 付款票据号
	private PaymentType paymentType; // 缴款方式 关联缴款类型表
	private String remark;
	private Integer auditId; // 审核表 id

	public Payment() {
	}

	public Payment(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Payment [paymentDate=" + paymentDate + ", paymentMoney=" + paymentMoney + ", paymentPerson=" + paymentPerson + ", paymentCompany=" + paymentCompany + ", paymentBill=" + paymentBill
				+ ", paymentType=" + paymentType + ", remark=" + remark + ", auditId=" + auditId + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public User getPaymentPerson() {
		return paymentPerson;
	}

	public void setPaymentPerson(User paymentPerson) {
		this.paymentPerson = paymentPerson;
	}

	public Company getPaymentCompany() {
		return paymentCompany;
	}

	public void setPaymentCompany(Company paymentCompany) {
		this.paymentCompany = paymentCompany;
	}

	public String getPaymentBill() {
		return paymentBill;
	}

	public void setPaymentBill(String paymentBill) {
		this.paymentBill = paymentBill;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

}