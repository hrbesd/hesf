package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.WorkerTemp;

/**
 * 残疾员工缓存表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerTempDao extends BaseDao<WorkerTemp> {

	/**
	 * 删除所有数据, 慎用！慎用！慎用！
	 * 
	 * @return
	 */
	int deleteAllData();

	/**
	 * 根据是否通过校验来查询,如果参数为空则查询所有
	 * 
	 * @param map中放入workerTemp对象
	 * @return
	 */
	public List<WorkerTemp> retrieveByCheck(Map<String, Object> map);

	/**
	 * 根据是否通过校验来查询总条数,如果参数为空则查询所有
	 * 
	 * @param map中放入workerTemp对象
	 * @return
	 */
	public Integer retrieveCountByCheck(Map<String, Object> map);

	/**
	 * 根据userId删除数据
	 * @param userId
	 * @return
	 */
	int deleteByUserId(Integer userId);
	
	/**
	 * 根据身份证号来查询 是否已经存在此身份证号
	 * @param workerIdCard
	 * @return 返回存在的条数
	 */
	Integer retrieveCountByworkerIdCard(String workerIdCard);
}