package com.esd.hesf.dao;

import com.esd.hesf.model.LegalHoliday;

/**
 * 法定假日 dao接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-10
 */
public interface LegalHolidayDao extends BaseDao<LegalHoliday> {

	/**
	 * 根据id得到一个法定假日对象
	 * @param id
	 * @return LegalHoliday
	 */
	public LegalHoliday retrieveByPrimaryKey(String id);
	
	/**
	 * 根据legaldate得到一个法定假日对象
	 * @param legaldate
	 * @return LegalHoliday
	 */
	public LegalHoliday retrieveByLegalDate(String legaldate);
	
}