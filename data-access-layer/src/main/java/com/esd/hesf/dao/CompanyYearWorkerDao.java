package com.esd.hesf.dao;

import java.util.Map;

import com.esd.hesf.model.CompanyYearWorker;

/**
 * 公司--残疾职工 关系表 dao借口
 * 
 * @author Administrator
 * 
 */
public interface CompanyYearWorkerDao extends BaseDao<CompanyYearWorker> {

	/**
	 * 复制上一年的公司-员工对应信息
	 * 
	 * @param map
	 * @return
	 */
	int insertLastYearData(Map<String, Object> map);

	/**
	 * 根据自身属性删除数据
	 * 
	 * @param cyw
	 * @return
	 */
	int deleteBySelfObject(CompanyYearWorker cyw);

	/**
	 * 删除所有数据, 慎用！慎用！慎用！
	 * 
	 * @return
	 */
	int deleteAllData();

	// /**
	// * 更改员工所在公司
	// * @param cys
	// * @return
	// */
	// int updateCompanyByWorkerAndYear(CompanyYearWorker cyw);

}