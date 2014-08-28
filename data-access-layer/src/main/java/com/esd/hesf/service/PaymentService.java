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
	List<Payment> getAll();
	
	/**
	 * 根据公司id, 账目年份 删除该公司今年所开的所有票
	 * @param companyId
	 * @param year
	 * @return
	 */
	Integer deleteByCompanyAndYear(Integer companyId,String year);
	

	/**
	 * 根据公司档案code,账目年限,审核年份, 得到已缴款数
	 * 
	 * @param year
	 * @param companyCode
	 * @return
	 */
	BigDecimal getEffPaid(String auditYear, String accountsYear,
			String companyCode);

	/**
	 * 根据公司id,账目年限,审核年份, 得到已缴款数
	 * 
	 * @param year
	 * @param companyId
	 * @return
	 */
	BigDecimal getEffPaid(String auditYear, String accountsYear,
			Integer companyId);

	/**
	 * 根据年份year, 公司id, 得到缴款明细列表
	 * 
	 * @param year
	 *            年份
	 * @param companyId
	 *            公司id
	 * @param page
	 *            页数
	 * @param pageSize
	 *            返回量
	 * @return
	 */
	PaginationRecordsAndNumber<Payment, Number> getPaymentRecords(String year,
			Integer companyId, Integer page, Integer pageSize);

	/**
	 * 根据年份, 公司档案code, 得到缴款明细列表
	 * 
	 * @param year
	 *            年份
	 * @param companyCode
	 *            公司档案code
	 * @param page
	 *            页数
	 * @param pageSize
	 *            返回量
	 * @return
	 */
	PaginationRecordsAndNumber<Payment, Number> getPaymentRecords(String year,
			String companyCode, Integer page, Integer pageSize);

	/**
	 * 多条件查询 缴款数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的example_payment()样例 测试样例见
	 *            com.esd.cs.TestController 的测试方法 26
	 * @return
	 */
	PaginationRecordsAndNumber<Payment, Number> getByMultiCondition(
			Map<String, Object> map);

	/**
	 * 导出缴款数据
	 * 
	 * @param FilePath
	 * @param paymentList
	 * @return
	 */
	boolean createPaymentExcel(String FilePath, List<Payment> paymentList,
			Integer page, Integer pageSize);

	/**
	 * 根据 缴款年份(账目年份),公司id 来查询该公司 该年份已经开了几张票
	 * 
	 * @param year
	 * @param companyId
	 * @return
	 */
	Integer getCountByCompanyAndYear(String year, Integer companyId);

	/**
	 * 根据 缴款的各个字段属性 来查询该公司 该年份已经开了几张票
	 * 
	 * @param payment
	 * @return
	 */
	Integer getCountByCompanyAndYear(Payment payment);

	/**
	 * 根据多个id, 查询缴款数据列表
	 * 
	 * @param ids
	 * @return
	 */
	List<Payment> getByIds(Integer[] ids);
	
	
}
