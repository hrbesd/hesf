package com.esd.hesf.dao;

/**
 * universal interface
 * 
 * @author Administrator
 * 
 */
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

	/**
	 * insert : 直接将整个对象插入到数据库, 没有对属性的非空判断
	 * 
	 * @param t
	 * @return
	 */
	int insert(T t);

	/**
	 * insertSelective ：有对对象的属性进行非空判断处理, 即只有非空字段才能插入
	 * 
	 * @param t
	 * @return
	 */
	int insertSelective(T t);

	/**
	 * delete : 按主键删除一条数据
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);
	
	/**
	 * delete : 按主键删除一条数据
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * update : 根据id, 更新一条数据
	 * 
	 * @param t
	 * @return
	 */
	int updateByPrimaryKey(T t);

	/**
	 * retrieveByPrimaryKey : 根据id, 查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	T retrieveByPrimaryKey(int id);

	/**
	 * retrieveByPage : 分页查询
	 * 
	 * @param map
	 *            map中放入对象的类名首字母小写, 起始索引, 返回量: 例：查询公司类(Company)的10条数据, 参数应为:
	 *            map.put("company",company); map.put("start",0);
	 *            map.put("size",10);
	 * @return
	 */
	List<T> retrieveByPage(Map<String, Object> map);

	/**
	 * retrieveCount--provided to the paging query uses, using the same params
	 * 查询数据总条数, 提供给分页查询用, 参数和分页查询一样
	 * 
	 * @param map
	 * @return
	 */
	int retrieveCount(Map<String, Object> map);
}
