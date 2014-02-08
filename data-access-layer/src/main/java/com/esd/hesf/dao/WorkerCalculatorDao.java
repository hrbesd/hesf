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
	List<WorkerCalculator> retrieveSpecialSetting();

	/**
	 * 查询 指定公司, 指定年度, 指定残疾等级和类型的残疾人数
	 * 
	 * @param map
	 * @return
	 */
	Integer retrieveSpecialCount(Map<String, Object> map);
}