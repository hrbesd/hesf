package com.esd.hesf.model;

/**
 * 缴款方式
 * 
 * @author Administrator
 * 
 */
public class PaymentType extends PrimaryKey_Int {

	private String text; // 缴款方式名称

	@Override
	public String toString() {
		return "PaymentType [text=" + text + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public PaymentType() {
	}

	public PaymentType(int id) {
		super.setId(id);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}