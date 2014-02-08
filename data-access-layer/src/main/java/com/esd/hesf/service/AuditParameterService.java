package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.WorkerCalculator;

/**
 * 年审参数 service接口
 * 
 * @author Administrator
 * 
 */
public interface AuditParameterService extends BaseService<AuditParameter> {

	/**
	 * 获得所有数据--测试方法
	 * 
	 * @return
	 */
	public List<AuditParameter> getAll();

	/**
	 * 根据年份获得该年的年审参数
	 * 
	 * @param year
	 * @return
	 */
	public AuditParameter getByYear(String year);

	/**
	 * 查询残疾人设定比例不为 1 的参数组
	 * 
	 * @return
	 */
	List<WorkerCalculator> getSpecialSetting();

	/**
	 * 查询 指定公司, 指定年度, 指定残疾类型和等级的残疾人数
	 * 
	 * @param map
	 * @return
	 */
	int getSpecialCount(String companyCode, String year, int workerHandicapType, int workerHandicapLevel);
}
