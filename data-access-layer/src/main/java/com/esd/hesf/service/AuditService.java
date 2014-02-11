package com.esd.hesf.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Payment;

/**
 * 审核 service接口
 * 
 * @author Administrator
 * 
 */
public interface AuditService extends BaseService<Audit> {

	/**
	 * 测试方法
	 * 
	 * @return
	 */
	public List<Audit> getAll();
	
	/**
	 * 根据年份year, 公司档案号companyCode获得一条审核数据
	 * @param year
	 * @param companyCode
	 * @return
	 */
	Audit getByPrimaryKey(String year,String companyCode);
	

	/**
	 * 初始化审计参数--包括复制上年度公司信息和想审计表中插入要审计的公司数据
	 * 
	 * @param currentYear
	 *            本年度
	 * @param lastYear
	 *            上年度
	 * @return
	 */
	public boolean initAuditData(String currentYear, String lastYear);

	/**
	 * 多条件查询 公司审核数据 数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的样例 测试样例见
	 *            com.esd.cs.TestController 的测试方法 23
	 * @return
	 */
	PaginationRecordsAndNumber<Audit, Number> getByMultiCondition(Map<String, Object> map);

	/**
	 * 根据审核id, 得到已经付款的金额
	 * 
	 * @param auditId
	 * @return
	 */
	BigDecimal getAlreadyPayByAudit(int auditId);

	/**
	 * 根据审核id, 得到缴款列表
	 * 
	 * @param auditId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecord(Integer auditId, Integer page, Integer pageSize);
}
