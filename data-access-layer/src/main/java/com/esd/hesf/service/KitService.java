package com.esd.hesf.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyLog;
import com.esd.hesf.model.User;
import com.esd.hesf.model.UserLog;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerLog;

/**
 * 工具类--提供常用的方法
 * 
 * @author Administrator
 * 
 */
public class KitService {

	/**
	 * 获得一个UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		char[] ch = uuid.toString().toCharArray();
		String s = "";
		for (char c : ch) {
			if (c == '-') {
				continue;
			}
			s += c;
		}
		return s;
	}

	/**
	 * 处理传进来的地区code, 变成适用于sql语句使用的格式
	 * 
	 * @param code
	 * @return
	 */
	public static String areaCodeForSql(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "20" + mid + "____";
		}
		return code;
	}

	/**
	 * 将年龄转化为出生年字符串
	 * 
	 * @param age
	 * @return
	 */
	public static String getBirthFromAge(String age) {
		// 非空判断
		if (age == null || "".equals(age)) {
			return null;
		}
		int iAge = Integer.parseInt(age);
		// 获得今天日期
		Date today = new Date();
		// 格式或为标准格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = sdf.format(today);
		// 获得出生年 = 去年 - 年龄
		String birthYear = String.valueOf((Integer.parseInt(todayStr.substring(
				0, 4)) - 1 - iAge));
		return birthYear;
	}

	/**
	 * 将日期Date转化为yyyy-MM-dd格式的字符串
	 * @param date
	 * @return
	 */
	public static String getStringDate(Date date) {
		if (date == null) {
			return null;
		}
		// 格式或为标准格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		return str;
	}
	
	/**
	 * 将Company转化为CompanyLog
	 * @param t
	 * @return
	 */
	public static CompanyLog getLogObjectFromEntity(Company t){
		CompanyLog log = new CompanyLog();
		log.setArea(t.getArea());
		log.setCompanyAddress(t.getCompanyAddress());
		log.setCompanyBank(t.getCompanyBank());
		log.setCompanyBankAccount(t.getCompanyBankAccount());
		log.setCompanyCode(t.getCompanyCode());
		log.setCompanyContactPerson(t.getCompanyContactPerson());
		log.setCompanyEconomyType(t.getCompanyEconomyType());
		log.setCompanyFax(t.getCompanyFax());
		log.setCompanyLegal(t.getCompanyLegal());
		log.setCompanyManagement(t.getCompanyManagement());
		log.setCompanyMobile(t.getCompanyMobile());
		log.setCompanyName(t.getCompanyName());
		log.setCompanyOrganizationCode(t.getCompanyOrganizationCode());
		log.setCompanyPhone(t.getCompanyPhone());
		log.setCompanyProperty(t.getCompanyProperty());
		log.setCompanyRemark(t.getCompanyRemark());
		log.setCompanyTaxCode(t.getCompanyTaxCode());
		log.setCompanyType(t.getCompanyType());
		log.setCompanyZipCode(t.getCompanyZipCode());
		log.setId(t.getId());
		log.setUserId(t.getUserId());
		log.setIsActive(t.getIsActive());
		log.setVersion(t.getVersion());
		return log;
	}

	/**
	 * 将User转化为UserLog
	 * @param t
	 * @return
	 */
	public static UserLog getLogObjectFromEntity(User t){
		UserLog log = new UserLog();
		log.setUserEmail(t.getUserEmail());
		log.setUserGroup(t.getUserGroup());
		log.setUserMobile(t.getUserMobile());
		log.setUserName(t.getUserName());
		log.setUserPassword(t.getUserPassword());
		log.setUserPhone(t.getUserPhone());
		log.setUserRealName(t.getUserRealName());
		log.setUserRemark(t.getUserRemark());
		log.setUserStatus(t.getUserStatus());
		log.setId(t.getId());
		log.setUserId(t.getUserId());
		log.setIsActive(t.getIsActive());
		log.setVersion(t.getVersion());
		return log;
	}

	/**
	 * 将Worker转化为WorkerLog
	 * @param t
	 * @return
	 */
	public static WorkerLog getLogObjectFromEntity(Worker t){
		WorkerLog log = new WorkerLog();
		log.setArea(t.getArea());
		log.setCareerCard(t.getCareerCard());
		log.setCurrentJob(t.getCurrentJob());
		log.setIsCadre(t.getIsCadre());
		log.setIsCollege(t.getIsCollege());
		log.setIsProfessor(t.getIsProfessor());
		log.setIsRetired(t.getIsRetired());
		log.setPensionInsurance(t.getPensionInsurance());
		log.setPhone(t.getPhone());
		log.setPic(t.getPic());
		log.setPicTitle(t.getPicTitle());
		log.setRemark(t.getRemark());
		log.setSalary(t.getSalary());
		log.setWorkerAddress(t.getWorkerAddress());
		log.setWorkerBirth(t.getWorkerBirth());
		log.setWorkerBirthYear(t.getWorkerBirthYear());
		log.setWorkerGender(t.getWorkerGender());
		log.setWorkerHandicapCode(t.getWorkerHandicapCode());
		log.setWorkerHandicapLevel(t.getWorkerHandicapLevel());
		log.setWorkerHandicapType(t.getWorkerHandicapType());
		log.setWorkerIdCard(t.getWorkerIdCard());
		log.setWorkerName(t.getWorkerName());
		log.setId(t.getId());
		log.setCreateTime(t.getCreateTime());
		log.setUpdateTime(t.getUpdateTime());
		log.setUserId(t.getUserId());
		log.setIsActive(t.getIsActive());
		log.setVersion(t.getVersion());
		return log;
	}




}
