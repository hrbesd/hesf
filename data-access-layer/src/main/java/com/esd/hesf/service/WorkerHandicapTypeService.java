package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.WorkerHandicapType;

/**
 * 残疾类别 service接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerHandicapTypeService extends BaseService<WorkerHandicapType> {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	List<WorkerHandicapType> getAll();
}
