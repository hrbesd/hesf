package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.WorkerTemp;

/**
 * 员工缓存表 service接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerTempService extends BaseService<WorkerTemp> {

	/**
	 * 删除所有数据, 慎用！慎用！慎用！
	 * 
	 * @return
	 */
	Boolean deleteAllData();
	
	/**
	 * 根据插入数据的用户id, 输出数据
	 * 
	 * @return
	 */
	Boolean deleteByUserId(Integer userId);

	/**
	 * 根据是否通过校验来查询,如果参数为空则查询所有
	 * 
	 * @param isOk
	 * @param userId
	 *            操作用户
	 * @return
	 */
	public List<WorkerTemp> getByCheck(Boolean isOk, Integer userId);

	/**
	 * 根据是否通过校验来查询总条数,如果参数为空则查询所有
	 * 
	 * @param isOk
	 * @param userId
	 *            操作用户
	 * @return
	 */
	public Integer getCountByCheck(Boolean isOk, Integer userId);
	
	/**
	 * 根据身份证号来查询 是否已经存在此身份证号,
	 * @param workerIdCard
	 * @return 返回存在的总条数
	 */
	public Integer getCountByworkerIdCard(String workerIdCard);
	
}
