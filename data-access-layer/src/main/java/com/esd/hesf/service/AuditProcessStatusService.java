package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.AuditProcessStatus;

/**
 * 审核进程状态 service接口
 * 
 * @author Administrator
 * 
 */
public interface AuditProcessStatusService extends
		BaseService<AuditProcessStatus> {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	List<AuditProcessStatus> getAll();
}
