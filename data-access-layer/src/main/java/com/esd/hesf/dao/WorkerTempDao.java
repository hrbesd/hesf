package com.esd.hesf.dao;

import java.util.HashMap;
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
	List<WorkerTemp> retrieveByCheck(Map<String, Object> map);

	/**
	 * 根据是否通过校验来查询总条数,如果参数为空则查询所有
	 * 
	 * @param map中放入workerTemp对象
	 * @return
	 */
	Integer retrieveCountByCheck(Map<String, Object> map);

	/**
	 * 根据userId删除数据
	 * @param userId
	 * @return
	 */
	int deleteByUserId(Integer userId);
	
	/**
	 * 根据companyId删除数据
	 * @param companyId
	 * @return
	 */
	int deleteByCompanyId(Integer companyId);
	
	/**
	 * 根据身份证号删除残疾职工数据
	 * @param WorkerIdCard
	 * @return
	 */
	int deleteByWorkerIdCard(String workerIdCard);
	
	/**
	 * 根据身份证号 查询是否已经有这个残疾职工了
	 * @param WorkerIdCard
	 * @return
	 */
	WorkerTemp retrieveByWorkerIdCard(String WorkerIdCard);
	
	/**
	 * 根据员工id, 获得他的图片
	 * @param id
	 * @return
	 */
	HashMap retrievePic(Integer id);
	
	/**
	 * 根据身份证号来查询 是否已经存在此身份证号
	 * @param workerIdCard
	 * @return 返回存在的条数
	 */
	Integer retrieveCountByworkerIdCard(String workerIdCard);
	
	/**
	 * 根据bean中任意属性条件查询, 但必须放入start--起始索引, size--返回量
	 * @param map
	 * @return
	 */
	List<WorkerTemp> retrieveByMultiConditions(Map<String,Object> map);
	
	/**
	 * 根据bean中任意属性条件查询, 但必须放入start--起始索引, size--返回量
	 * @param map
	 * @return
	 */
	int retrieveByMultiConditionsCount(Map<String,Object> map);
}