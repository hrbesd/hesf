package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.WorkerHandicapLevel;

/**
 * 残疾等级 service实现类
 * 
 * @author Administrator
 * 
 */
public interface WorkerHandicapLevelService extends BaseService<WorkerHandicapLevel> {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	public List<WorkerHandicapLevel> getAll();
}
