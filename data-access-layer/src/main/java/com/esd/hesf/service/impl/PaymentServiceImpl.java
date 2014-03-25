package com.esd.hesf.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;
import com.esd.hesf.service.PaymentService;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDao dao;

	@Override
	public boolean save(Payment t) {
		if (t == null) {
			new HesfException("payment对象", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		// 验证缴款人
		if (t.getPaymentPerson() == null) {
			new HesfException("t.paymentPerson", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getPaymentPerson().getId() == null
				|| t.getPaymentPerson().getId() <= 0) {
			new HesfException("t.paymentPerson.id",
					HesfException.type_number_negative).printStackTrace();
			return false;
		}
		// 验证缴款公司
		if (t.getPaymentCompany() == null) {
			new HesfException("t.paymentCompany", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getPaymentCompany().getId() == null
				|| t.getPaymentCompany().getId() <= 0) {
			new HesfException("t.paymentCompany.id",
					HesfException.type_number_negative).printStackTrace();
			return false;
		}
		// 验证缴款金额

		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Payment t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Payment getByPrimaryKey(Integer id) {
		Payment t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaginationRecords(
			Payment t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public BigDecimal getAlreadyPayByAudit(Integer auditId) {
		if (auditId == null || auditId <= 0) {
			new HesfException("auditId", HesfException.type_number_negative)
					.printStackTrace();
			return null;
		}
		Payment payment = new Payment();
		payment.setAudit(new Audit(auditId));
		return dao.retrieveAlreadyPay(payment);
	}

	@Override
	public BigDecimal getAlreadyPayByCompany(Integer companyId) {
		if (companyId == null || "".equals(companyId)) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentCompany(new Company(companyId));
		return dao.retrieveAlreadyPay(payment);
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecordByAudit(
			Integer auditId, Integer page, Integer pageSize) {
		if (auditId == null || auditId <= 0) {
			new HesfException("auditId", HesfException.type_number_negative)
					.printStackTrace();
			return null;
		}
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setAudit(new Audit(auditId));
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecordByCompany(
			Integer companyId, Integer page, Integer pageSize) {
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setPaymentCompany(new Company(companyId));
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Payment> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getByMultiCondition(
			Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		// 初始为0
		int size = 0;
		// 返回量
		if (map.get("pageSize") == null) {
			map.put("size", Constants.SIZE);
		} else {
			size = Integer.parseInt(map.get("pageSize").toString());
			map.put("size", size);
		}
		// 起始索引值
		if (map.get("page") == null) {
			map.put("start", Constants.START);
		} else {
			int page = Integer.parseInt(map.get("page").toString());
			map.put("start", page <= 1 ? Constants.START : (page - 1) * size);
		}
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	public static void main(String[] args) {
		PaymentServiceImpl im = new PaymentServiceImpl();
		// im.createPaymentExcel(filePath, paymentList);
		System.out.println(new File("1.xls"));
	}

	@Override
	public boolean createPaymentExcel(String FilePath,
			List<Payment> paymentList, Integer page, Integer pageSize) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.getSheet("sheet1");
		if (sheet == null) {
			sheet = wb.createSheet("sheet1");
			// 创建一个头部Excel的单元格
			HSSFRow titleRow = sheet.createRow(0);
			// 设置单元格的样式格式
			// 档案编码
			titleRow.createCell(0).setCellValue("档案编码");
			// 主管部门
			titleRow.createCell(1).setCellValue("主管部门");
			// 企业名称
			titleRow.createCell(2).setCellValue("企业名称");
			// 企业法人
			titleRow.createCell(3).setCellValue("企业法人");
			// 企业联系人
			titleRow.createCell(4).setCellValue("企业联系人");
			// 组织机构代码证号
			titleRow.createCell(5).setCellValue("组织机构代码证号");
			// 税务编码
			titleRow.createCell(6).setCellValue("税务编码");
			// 企业地址
			titleRow.createCell(7).setCellValue("企业地址");
			// 企业邮政编码
			titleRow.createCell(8).setCellValue("企业邮政编码");
			// 传真
			titleRow.createCell(9).setCellValue("传真");
			// 联系电话
			titleRow.createCell(10).setCellValue("联系电话");
			// 企业联系人手机
			titleRow.createCell(11).setCellValue("企业联系人手机");
			// 开户银行
			titleRow.createCell(12).setCellValue("开户银行");
			// 银行账户
			titleRow.createCell(13).setCellValue("银行账户");
			// 备注
			titleRow.createCell(14).setCellValue("备注");
			// 审核年度
			titleRow.createCell(15).setCellValue("审核年度");
			// 员工总数
			titleRow.createCell(16).setCellValue("员工总数");
			// 残疾员工总数/已录入数
			titleRow.createCell(17).setCellValue("残疾员工总数/已录入数");
			// 预计残疾人数
			titleRow.createCell(18).setCellValue("预计残疾人数");
			// 应安排残疾人数
			titleRow.createCell(19).setCellValue("应安排残疾人数");
			// 已安排残疾人数
			titleRow.createCell(20).setCellValue("已安排残疾人数");
			// 应缴金额
			titleRow.createCell(21).setCellValue("应缴金额");
			// 减免金额
			titleRow.createCell(22).setCellValue("减免金额");
			// 实际应缴金额
			titleRow.createCell(23).setCellValue("实际应缴金额");
			// 实缴总金额
			titleRow.createCell(24).setCellValue("实缴总金额");
			// 上年度未缴金额
			titleRow.createCell(25).setCellValue("上年度未缴金额");
			// 补缴金额
			titleRow.createCell(26).setCellValue("补缴金额");
			// 滞纳金金额
			titleRow.createCell(27).setCellValue("滞纳金金额");
			// 是否减免滞纳金
			titleRow.createCell(28).setCellValue("是否减免滞纳金");
			// 初审日期
			titleRow.createCell(29).setCellValue("初审日期");
			// 初审人意见
			titleRow.createCell(30).setCellValue("初审人意见");
			// 复审日期
			titleRow.createCell(31).setCellValue("复审日期");
			// 复审人意见
			titleRow.createCell(32).setCellValue("复审人意见");
			// 是否免缴
			titleRow.createCell(33).setCellValue("是否免缴");
			// 未审年数
			titleRow.createCell(34).setCellValue("未审年数");
			// 缴款时间
			titleRow.createCell(35).setCellValue("缴款时间");
			// 缴款金额
			titleRow.createCell(36).setCellValue("缴款金额");
			// 缴款操作人
			titleRow.createCell(37).setCellValue("缴款操作人");
			// 付款票据号
			titleRow.createCell(38).setCellValue("付款票据号");
			// 缴款方式
			titleRow.createCell(39).setCellValue("缴款方式");
			// 打票日期
			titleRow.createCell(40).setCellValue("打票日期");
			// 换票日期
			titleRow.createCell(41).setCellValue("换票日期");
			// 是否返票
			titleRow.createCell(42).setCellValue("是否返票");
			// 是否作废票据号
			titleRow.createCell(43).setCellValue("是否作废票据号");
			// 缴款方式
			titleRow.createCell(44).setCellValue("缴款方式");
		}
		int startRow = (page - 1) * pageSize;
		// 插入的起始行
		int k = 0;
		for (int i = 1 + startRow; i <= (page - 1) * pageSize
				+ paymentList.size(); i++) {
			Payment payment = paymentList.get(k);
			k++;
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			// HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			// 档案编码
			row.createCell(0).setCellValue(
					payment.getPaymentCompany().getCompanyCode());
			// 主管部门
			row.createCell(1).setCellValue(
					payment.getPaymentCompany().getCompanyManagement());
			// 企业名称
			row.createCell(2).setCellValue(
					payment.getPaymentCompany().getCompanyName());
			// 企业法人
			row.createCell(3).setCellValue(
					payment.getPaymentCompany().getCompanyLegal());
			// 企业联系人
			row.createCell(4).setCellValue(
					payment.getPaymentCompany().getCompanyContactPerson());
			// 组织机构代码证号
			row.createCell(5).setCellValue(
					payment.getPaymentCompany().getCompanyOrganizationCode());
			// 税务编码
			row.createCell(6).setCellValue(
					payment.getPaymentCompany().getCompanyTaxCode());
			// 企业地址
			row.createCell(7).setCellValue(
					payment.getPaymentCompany().getCompanyAddress());
			// 企业邮政编码
			row.createCell(8).setCellValue(
					payment.getPaymentCompany().getCompanyZipCode());
			// 传真
			row.createCell(9).setCellValue(
					payment.getPaymentCompany().getCompanyFax());
			// //企业类型
			// row.createCell(10).setCellValue(payment.getPaymentCompany().getCompanyType().getCompanyType());
			// //企业经济类型
			// row.createCell(11).setCellValue(payment.getPaymentCompany().getCompanyEconomyType().getCompanyEconomyType());
			// //公司性质
			// row.createCell(12).setCellValue(payment.getPaymentCompany().getCompanyProperty().getCompanyProperty());
			// 联系电话
			row.createCell(10).setCellValue(
					payment.getPaymentCompany().getCompanyPhone());
			// 企业联系人手机
			row.createCell(11).setCellValue(
					payment.getPaymentCompany().getCompanyMobile());
			// 开户银行
			row.createCell(12).setCellValue(
					payment.getPaymentCompany().getCompanyBank());
			// 银行账户
			row.createCell(13).setCellValue(
					payment.getPaymentCompany().getCompanyBankAccount());
			// 备注
			row.createCell(14).setCellValue(
					payment.getPaymentCompany().getCompanyRemark());
			// 审核年度
			row.createCell(15).setCellValue(payment.getAudit().getYear());
			// //审核状态
			// row.createCell(19).setCellValue(payment.getAudit().getAuditProcessStatus().getAuditProcessStatus());
			// 员工总数
			row.createCell(16).setCellValue(
					payment.getAudit().getCompanyEmpTotal());
			// 残疾员工总数/已录入数
			row.createCell(17).setCellValue(
					payment.getAudit().getCompanyHandicapTotal());
			// 预计残疾人数
			row.createCell(18).setCellValue(
					payment.getAudit().getCompanyPredictTotal());
			// 应安排残疾人数
			if (payment.getAudit().getCompanyShouldTotal() != null) {
				row.createCell(19).setCellValue(
						payment.getAudit().getCompanyShouldTotal()
								.doubleValue());
			}
			// 已安排残疾人数
			row.createCell(20).setCellValue(
					payment.getAudit().getCompanyAlreadyTotal());
			// 应缴金额
			if (payment.getAudit().getAmountPayable() != null) {
				row.createCell(21).setCellValue(
						payment.getAudit().getAmountPayable().doubleValue());
			}
			// 减免金额
			if (payment.getAudit().getReductionAmount() != null) {
				row.createCell(22).setCellValue(
						payment.getAudit().getReductionAmount().doubleValue());
			}
			// 实际应缴金额
			if (payment.getAudit().getActualAmount() != null) {
				row.createCell(23).setCellValue(
						payment.getAudit().getActualAmount().doubleValue());
			}
			// 实缴总金额
			if (payment.getAudit().getPayAmount() != null) {
				row.createCell(24).setCellValue(
						payment.getAudit().getPayAmount().doubleValue());
			}
			// 上年度未缴金额
			if (payment.getAudit().getRemainAmount() != null) {
				row.createCell(25).setCellValue(
						payment.getAudit().getRemainAmount().doubleValue());
			}
			// 补缴金额
			if (payment.getAudit().getComplementAmount() != null) {
				row.createCell(26).setCellValue(
						payment.getAudit().getComplementAmount().doubleValue());
			}
			// 滞纳金金额
			if (payment.getAudit().getDelayPayAmount() != null) {
				row.createCell(27).setCellValue(
						payment.getAudit().getDelayPayAmount().doubleValue());
			}
			// 是否减免滞纳金
			if (payment.getAudit().getIsDelayPay()) {
				row.createCell(28).setCellValue("是");
			} else {
				row.createCell(28).setCellValue("否");
			}
			// //初审人
			// row.createCell(33).setCellValue("初审人");
			// 初审日期
			if (payment.getAudit().getInitAuditDate() != null) {
				row.createCell(29).setCellValue(
						KitService.getStringDate(payment.getAudit().getInitAuditDate()));
			}
			// 初审人意见
			row.createCell(30).setCellValue(
					payment.getAudit().getInitAuditComment());
			// //复审人
			// row.createCell(36).setCellValue("复审人");
			// 复审日期
			if (payment.getAudit().getVerifyAuditDate() != null) {
				row.createCell(31).setCellValue(
						KitService.getStringDate(payment.getAudit().getVerifyAuditDate()));
			}
			// 复审人意见
			row.createCell(32).setCellValue(
					payment.getAudit().getVerifyAuditComment());
			// 是否免缴
			if (payment.getAudit().getIsExempt()) {
				row.createCell(33).setCellValue("是");
			} else {
				row.createCell(33).setCellValue("否");
			}
			// 未审年数
			row.createCell(34).setCellValue(
					payment.getAudit().getUnauditYears());
			// 缴款时间
			row.createCell(35).setCellValue(KitService.getStringDate(payment.getPaymentDate()));
			// 缴款金额
			if (payment.getPaymentMoney() != null) {
				row.createCell(36).setCellValue(
						payment.getPaymentMoney().doubleValue());
			}
			// 缴款操作人
			row.createCell(37).setCellValue(
					payment.getPaymentPerson().getUserRealName());
			// 付款票据号
			row.createCell(38).setCellValue(payment.getPaymentBill());
			// 缴款方式
			row.createCell(39).setCellValue(payment.getPaymentType().getText());
			// 打票日期
			if (payment.getBillPrintDate() != null) {
				row.createCell(40).setCellValue(KitService.getStringDate(payment.getBillPrintDate()));
			}
			// 换票日期
			if (payment.getBillExchangeDate() != null) {
				row.createCell(41).setCellValue(KitService.getStringDate(payment.getBillExchangeDate()));
			}
			// 是否返票
			if (payment.getBillReturn()) {
				row.createCell(42).setCellValue("是");
			} else {
				row.createCell(42).setCellValue("否");
			}
			// 是否作废票据号
			if (payment.getBillObsolete()) {
				row.createCell(43).setCellValue("是");
			} else {
				row.createCell(43).setCellValue("否");
			}
			// 缴款方式
			row.createCell(44).setCellValue(
					payment.getPaymentExceptional().getPaymentExceptional());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			// companyList.clear();
			// companyList = null;
//			os = null;
//			wb = null;
		//	System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	// @Override
	// public boolean createPaymentExcel(String FilePath, List<Payment>
	// paymentList) {
	// // 创建Excel的工作书册 Workbook,对应到一个excel文档
	// HSSFWorkbook wb = new HSSFWorkbook();
	// // 创建Excel的工作sheet,对应到一个excel文档的tab
	// HSSFSheet sheet = wb.createSheet("sheet1");
	//
	// // 创建一个头部Excel的单元格
	// HSSFRow titleRow = sheet.createRow(0);
	// // 设置单元格的样式格式
	// // 档案编码
	// titleRow.createCell(0).setCellValue("档案编码");
	// // 主管部门
	// titleRow.createCell(1).setCellValue("主管部门");
	// // 企业名称
	// titleRow.createCell(2).setCellValue("企业名称");
	// // 企业法人
	// titleRow.createCell(3).setCellValue("企业法人");
	// // 企业联系人
	// titleRow.createCell(4).setCellValue("企业联系人");
	// // 组织机构代码证号
	// titleRow.createCell(5).setCellValue("组织机构代码证号");
	// // 税务编码
	// titleRow.createCell(6).setCellValue("税务编码");
	// // 企业地址
	// titleRow.createCell(7).setCellValue("企业地址");
	// // 企业邮政编码
	// titleRow.createCell(8).setCellValue("企业邮政编码");
	// // 传真
	// titleRow.createCell(9).setCellValue("传真");
	// // //企业类型
	// // titleRow.createCell(10).setCellValue("企业类型");
	// // //企业经济类型
	// // titleRow.createCell(11).setCellValue("企业经济类型");
	// // //公司性质
	// // titleRow.createCell(12).setCellValue("公司性质");
	// // 联系电话
	// titleRow.createCell(13).setCellValue("联系电话");
	// // 企业联系人手机
	// titleRow.createCell(14).setCellValue("企业联系人手机");
	// // 开户银行
	// titleRow.createCell(15).setCellValue("开户银行");
	// // 银行账户
	// titleRow.createCell(16).setCellValue("银行账户");
	// // 备注
	// titleRow.createCell(17).setCellValue("备注");
	// // 审核年度
	// titleRow.createCell(18).setCellValue("审核年度");
	// // //审核状态
	// // titleRow.createCell(19).setCellValue("审核状态");
	// // 员工总数
	// titleRow.createCell(20).setCellValue("员工总数");
	// // 残疾员工总数/已录入数
	// titleRow.createCell(21).setCellValue("残疾员工总数/已录入数");
	// // 预计残疾人数
	// titleRow.createCell(22).setCellValue("预计残疾人数");
	// // 应安排残疾人数
	// titleRow.createCell(23).setCellValue("应安排残疾人数");
	// // 已安排残疾人数
	// titleRow.createCell(24).setCellValue("已安排残疾人数");
	// // 应缴金额
	// titleRow.createCell(25).setCellValue("应缴金额");
	// // 减免金额
	// titleRow.createCell(26).setCellValue("减免金额");
	// // 实际应缴金额
	// titleRow.createCell(27).setCellValue("实际应缴金额");
	// // 实缴总金额
	// titleRow.createCell(28).setCellValue("实缴总金额");
	// // 上年度未缴金额
	// titleRow.createCell(29).setCellValue("上年度未缴金额");
	// // 补缴金额
	// titleRow.createCell(30).setCellValue("补缴金额");
	// // 滞纳金金额
	// titleRow.createCell(31).setCellValue("滞纳金金额");
	// // 是否减免滞纳金
	// titleRow.createCell(32).setCellValue("是否减免滞纳金");
	// // //初审人
	// // titleRow.createCell(33).setCellValue("初审人");
	// // 初审日期
	// titleRow.createCell(34).setCellValue("初审日期");
	// // 初审人意见
	// titleRow.createCell(35).setCellValue("初审人意见");
	// // //复审人
	// // titleRow.createCell(36).setCellValue("复审人");
	// // 复审日期
	// titleRow.createCell(37).setCellValue("复审日期");
	// // 复审人意见
	// titleRow.createCell(38).setCellValue("复审人意见");
	// // 是否免缴
	// titleRow.createCell(39).setCellValue("是否免缴");
	// // 未审年数
	// titleRow.createCell(40).setCellValue("未审年数");
	// // 缴款时间
	// titleRow.createCell(41).setCellValue("缴款时间");
	// // 缴款金额
	// titleRow.createCell(42).setCellValue("缴款金额");
	// // 缴款操作人
	// titleRow.createCell(43).setCellValue("缴款操作人");
	// // 付款票据号
	// titleRow.createCell(44).setCellValue("付款票据号");
	// // 缴款方式
	// titleRow.createCell(45).setCellValue("缴款方式");
	// // 打票日期
	// titleRow.createCell(46).setCellValue("打票日期");
	// // 换票日期
	// titleRow.createCell(47).setCellValue("换票日期");
	// // 是否返票
	// titleRow.createCell(48).setCellValue("是否返票");
	// // 是否作废票据号
	// titleRow.createCell(49).setCellValue("是否作废票据号");
	// // 缴款方式
	// titleRow.createCell(50).setCellValue("缴款方式");
	//
	// // headell = headRow.createCell(1);
	// // headell.setCellValue("税务编码");
	// // headell = headRow.createCell(2);
	// // headell.setCellValue("单位名称");
	// // sheet.setColumnWidth(2, 12000); // 设置第二列的宽度
	// //
	// // headell = headRow.createCell(3);
	// // headell.setCellValue("法人代表");
	// // headell = headRow.createCell(4);
	// // headell.setCellValue("联系人");
	// //
	// // headell = headRow.createCell(5);
	// // headell.setCellValue("电话号码");
	// //
	// // headell = headRow.createCell(6);
	// // headell.setCellValue("手机号码");
	// //
	// // headell = headRow.createCell(7);
	// // headell.setCellValue("邮编");
	// // headell = headRow.createCell(8);
	// // headell.setCellValue("单位地址");
	// // sheet.setColumnWidth(8, 12000);
	//
	// for (int i = 1; i <= paymentList.size(); i++) {
	// Payment payment = paymentList.get(i - 1);
	// // 创建一个Excel的单元格
	// HSSFRow row = sheet.createRow(i);
	// // HSSFCell cell = row.createCell(0);
	// // 设置单元格的样式格式
	// // 档案编码
	// row.createCell(0).setCellValue(
	// payment.getPaymentCompany().getCompanyCode());
	// // 主管部门
	// row.createCell(1).setCellValue(
	// payment.getPaymentCompany().getCompanyManagement());
	// // 企业名称
	// row.createCell(2).setCellValue(
	// payment.getPaymentCompany().getCompanyName());
	// // 企业法人
	// row.createCell(3).setCellValue(
	// payment.getPaymentCompany().getCompanyLegal());
	// // 企业联系人
	// row.createCell(4).setCellValue(
	// payment.getPaymentCompany().getCompanyContactPerson());
	// // 组织机构代码证号
	// row.createCell(5).setCellValue(
	// payment.getPaymentCompany().getCompanyOrganizationCode());
	// // 税务编码
	// row.createCell(6).setCellValue(
	// payment.getPaymentCompany().getCompanyTaxCode());
	// // 企业地址
	// row.createCell(7).setCellValue(
	// payment.getPaymentCompany().getCompanyAddress());
	// // 企业邮政编码
	// row.createCell(8).setCellValue(
	// payment.getPaymentCompany().getCompanyZipCode());
	// // 传真
	// row.createCell(9).setCellValue(
	// payment.getPaymentCompany().getCompanyFax());
	// // //企业类型
	// //
	// row.createCell(10).setCellValue(payment.getPaymentCompany().getCompanyType().getCompanyType());
	// // //企业经济类型
	// //
	// row.createCell(11).setCellValue(payment.getPaymentCompany().getCompanyEconomyType().getCompanyEconomyType());
	// // //公司性质
	// //
	// row.createCell(12).setCellValue(payment.getPaymentCompany().getCompanyProperty().getCompanyProperty());
	// // 联系电话
	// row.createCell(13).setCellValue(
	// payment.getPaymentCompany().getCompanyPhone());
	// // 企业联系人手机
	// row.createCell(14).setCellValue(
	// payment.getPaymentCompany().getCompanyMobile());
	// // 开户银行
	// row.createCell(15).setCellValue(
	// payment.getPaymentCompany().getCompanyBank());
	// // 银行账户
	// row.createCell(16).setCellValue(
	// payment.getPaymentCompany().getCompanyBankAccount());
	// // 备注
	// row.createCell(17).setCellValue(
	// payment.getPaymentCompany().getCompanyRemark());
	// // 审核年度
	// row.createCell(18).setCellValue(payment.getAudit().getYear());
	// // //审核状态
	// //
	// row.createCell(19).setCellValue(payment.getAudit().getAuditProcessStatus().getAuditProcessStatus());
	// // 员工总数
	// row.createCell(20).setCellValue(
	// payment.getAudit().getCompanyEmpTotal());
	// // 残疾员工总数/已录入数
	// row.createCell(21).setCellValue(
	// payment.getAudit().getCompanyHandicapTotal());
	// // 预计残疾人数
	// row.createCell(22).setCellValue(
	// payment.getAudit().getCompanyPredictTotal());
	// // 应安排残疾人数
	// if (payment.getAudit().getCompanyShouldTotal() != null) {
	// row.createCell(23).setCellValue(
	// payment.getAudit().getCompanyShouldTotal()
	// .doubleValue());
	// }
	// // 已安排残疾人数
	// row.createCell(24).setCellValue(
	// payment.getAudit().getCompanyAlreadyTotal());
	// // 应缴金额
	// if (payment.getAudit().getAmountPayable() != null) {
	// row.createCell(25).setCellValue(
	// payment.getAudit().getAmountPayable().doubleValue());
	// }
	// // 减免金额
	// if (payment.getAudit().getReductionAmount() != null) {
	// row.createCell(26).setCellValue(
	// payment.getAudit().getReductionAmount().doubleValue());
	// }
	// // 实际应缴金额
	// if (payment.getAudit().getActualAmount() != null) {
	// row.createCell(27).setCellValue(
	// payment.getAudit().getActualAmount().doubleValue());
	// }
	// // 实缴总金额
	// if (payment.getAudit().getPayAmount() != null) {
	// row.createCell(28).setCellValue(
	// payment.getAudit().getPayAmount().doubleValue());
	// }
	// // 上年度未缴金额
	// if (payment.getAudit().getRemainAmount() != null) {
	// row.createCell(29).setCellValue(
	// payment.getAudit().getRemainAmount().doubleValue());
	// }
	// // 补缴金额
	// if (payment.getAudit().getComplementAmount() != null) {
	// row.createCell(30).setCellValue(
	// payment.getAudit().getComplementAmount().doubleValue());
	// }
	// // 滞纳金金额
	// if (payment.getAudit().getDelayPayAmount() != null) {
	// row.createCell(31).setCellValue(
	// payment.getAudit().getDelayPayAmount().doubleValue());
	// }
	// // 是否减免滞纳金
	// if (payment.getAudit().getIsDelayPay()) {
	// row.createCell(32).setCellValue("是");
	// } else {
	// row.createCell(32).setCellValue("否");
	// }
	// // //初审人
	// // row.createCell(33).setCellValue("初审人");
	// // 初审日期
	// if (payment.getAudit().getInitAuditDate() != null) {
	// row.createCell(34).setCellValue(
	// payment.getAudit().getInitAuditDate());
	// }
	// // 初审人意见
	// row.createCell(35).setCellValue(
	// payment.getAudit().getInitAuditComment());
	// // //复审人
	// // row.createCell(36).setCellValue("复审人");
	// // 复审日期
	// if (payment.getAudit().getVerifyAuditDate() != null) {
	// row.createCell(37).setCellValue(
	// payment.getAudit().getVerifyAuditDate());
	// }
	// // 复审人意见
	// row.createCell(38).setCellValue(
	// payment.getAudit().getVerifyAuditComment());
	// // 是否免缴
	// if (payment.getAudit().getIsExempt()) {
	// row.createCell(39).setCellValue("是");
	// } else {
	// row.createCell(39).setCellValue("否");
	// }
	// // 未审年数
	// row.createCell(40).setCellValue(
	// payment.getAudit().getUnauditYears());
	// // 缴款时间
	// row.createCell(41).setCellValue(payment.getPaymentDate());
	// // 缴款金额
	// if (payment.getPaymentMoney() != null) {
	// row.createCell(42).setCellValue(
	// payment.getPaymentMoney().doubleValue());
	// }
	// // 缴款操作人
	// row.createCell(43).setCellValue(
	// payment.getPaymentPerson().getUserRealName());
	// // 付款票据号
	// row.createCell(44).setCellValue(payment.getPaymentBill());
	// // 缴款方式
	// row.createCell(45).setCellValue(payment.getPaymentType().getText());
	// // 打票日期
	// if (payment.getBillPrintDate() != null) {
	// row.createCell(46).setCellValue(payment.getBillPrintDate());
	// }
	// // 换票日期
	// if (payment.getBillExchangeDate() != null) {
	// row.createCell(47).setCellValue(payment.getBillExchangeDate());
	// }
	// // 是否返票
	// if (payment.getBillReturn()) {
	// row.createCell(48).setCellValue("是");
	// } else {
	// row.createCell(48).setCellValue("否");
	// }
	// // 是否作废票据号
	// if (payment.getBillObsolete()) {
	// row.createCell(49).setCellValue("是");
	// } else {
	// row.createCell(49).setCellValue("否");
	// }
	// // 缴款方式
	// row.createCell(50).setCellValue(
	// payment.getPaymentExceptional().getPaymentExceptional());
	// }
	// try {
	// FileOutputStream os = new FileOutputStream(FilePath);
	// wb.write(os);
	// os.flush();
	// os.close();
	// // companyList.clear();
	// // companyList = null;
	// os = null;
	// wb = null;
	// System.gc();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return true;
	// }

}
