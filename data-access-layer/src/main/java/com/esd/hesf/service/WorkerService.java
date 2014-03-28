package com.esd.hesf.service;

import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.viewmodels.WorkerViewModel;

/**
 * 残疾职工 service接口
 * 
 * @author Administrator
 * 
 */
public interface WorkerService extends BaseService<Worker> {

	/**
	 * 向一个企业中添加职工
	 * 
	 * @param worker
	 *            职工对象
	 * @param companyId
	 *            企业id
	 * @param year
	 *            职工和公司建立关系 年份
	 * @return
	 */
	boolean save(Worker worker, Integer companyId, String year);

	/**
	 * 根据年度, 公司id, 和员工的相关信息 得到员工列表
	 * 
	 * @param map
	 *            参数map 详见 com.esd.hesf.ExampleMap类中的 example_worker() 里面的描述
	 *            测试样例见 com.esd.cs.TestController 的测试方法13
	 * @return
	 */
	PaginationRecordsAndNumber<Worker, Number> getPaginationRecords(
			Map<String, Object> map);

	/**
	 * 多条件查询 员工 数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的样例 测试样例见
	 *            com.esd.cs.TestController 的测试方法 27
	 * @return
	 */
	PaginationRecordsAndNumber<WorkerViewModel, Number> getByMultiCondition(
			Map<String, Object> map);

	/**
	 * 根据年份和残疾证号, 判断一个员工是在在一家公司里
	 * 
	 * @param year
	 * @param workerHandicapCode
	 * @return
	 */
	Company retrieveCompanyByWorker(String year, String workerHandicapCode);

	/**
	 * 根据 身份证号 判断该残疾人在数据库中是否存在
	 * 
	 * @param workerHandicapCode
	 * @return
	 */
	Worker getByWorkerIdCard(String workerIdCard);

	/**
	 * 根据 残疾证号 判断该残疾人在数据库中是否存在
	 * 
	 * @param workerHandicapCode
	 * @return
	 */
	Worker getByWorkerHandicapCode(String workerHandicapCode);

	/**
	 * 更改残疾员工 所在公司
	 * 
	 * @param workerId
	 *            残疾员工id--非空
	 * @param targetCompanyCode
	 *            跳转目标公司id--非空
	 * @param currentYear
	 *            当前年份--非空
	 * @param currentJob
	 *            新职位名称, 可为空
	 * @return
	 */
	boolean changeCompany(Integer workerId, Integer targetCompanyId,
			String currentYear, String currentJob);

	/**
	 * 删除所有员工数据, 包括关联的公司. 慎用！慎用！慎用！慎用！慎用！
	 * 
	 * @return
	 */
	void deleteAllData();

	/**
	 * 测试数据用--暂时勿删
	 * 
	 * @param size
	 * @return
	 */
	List<Worker> getAll(int size);

}
