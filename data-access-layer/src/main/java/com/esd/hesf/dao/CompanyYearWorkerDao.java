package com.esd.hesf.dao;

import com.esd.hesf.model.CompanyYearWorker;

/**
 * 公司--残疾职工 关系表 dao借口
 * 
 * @author Administrator
 * 
 */
public interface CompanyYearWorkerDao extends BaseDao<CompanyYearWorker> {

	/**
	 * 根据自身属性删除数据
	 * 
	 * @param cyw
	 * @return
	 */
	int deleteBySelfObject(CompanyYearWorker cyw);

	// /**
	// * 更改员工所在公司
	// * @param cys
	// * @return
	// */
	// int updateCompanyByWorkerAndYear(CompanyYearWorker cyw);
}