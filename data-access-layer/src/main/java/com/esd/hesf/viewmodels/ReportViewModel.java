package com.esd.hesf.viewmodels;

import java.math.BigDecimal;

/**
 * 统计报表 视图模型
 * 
 * @author Administrator
 * 
 */
public class ReportViewModel {

	private String reportName; // 报表依据类型名, 如单位类型, 地区, 经济类型等 11
	private Integer unitNum; // 单位总数 11
	private Integer empTotal; // 单位总人数11
	private Integer unAudit; // 待初审单位数
	private Integer unReAudit; // 已初审, 待复核单位数
	private Integer auditOk;// 已复核, 达标单位数
	private Integer unauditOk; // 已复核, 未达标单位数
	private BigDecimal shouldTotal; // 应安排人数11
	private BigDecimal alreadyTotal; // 已经安排人数11
	private BigDecimal lessTotal; // 少安排人数
	private BigDecimal amountPayable; // 应缴金额11
	private BigDecimal reductionAmount; // 减免金额11
	private BigDecimal actualAmount; // 实际应缴金额11
	private BigDecimal alreadyAmount; // 已缴金额11
	private Integer companyType; // 公司类型id, 不做为前台显示字段 xxx
	private String areaCode; // 地区code, 不作为前台显示字段 xxx
	private Integer companyEconomyType; // 经济类型id, 不做为前台显示字段 xxx

	@Override
	public String toString() {
		return "ReportViewModel [reportName=" + reportName + ", unitNum=" + unitNum + ", empTotal=" + empTotal + ", unAudit=" + unAudit + ", unReAudit=" + unReAudit + ", auditOk=" + auditOk
				+ ", unauditOk=" + unauditOk + ", shouldTotal=" + shouldTotal + ", alreadyTotal=" + alreadyTotal + ", lessTotal=" + lessTotal + ", amountPayable=" + amountPayable
				+ ", reductionAmount=" + reductionAmount + ", actualAmount=" + actualAmount + ", alreadyAmount=" + alreadyAmount + "]";
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getUnitNum() {
		if (this.unitNum == null) {
			return 0;
		}
		return unitNum;
	}

	public void setUnitNum(Integer unitNum) {
		if (this.unitNum == null) {
			this.unitNum = 0;
		}
		this.unitNum = unitNum;
	}

	public Integer getEmpTotal() {
		if (this.empTotal == null) {
			return 0;
		}
		return empTotal;
	}

	public void setEmpTotal(Integer empTotal) {
		this.empTotal = empTotal;
	}

	public Integer getUnAudit() {
		if (unAudit == null) {
			return 0;
		}
		return unAudit;
	}

	public void setUnAudit(Integer unAudit) {
		if (this.unAudit == null) {
			this.unAudit = 0;
		}
		this.unAudit += unAudit;
	}

	public Integer getUnReAudit() {
		if (unReAudit == null) {
			return 0;
		}
		return unReAudit;
	}

	public void setUnReAudit(Integer unReAudit) {
		if (this.unReAudit == null) {
			this.unReAudit = 0;
		}
		this.unReAudit += unReAudit;
	}

	public Integer getAuditOk() {
		if (auditOk == null) {
			return 0;
		}
		return auditOk;
	}

	public void setAuditOk(Integer auditOk) {
		if (this.auditOk == null) {
			this.auditOk = 0;
		}
		this.auditOk += auditOk;
	}

	public Integer getUnauditOk() {
		if (unauditOk == null) {
			return 0;
		}
		return unauditOk;
	}

	public void setUnauditOk(Integer unauditOk) {
		if (this.unauditOk == null) {
			this.unauditOk = 0;
		}
		this.unauditOk += unauditOk;
	}

	public BigDecimal getShouldTotal() {
		if (shouldTotal == null) {
			return new BigDecimal(0);
		}
		return shouldTotal;
	}

	public void setShouldTotal(BigDecimal shouldTotal) {
		this.shouldTotal = shouldTotal;
	}

	public BigDecimal getAlreadyTotal() {
		if (alreadyTotal == null) {
			return new BigDecimal(0);
		}
		return alreadyTotal;
	}

	public void setAlreadyTotal(BigDecimal alreadyTotal) {
		this.alreadyTotal = alreadyTotal;
	}

	public BigDecimal getLessTotal() {
		this.lessTotal = this.getShouldTotal().subtract(this.getAlreadyTotal());
		return this.lessTotal;
	}

	public void setLessTotal(BigDecimal lessTotal) {
		this.lessTotal = lessTotal;
	}

	public BigDecimal getAmountPayable() {
		if (amountPayable == null) {
			return new BigDecimal(0);
		}
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public BigDecimal getReductionAmount() {
		if (reductionAmount == null) {
			return new BigDecimal(0);
		}
		return reductionAmount;
	}

	public void setReductionAmount(BigDecimal reductionAmount) {
		this.reductionAmount = reductionAmount;
	}

	public BigDecimal getActualAmount() {
		if (actualAmount == null) {
			return new BigDecimal(0);
		}
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getAlreadyAmount() {
		if (alreadyAmount == null) {
			return new BigDecimal(0);
		}
		return alreadyAmount;
	}

	public void setAlreadyAmount(BigDecimal alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}

	public Integer getCompanyType() {
//		if (companyType == null) {
//			return 0;
//		}
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getAreaCode() {
//		if (areaCode == null) {
//			return "";
//		}
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getCompanyEconomyType() {
//		if (companyEconomyType == null) {
//			return 0;
//		}
		return companyEconomyType;
	}

	public void setCompanyEconomyType(Integer companyEconomyType) {
		this.companyEconomyType = companyEconomyType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ReportViewModel rvm = (ReportViewModel) obj;
		if (this.getCompanyType() != null) {
//			System.out.println("this.companyType: " + this.getCompanyType() + "  obj.companyType: " + rvm.getCompanyType());
			if (!rvm.getCompanyType().equals(this.getCompanyType())) {
//				System.out.println("companyType:" + this.getCompanyType() + " 存在, 不可以!");
				return false;
			}
		}
		// System.out.println("this.companyEconomyType: "+this.getCompanyEconomyType()+"  obj.companyEconomyType: "+rvm.getCompanyEconomyType());
		if (this.getCompanyEconomyType() != null) {
			if (!rvm.getCompanyEconomyType().equals(this.getCompanyEconomyType())) {
//				 System.out.println("companyEconomyType:"+this.getCompanyEconomyType()+" 存在, 不可以!");
				return false;
			}
		}
//		 System.out.println("this.areaCode: "+this.getAreaCode()+"  obj.areaCode: "+rvm.getAreaCode());
		if (this.getAreaCode() != null) {
			if (!rvm.getAreaCode().equals(this.getAreaCode())) {
//				 System.out.println("areaCode:"+this.getAreaCode()+" 存在, 不可以!");
				return false;
			}
		}
		return true;
	}

}
