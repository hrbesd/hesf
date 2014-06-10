package com.esd.hesf.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esd.hesf.model.Worker;

/**
 * 残疾员工 dao接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerDao extends BaseDao<Worker> {

	/**
	 * 根据员工id, 获得他的图片
	 * @param id
	 * @return
	 */
	HashMap retrievePic(Integer id);
	
	/**
	 *  根据多个id查询残疾人列表, map中放入array数组, 或者list集合等
	 *  key值必须为: array 
	 * @param map
	 * @return
	 */
	List<Worker> retrieveByPrimaryKeys(Map<String, Object> map);
	
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