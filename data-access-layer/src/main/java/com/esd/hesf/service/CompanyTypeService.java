package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.CompanyType;

/**
 * 公司类型 service接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyTypeService extends BaseService<CompanyType> {

	/**
	 * 获得所有数据 , 测试用
	 * @return
	 */
	List<CompanyType> getAll();
}
