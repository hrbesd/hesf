package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.Accounts;

/**
 * 缴款账目 service接口
 * 
 * @author Administrator
 * 
 */
public interface AccountsService extends BaseService<Accounts> {

	/**
	 * 获取所有数据 测试用
	 * 
	 * @return
	 */
	public List<Accounts> getAll();

	/**
	 * 根据年份, 公司id, 审核id获得账目列表
	 * @param year
	 * @param companyId
	 * @param auditId
	 * @return
	 */
	public List<Accounts> get(String year, Integer companyId, Integer auditId);
	
	/**
	 * 根据年份, 公司档案code, 审核id获得账目列表
	 * @param year
	 * @param companyCode
	 * @param auditId
	 * @return
	 */
	public List<Accounts> get(String year, String companyCode, Integer auditId);
}
