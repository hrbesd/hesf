package com.esd.hesf.service;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
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
	 * 根据插入数据的用户id, 删除数据
	 * 
	 * @return
	 */
	Boolean deleteByUserId(Integer userId);
	
	/**
	 * 根据数据所属的公司, 删除数据
	 * 
	 * @return
	 */
	Boolean deleteByCompanyId(Integer companyId);
	
	/**
	 * 根据身份证号, 删除数据
	 * 
	 * @return
	 */
	Boolean deleteByWorkerIdCard(String workerIdCard);
	
	/**
	 * 根据身份证号 查询是否已经有这个残疾职工了
	 * @param WorkerIdCard
	 * @return
	 */
	WorkerTemp getByWorkerIdCard(String WorkerIdCard);
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
	
	/**
	 * 根据bean中任意属性条件查询, 但必须放入page--起始页, pageSize--返回量
	 * @param map
	 * @return
	 */
	PaginationRecordsAndNumber<WorkerTemp,Number> getByMultiConditions(Map<String,Object> map);
	
	/**
	 * 根据员工id, 得到其对应的图片
	 * @param id
	 * @return
	 */
	byte[] getPicByPrimaryKey(Integer id);
	
	/**
	 * 根据bean中任意属性条件查询数据条数
	 * @param map
	 * @return
	 */
	int getCountByCompanyId(Integer companyId);
}
