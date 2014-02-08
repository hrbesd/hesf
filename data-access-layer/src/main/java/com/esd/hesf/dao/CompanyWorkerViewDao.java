package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.viewmodels.WorkerViewModel;

/**
 * 公司--员工视图表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyWorkerViewDao {

	/**
	 * 根据公司查询 他所拥有的员工
	 * 
	 * @param map
	 *            map中放入: year--年份, companyid--公司id, start--其实索引, size--返回量
	 * @return
	 */
	List<Worker> retrieveWorkerByCompany(Map<String, Object> map);

	/**
	 * 根据其他查询方法一样的条件查询数据总条数
	 * 
	 * @param map
	 * @return
	 */
	int retrieveCount(Map<String, Object> map);

	/**
	 * 根据员工相关信息查询带公所属公司信息的员工信息列表
	 * 
	 * @param map
	 * @return
	 */
	List<WorkerViewModel> retrieveWorkerByWorker(Map<String, Object> map);

	/**
	 * 根据员工相关信息查询所属公司信息的员工信息 总条数
	 * 
	 * @param map
	 * @return
	 */
	int retrieveWorkerByWorkerCount(Map<String, Object> map);
	
	/**
	 * 根据年份和残疾证号, 判断一个员工是在在一家公司里
	 * @param map中 放入 year : 年份 , workerHandicapCode : 残疾证号
	 * @return
	 */
	Company retrieveCompanyByWorker(Map<String,String> map);
}