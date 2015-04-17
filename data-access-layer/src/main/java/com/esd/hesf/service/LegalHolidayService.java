package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.LegalHoliday;
import com.esd.hesf.model.User;

/**
 * 法定假日 service接口
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-10
 */
public interface LegalHolidayService extends BaseService<LegalHoliday> {

	/**
	 * 根据id得到一个对象
	 * @param id
	 * @return
	 */
	public LegalHoliday getByPrimaryKey(String id);
	
	/**
	 * 根据id删除一个对象
	 * @param id
	 * @return
	 */
	public Boolean delete(String id);
	
	/**
	 * 获取所有对象
	 * @return
	 */
	public List<LegalHoliday> getAll();
	
	/**
	 * 检查是否是休息日, 或者不在可操作时间
	 * @param user
	 * @return false: 表示为休息日等不可操作时间, true为允许操作时间.
	 */
	public Boolean checkIsRestDay(User user);
	
	/**
	 * 根据legaldate得到一个对象
	 * @param legaldate
	 * @return
	 */
	public LegalHoliday getByLegaldate(String legaldate);
	
	
}
