package com.esd.hesf;

import java.util.HashMap;
import java.util.Map;

import com.esd.hesf.model.Payment;

public class ExampleMap {

	/**
	 * 企业 查询参数map 样例 ******为非空项
	 */
	public void example_company() {
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("year", "2001"); // 年度 已被删除
		map.put("companyCode", "601001"); // 公司档案号
		map.put("companyName", "广寒攻丝"); // 公司名称
		map.put("companyProperty", 1); // 公司性质 对应的id
		map.put("companyEconomyType", 1); // 公司经济类型 对应的id
		map.put("companyType", 1); // 公司类型 新添加参数
		map.put("areaCode", "20230000"); // 地区 对应地区 code
		map.put("companyTaxCode", "878923749238293874"); // 公司税务编码
		map.put("companyLegal", "法人1号"); // 公司法人代表
		map.put("companyOrganizationCode", "878923749238293874"); // 组织机构代码证
		map.put("companyAddress", "宏碁大姐1008号"); // 公司地址
		map.put("page", 2); // 分页--起始页 ******************************
		map.put("pageSize", 50);// 分页--返回量 ******************************
		map.put("isActive", false); // 是否已经被逻辑删除了, 默认值为false,即只查询没有被逻辑删除的

		// 以下4个字段已被挪到审核查询参数中
		// map.put("minTotal", 5); // 查询范围中 公司最少人数 已被删除
		// map.put("maxTotal", 250); // 查询范围中 公司最多人数 已被删除
		// map.put("minHandicapTotal", 5); // 查询范围中 残疾职工最少人数 已被删除
		// map.put("maxHandicapTotal", 250); // 查询范围中 残疾职工最多人数 已被删除
	}

	/**
	 * 残疾职工 查询参数map 样例 ******为非空项
	 */
	public void example_worker() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 如果查询一个公司里的残疾员工的话, 要么填下面两个属性, 要么填公司id companyId!
		map.put("year", "2001"); // 年度
		map.put("companyId", 1366);
		map.put("getOverproof", true);	//是否获得全部达到退休年龄的员工
		// map.put("companyCode", "1390293306601");// 公司档案code //已被删除

		/**********************************************************/
		/************ 以上字段为查询公司里的残疾人用 ******************/

		map.put("workerHandicapCode", "34234234234234"); // 残疾证号
		map.put("careerCard", "34asasd123"); // 就业证号
		map.put("workerName", "2001"); // 姓名
		map.put("workerGender", "34234234234234"); // 性别
		map.put("currentJob", "34asasd123"); // 当前岗位
		map.put("areaCode", "20230000"); // 地区 对应地区 code
		map.put("minAge", 15); // 最小年龄
		map.put("maxAge", 50); // 最大年龄
		map.put("phone", "13812345678");
		map.put("workerHandicapType", "34234234234234"); // 残疾类别 对应的id
		map.put("workerHandicapLevel", "34asasd123"); // 残疾等级 对应的id
		map.put("isRetired", true);	//是否是内退的
		map.put("page", 2); // 分页--起始页 ******************************
		map.put("pageSize", 50);// 分页--返回量 ******************************
		map.put("isActive", false); // 是否已经被逻辑删除了, 默认值为false,即只查询没有被逻辑删除的
	}

	/**
	 * 单位年审信息 查询参数map 样例 ******为非空项
	 */
	public void example_audit() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", "2001"); // 年度
		map.put("companyCode", "601001"); // 公司档案号
		map.put("companyTaxCode", "878923749238293874"); // 公司税务编码
		map.put("companyOrganizationCode", "878923749238293874"); // 组织机构代码证
		map.put("companyProperty", 1); // 公司性质 对应的id
		map.put("companyEconomyType", 1); // 公司经济类型 对应的id
		map.put("companyType", 1); // 公司类型 对应的id 新添加
		map.put("areaCode", "20230000"); // 地区 对应地区 code
		map.put("minTotal", 5); // 查询范围中 公司最少人数
		map.put("maxTotal", 250); // 查询范围中 公司最多人数
		// 从公司参数中挪来的2个参数
		map.put("minHandicapTotal", 5); // 查询范围中 残疾职工最少人数 新添加
		map.put("maxHandicapTotal", 250); // 查询范围中 残疾职工最多人数 新添加

		map.put("companyName", "广寒攻丝"); // 公司名称
		map.put("companyAddress", "宏碁大姐1008号"); // 公司地址
		map.put("companyLegal", "法人1号"); // 公司法人代表
		map.put("auditProcessStatus", 5); // 流程状态 --1初审，2复审 类似这样的字段
		map.put("paymentPerson", 3); // 缴款人 id
		map.put("overYear", 3); // 超过几年未初审的公司
		map.put("isExempt", false); // 是否免缴 true免缴, false不免缴
		map.put("actualAmount", 20000.0); // 实缴金额
		map.put("page", 2); // 分页--起始页 ******************************
		map.put("pageSize", 50);// 分页--返回量 ******************************
		map.put("isActive", false); // 是否已经被逻辑删除了, 默认值为false,即只查询没有被逻辑删除的
	}
	
	/**
	 * 缴款信息 查询参数map 样例 ******为非空项
	 */
	public void example_payment() {
		Map<String, Object> map = new HashMap<String, Object>();
		Payment payment = new Payment();	//payment对象, 包含其中的各种属性值
		map.put("payment", payment);
		map.put("startDate", "2001-12-01");	//缴款开始日期
		map.put("endDate", "2011-02-05");	//缴款结束日期
		map.put("page", 2); // 分页--起始页 ******************************
		map.put("pageSize", 50);// 分页--返回量 ******************************
	}
}
