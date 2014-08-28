package com.esd.hesf.dao;

import java.math.BigDecimal;
import java.util.List;
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
	 * 根据账目年year, 公司id 删除该公司该年所开出的所有票
	 * @param map
	 * @return
	 */
	int deleteByCompanyAndYear(Map<String,Object> map);
	
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
	 * 根据 payment的各个字段 计算有几条数据
	 * 
	 * @param payment
	 *            根据开票的各种
	 * @return
	 */
	Integer retireveCountByPayment(Payment payment);

	/**
	 * 根据多个id查询缴款信息列表, map中放入array数组, 或者list集合等 key值必须为: array
	 * 
	 * @param map
	 * @return
	 */
	List<Payment> retrieveByPrimaryKeys(Map<String, Object> map);

}