package com.esd.hesf.dao;

import java.math.BigDecimal;

import com.esd.hesf.model.Payment;

/**
 * 交款表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface PaymentDao extends BaseDao<Payment> {

	/**
	 * 根据审核表id 获得缴款总数
	 * 
	 * @param auditId
	 * @return
	 */
	BigDecimal retrieveAlreadyPay(Payment payment);

}