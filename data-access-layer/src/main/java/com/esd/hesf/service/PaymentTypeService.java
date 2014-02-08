package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.PaymentType;

/**
 * 缴款类型 servcie 接口
 * 
 * @author Administrator
 * 
 */
public interface PaymentTypeService extends BaseService<PaymentType> {

	/**
	 * 获取所有方法, 测试用
	 * 
	 * @return
	 */
	public List<PaymentType> getAll();

}
