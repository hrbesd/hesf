package com.esd.hesf.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 缴款表
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date billPrintDate; // 打票日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date billExchangeDate; // 换票日期
	private Boolean billReturn; // 返票
	private Boolean billFinance; // 财政
	private Boolean billObsolete; // 是否 作废票据 默认为否
	private PaymentExceptional paymentExceptional; // 特殊缴款方式类别
	private String remark; // 备注
	private Audit audit; // 缴款所属于的审核对象

	public Payment() {
	}

	public Payment(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Payment [paymentDate=" + paymentDate + ", paymentMoney=" + paymentMoney + ", paymentPerson=" + paymentPerson + ", paymentCompany=" + paymentCompany + ", paymentBill=" + paymentBill
				+ ", paymentType=" + paymentType + ", billPrintDate=" + billPrintDate + ", billExchangeDate=" + billExchangeDate + ", billReturn=" + billReturn + ", billFinance=" + billFinance
				+ ", billObsolete=" + billObsolete + ", paymentExceptional=" + paymentExceptional + ", remark=" + remark + ", audit=" + audit + "]";
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

	public Date getBillPrintDate() {
		return billPrintDate;
	}

	public void setBillPrintDate(Date billPrintDate) {
		this.billPrintDate = billPrintDate;
	}

	public Date getBillExchangeDate() {
		return billExchangeDate;
	}

	public void setBillExchangeDate(Date billExchangeDate) {
		this.billExchangeDate = billExchangeDate;
	}

	public Boolean getBillReturn() {
		return billReturn;
	}

	public void setBillReturn(Boolean billReturn) {
		this.billReturn = billReturn;
	}

	public Boolean getBillFinance() {
		return billFinance;
	}

	public void setBillFinance(Boolean billFinance) {
		this.billFinance = billFinance;
	}

	public Boolean getBillObsolete() {
		return billObsolete;
	}

	public void setBillObsolete(Boolean billObsolete) {
		this.billObsolete = billObsolete;
	}

	public PaymentExceptional getPaymentExceptional() {
		return paymentExceptional;
	}

	public void setPaymentExceptional(PaymentExceptional paymentExceptional) {
		this.paymentExceptional = paymentExceptional;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

}