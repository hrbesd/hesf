package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.WorkerCalculator;

/**
 * 残疾人计算类 dao接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerCalculatorDao {

	/**
	 * 查询残疾人设定比例不为1的参数组
	 * 
	 * @return
	 */
	List<WorkerCalculator> retrieveSpecialSetting(String year);

	/**
	 * 查询 指定公司, 指定年度, 指定残疾等级和类型的残疾人数
	 * 
	 * @param map	
	 * map中放入: year-年份, companyId--公司id, workerHandicapType--残疾类型, workerHandicapLevel--残疾等级
	 * @return
	 */
	Integer retrieveSpecialCount(Map<String, Object> map);
	
	/**
	 * 查询 指定公司, 指定残疾等级和类型的残疾人数--从员工缓存表中
	 * 
	 * @param map	
	 * map中放入: companyId--公司id, workerHandicapType--残疾类型, workerHandicapLevel--残疾等级
	 * @return
	 */
	Integer retrieveSpecialCountFromWorkerTemp(Map<String, Object> map);
	
	
	
}