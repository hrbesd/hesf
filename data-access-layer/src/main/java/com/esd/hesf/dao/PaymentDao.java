package com.esd.hesf.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.esd.hesf.model.Payment;

/**
 * 缴款明细表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface PaymentDao extends BaseDao<Payment> {

	/**
	 * 根据公司id/code, 年份 获得该公司某年度已缴款总额
	 * 
	 * @param payment对象
	 * @return
	 */
	BigDecimal retrieveAlreadyPay(Payment payment);

	/**
	 * 根据年份, (公司性质id, 地区code, 公司经济类型id)三选一查询已缴款总额
	 * 
	 * @param map中放入year
	 *            -String, companyType-Integer, companyEconomyType-Integer,
	 *            areaCode-String
	 * @return
	 */
	BigDecimal retrieveByCompanyAndYear(Map<String, Object> map);

	/**
	 * 根据 缴款年份(账目年份),公司id 来查询该公司 该年份已经开了几张票
	 * @param map	放year-账目年份, companyId-公司id
	 * @return
	 */
	Integer retireveCountByCompanyAndYear(Map<String,Object> map);
}