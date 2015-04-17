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
	List<AuditParameter> getAll();

	/**
	 * 根据年份获得该年的年审参数
	 * 
	 * @param year
	 * @return
	 */
	AuditParameter getByYear(String year);

	/**
	 * 查询残疾人设定比例不为 1 的参数组
	 * 
	 * @param year
	 * @return
	 */
	List<WorkerCalculator> getSpecialSetting(String year);

	/**
	 * 查询 指定公司, 指定年度, 指定残疾类型和等级的残疾人数
	 * 
	 * year-年份, companyCode--公司code, workerHandicapType--残疾类型,
	 * workerHandicapLevel--残疾等级
	 * 
	 * @return
	 */
	int getSpecialCount(String companyCode, String year, int workerHandicapType,
			int workerHandicapLevel);

	/**
	 * 查询 指定公司, 指定残疾类型和等级的残疾人数--从员工缓存表中
	 * 
	 * year-年份, companyId--公司id, workerHandicapType--残疾类型,
	 * workerHandicapLevel--残疾等级
	 * 
	 * @return
	 */
	int getSpecialCountFromWorkerTemp(Integer companyId, String year, int workerHandicapType,
			int workerHandicapLevel);
	
	/**
	 * 得到最近一个审核参数的年份
	 * 
	 * @return
	 */
	String getLastestYear();

	/**
	 * 
	 * 得到所有审核年度列表
	 * 
	 * @return
	 */
	String[] getYears();

}
