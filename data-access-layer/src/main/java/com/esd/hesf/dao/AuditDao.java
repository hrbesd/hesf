package com.esd.hesf.dao;

import java.util.Map;

import com.esd.hesf.model.Audit;

/**
 * 审核表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface AuditDao extends BaseDao<Audit> {

	/**
	 * 复制去年的审核数据
	 * 
	 * @param currentYear
	 * @param lastYear
	 * @return
	 */
	int insertLastYearData(Map<String, String> map);
}