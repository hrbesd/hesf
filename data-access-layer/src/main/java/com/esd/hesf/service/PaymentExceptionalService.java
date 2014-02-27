package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.PaymentExceptional;

/**
 * 特殊缴款方式类别 service 接口
 * 
 * @author Administrator
 * 
 */
public interface PaymentExceptionalService extends BaseService<PaymentExceptional> {

	/**
	 * 获得所有 特殊缴款方式类别 方法
	 * 
	 * @return
	 */
	public List<PaymentExceptional> getAll();
}
