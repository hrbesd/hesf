package com.esd.hesf.dao;

import java.util.List;

import com.esd.hesf.model.Worker;

/**
 * 残疾员工 dao接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerDao extends BaseDao<Worker> {

	/**
	 * 根据多个id, 得到对应的员工列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<Worker> retrieveByMultiIds(int[] ids);
	
	/**
	 * 根据 身份证号 判断该残疾人在数据库中是否存在
	 * @param workerHandicapCode
	 * @return
	 */
	public Worker retrieveByWorkerIdCard(String workerIdCard);
	
	/**
	 * 根据 残疾证号 判断该残疾人在数据库中是否存在
	 * @param workerHandicapCode
	 * @return
	 */
	public Worker retrieveByWorkerHandicapCode(String workerHandicapCode);
	
	/**
	 * 删除所有数据, 慎用！慎用！慎用！
	 * @return
	 */
	int deleteAllData();
}