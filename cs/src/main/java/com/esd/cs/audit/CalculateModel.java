package com.esd.cs.audit;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculateModel {

	private DecimalFormat df = new DecimalFormat("0.00");

	private Integer zaiZhiYuanGongZongShu;
	private BigDecimal yingAnPaiCanJiRen;
	private Integer yiAnPaiCanJiRen;
	private Integer yiLuRuCanJiRen;
	private Integer yuDingCanJiRen;

	private BigDecimal shangNianDuWeiJiaoBaoZhangJin;
	private BigDecimal yingJiaoJingE;
	private BigDecimal jianJiaoJingE;
	private BigDecimal buJiaoJingE;
	private BigDecimal shiJiaoJingE;
	private String year;
	private String companyCode;

	private String s_zaiZhiYuanGongZongShu;
	private String s_yingAnPaiCanJiRen;
	private String s_yiAnPaiCanJiRen;
	private String s_yiLuRuCanJiRen;
	private String s_yuDingCanJiRen;

	private String s_shangNianDuWeiJiaoBaoZhangJin;
	private String s_yingJiaoJingE;
	private String s_jianJiaoJingE;
	private String s_buJiaoJingE;
	private String s_shiJiaoJingE;

	@Override
	public String toString() {
		return "calculateModel [zaiZhiYuanGongZongShu=" + zaiZhiYuanGongZongShu + ", yingAnPaiCanJiRen=" + yingAnPaiCanJiRen + ", yiAnPaiCanJiRen=" + yiAnPaiCanJiRen + ", yiLuRuCanJiRen=" + yiLuRuCanJiRen + ", yuDingCanJiRen=" + yuDingCanJiRen + ", shangNianDuWeiJiaoBaoZhangJin=" + shangNianDuWeiJiaoBaoZhangJin + ", yingJiaoJingE=" + yingJiaoJingE + ", jianJiaoJingE=" + jianJiaoJingE
				+ ", buJiaoJingE=" + buJiaoJingE + ", shiJiaoJingE=" + shiJiaoJingE + "]";
	}

	public Integer getZaiZhiYuanGongZongShu() {
		return zaiZhiYuanGongZongShu;
	}

	public void setZaiZhiYuanGongZongShu(Integer zaiZhiYuanGongZongShu) {
		this.zaiZhiYuanGongZongShu = zaiZhiYuanGongZongShu;
		this.s_zaiZhiYuanGongZongShu = String.valueOf(zaiZhiYuanGongZongShu);
	}

	public BigDecimal getYingAnPaiCanJiRen() {
		return yingAnPaiCanJiRen;
	}

	public void setYingAnPaiCanJiRen(BigDecimal yingAnPaiCanJiRen) {
		this.yingAnPaiCanJiRen = yingAnPaiCanJiRen;
		this.s_yingAnPaiCanJiRen = String.valueOf(df.format(yingAnPaiCanJiRen));
	}

	public Integer getYiAnPaiCanJiRen() {
		return yiAnPaiCanJiRen;
	}

	public void setYiAnPaiCanJiRen(Integer yiAnPaiCanJiRen) {
		this.yiAnPaiCanJiRen = yiAnPaiCanJiRen;
		this.s_yiAnPaiCanJiRen = String.valueOf(yiAnPaiCanJiRen);
	}

	public Integer getYiLuRuCanJiRen() {
		return yiLuRuCanJiRen;
	}

	public void setYiLuRuCanJiRen(Integer yiLuRuCanJiRen) {
		this.yiLuRuCanJiRen = yiLuRuCanJiRen;
		this.s_yiLuRuCanJiRen = String.valueOf(yiLuRuCanJiRen);
	}

	public Integer getYuDingCanJiRen() {
		return yuDingCanJiRen;
	}

	public void setYuDingCanJiRen(Integer yuDingCanJiRen) {
		this.yuDingCanJiRen = yuDingCanJiRen;
		this.s_yuDingCanJiRen = String.valueOf(yuDingCanJiRen);
	}

	public BigDecimal getShangNianDuWeiJiaoBaoZhangJin() {
		return shangNianDuWeiJiaoBaoZhangJin;
	}

	public void setShangNianDuWeiJiaoBaoZhangJin(BigDecimal shangNianDuWeiJiaoBaoZhangJin) {
		this.shangNianDuWeiJiaoBaoZhangJin = shangNianDuWeiJiaoBaoZhangJin;
		this.s_shangNianDuWeiJiaoBaoZhangJin = String.valueOf(df.format(shangNianDuWeiJiaoBaoZhangJin));
	}

	public BigDecimal getYingJiaoJingE() {
		return yingJiaoJingE;
	}

	public void setYingJiaoJingE(BigDecimal yingJiaoJingE) {
		this.yingJiaoJingE = yingJiaoJingE;
		this.s_yingJiaoJingE = String.valueOf(df.format(yingJiaoJingE));
	}

	public BigDecimal getJianJiaoJingE() {
		return jianJiaoJingE;
	}

	public void setJianJiaoJingE(BigDecimal jianJiaoJingE) {
		this.jianJiaoJingE = jianJiaoJingE;
		this.s_jianJiaoJingE = String.valueOf(df.format(jianJiaoJingE));
	}

	public BigDecimal getBuJiaoJingE() {
		return buJiaoJingE;
	}

	public void setBuJiaoJingE(BigDecimal buJiaoJingE) {
		this.buJiaoJingE = buJiaoJingE;
		this.s_buJiaoJingE = String.valueOf(df.format(buJiaoJingE));
	}

	public BigDecimal getShiJiaoJingE() {
		return shiJiaoJingE;
	}

	public void setShiJiaoJingE(BigDecimal shiJiaoJingE) {
		this.shiJiaoJingE = shiJiaoJingE;
		this.s_shiJiaoJingE = String.valueOf(df.format(shiJiaoJingE));
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	// ==========================================================================
	public String getS_zaiZhiYuanGongZongShu() {
		return s_zaiZhiYuanGongZongShu;
	}

	public void setS_zaiZhiYuanGongZongShu(String s_zaiZhiYuanGongZongShu) {
		this.s_zaiZhiYuanGongZongShu = s_zaiZhiYuanGongZongShu;
	}

	public String getS_yingAnPaiCanJiRen() {
		return s_yingAnPaiCanJiRen;
	}

	public void setS_yingAnPaiCanJiRen(String s_yingAnPaiCanJiRen) {
		this.s_yingAnPaiCanJiRen = s_yingAnPaiCanJiRen;
	}

	public String getS_yiAnPaiCanJiRen() {
		return s_yiAnPaiCanJiRen;
	}

	public void setS_yiAnPaiCanJiRen(String s_yiAnPaiCanJiRen) {
		this.s_yiAnPaiCanJiRen = s_yiAnPaiCanJiRen;
	}

	public String getS_yiLuRuCanJiRen() {
		return s_yiLuRuCanJiRen;
	}

	public void setS_yiLuRuCanJiRen(String s_yiLuRuCanJiRen) {
		this.s_yiLuRuCanJiRen = s_yiLuRuCanJiRen;
	}

	public String getS_yuDingCanJiRen() {
		return s_yuDingCanJiRen;
	}

	public void setS_yuDingCanJiRen(String s_yuDingCanJiRen) {
		this.s_yuDingCanJiRen = s_yuDingCanJiRen;
	}

	public String getS_shangNianDuWeiJiaoBaoZhangJin() {
		return s_shangNianDuWeiJiaoBaoZhangJin;
	}

	public void setS_shangNianDuWeiJiaoBaoZhangJin(String s_shangNianDuWeiJiaoBaoZhangJin) {
		this.s_shangNianDuWeiJiaoBaoZhangJin = s_shangNianDuWeiJiaoBaoZhangJin;
	}

	public String getS_yingJiaoJingE() {
		return s_yingJiaoJingE;
	}

	public void setS_yingJiaoJingE(String s_yingJiaoJingE) {
		this.s_yingJiaoJingE = s_yingJiaoJingE;
	}

	public String getS_jianJiaoJingE() {
		return s_jianJiaoJingE;
	}

	public void setS_jianJiaoJingE(String s_jianJiaoJingE) {
		this.s_jianJiaoJingE = s_jianJiaoJingE;
	}

	public String getS_buJiaoJingE() {
		return s_buJiaoJingE;
	}

	public void setS_buJiaoJingE(String s_buJiaoJingE) {
		this.s_buJiaoJingE = s_buJiaoJingE;
	}

	public String getS_shiJiaoJingE() {
		return s_shiJiaoJingE;
	}

	public void setS_shiJiaoJingE(String s_shiJiaoJingE) {
		this.s_shiJiaoJingE = s_shiJiaoJingE;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
