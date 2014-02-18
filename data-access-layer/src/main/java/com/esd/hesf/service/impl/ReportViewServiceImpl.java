package com.esd.hesf.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.hesf.dao.AuditCompanyViewDao;
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.viewmodels.AuditTempModel;
import com.esd.hesf.viewmodels.ReportViewModel;

@Service
public class ReportViewServiceImpl implements ReportViewService {

	@Autowired
	private AuditCompanyViewDao acvDao;
	
	@Autowired
	private PaymentDao pDao;

	@Override
	public List<ReportViewModel> getByCompanyType(String year) {
		System.out.println("year : " + year);
		// ①从审核-公司 是视图表中查出数据列表
		List<ReportViewModel> list = acvDao.retrieveReportByCompanyType(year);
		// ②根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("year", year);
			map.put("companyType", rvm.getCompanyType());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			//查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

	@Override
	public List<ReportViewModel> getByArea(String year) {
		System.out.println("year : " + year);
		// ①从审核-公司 是视图表中查出数据列表
		List<ReportViewModel> list = acvDao.retrieveReportByArea(year);
		// ②根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("year", year);
			map.put("areaCode", rvm.getAreaCode());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			//查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

	@Override
	public List<ReportViewModel> getByCompanyEconomyType(String year) {
		System.out.println("year : " + year);
		// ①从审核-公司 是视图表中查出数据列表
		List<ReportViewModel> list = acvDao.retrieveReportByCompanyEconomyType(year);
		// ②根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("year", year);
			map.put("companyEconomyType", rvm.getCompanyEconomyType());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			//查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

}
