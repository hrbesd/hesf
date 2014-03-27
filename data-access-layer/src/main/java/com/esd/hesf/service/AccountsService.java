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

}
