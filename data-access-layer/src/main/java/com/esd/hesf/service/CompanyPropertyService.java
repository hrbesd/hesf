package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.CompanyProperty;

/**
 * 企业经济类型service接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyPropertyService extends BaseService<CompanyProperty> {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	List<CompanyProperty> getAll();
}
