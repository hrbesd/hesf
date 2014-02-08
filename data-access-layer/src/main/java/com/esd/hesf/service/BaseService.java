package com.esd.hesf.service;

import com.esd.common.util.PaginationRecordsAndNumber;

/**
 * 通用service接口
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public interface BaseService<T> {
	/**
	 * save : 保存
	 * 
	 * @param t
	 * @return
	 */
	boolean save(T t);

	/**
	 * delete : 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(int id);

	/**
	 * update ：根据主键更新
	 * 
	 * @param t
	 * @return
	 */
	boolean update(T t);

	/**
	 * getByPrimaryKey : 根据主键id查询一条数据,其中的数据已经处理成可供前台显示的
	 * 
	 * @param id
	 * @return
	 */
	T getByPrimaryKey(int id);

	/**
	 * getPaginationRecords: 分页查询
	 * 
	 * @param t
	 *            : 类型对象, 其中的属性可以做为相应的查询参数
	 * @param page
	 *            : 起始页数
	 * @param pageSize
	 *            :返回条数
	 * @return
	 */
	PaginationRecordsAndNumber<T, Number> getPaginationRecords(T t, Integer page, Integer pageSize);

}
