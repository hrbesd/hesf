package com.esd.hesf.model;

/**
 * 特殊缴款方式类别
 * 
 * @author Administrator
 * 
 */
public class PaymentExceptional extends PrimaryKey_Int {

	private String paymentExceptional;

	private String value;

	@Override
	public String toString() {
		return "PaymentExceptional [paymentExceptional=" + paymentExceptional + "]";
	}

	public String getPaymentExceptional() {
		return paymentExceptional;
	}

	public void setPaymentExceptional(String paymentExceptional) {
		this.paymentExceptional = paymentExceptional;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
