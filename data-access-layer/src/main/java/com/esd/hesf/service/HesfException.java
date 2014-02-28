package com.esd.hesf.service;

/**
 * 自定义异常
 * 
 * @author Administrator
 * 
 */
public class HesfException extends Exception {

	/**
	 * 空值情况
	 */
	public static final String type_null = " 不能为空!";
	/**
	 * 数字小于
	 */
	public static final String type_number_negative = " 不能为小于等于零!";

	/**
	 * 
	 */
	public static final String type_fail = " 操作失败!";
	private static final long serialVersionUID = 1L;
	private String msg; // 提示信息

	public HesfException(String msg) {
		this.msg = msg;
	}

	public HesfException(String variableName, String type) {
		if (type == null || "".equals(type)) {
			new HesfException("type", HesfException.type_null).printStackTrace();
		}
		this.msg = variableName + type;
	}

	@Override
	public String toString() {
		return "HesfException: " + msg;
	}

}
