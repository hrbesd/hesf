package com.esd.hesf.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Payment;

/**
 * 缴款 service接口
 * 
 * @author Administrator
 * 
 */
public interface PaymentService extends BaseService<Payment> {

	/**
	 * 获取所有数据 测试用
	 * 
	 * @return
	 */
	public List<Payment> getAll();

	/**
	 * 根据账目id, 得到已经付款的金额
	 * 
	 * @param auditId	大于0的正整数
	 * @return
	 */
	BigDecimal getAlreadyPayByAudit1(Integer auditId);
	
	/**
	 * 根据公司id, 得到已经付款的金额
	 * 
	 * @param companyId	公司id
	 * @return
	 */
	BigDecimal getAlreadyPayByCompany(Integer companyId);

	/**
	 * 根据审核id, 得到缴款列表
	 * 
	 * @param auditId	大于0的正整数
	 * @param page	页数
	 * @param pageSize	返回量
	 * @return
	 */
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecordByAudit(Integer auditId, Integer page, Integer pageSize);

	/**
	 * 根据公司id, 得到缴款列表
	 * 
	 * @param companyId	公司id
	 * @param page	页数
	 * @param pageSize	返回量
	 * @return
	 */
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecordByCompany(Integer companyId, Integer page, Integer pageSize);
	
	/**
	 * 多条件查询  缴款数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的example_payment()样例 
	 *            测试样例见 com.esd.cs.TestController 的测试方法 26
	 * @return
	 */
	PaginationRecordsAndNumber<Payment, Number> getByMultiCondition(Map<String, Object> map);
	
	/**
	 * 导出缴款数据
	 * @param FilePath
	 * @param paymentList
	 * @return
	 */
	public boolean createPaymentExcel(String FilePath, List<Payment> paymentList,Integer page,Integer pageSize);
}
