package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.Audit;

/**
 * 审核表视图 查询dao接口
 * 
 * @author Administrator
 * 
 */
public interface AuditCompanyViewDao {

	/**
	 * 根据传进来的公司等信息查询审核信息
	 * 
	 * @param map
	 * @return
	 */
	List<Audit> retrieveByCompany(Map<String, Object> map);

	/**
	 * 查询条数, 分页使用, 使用同样的参数
	 * 
	 * @param map
	 * @return
	 */
	int retrieveCount(Map<String, Object> map);
}
