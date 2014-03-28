package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.CompanyEconomyType;

/**
 * 企业经济类型service接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyEconomyTypeService extends BaseService<CompanyEconomyType> {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	List<CompanyEconomyType> getAll();
}
