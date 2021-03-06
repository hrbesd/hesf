/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CalculateModel {

	private DecimalFormat df = new DecimalFormat("0.00");
	private DecimalFormat df4 = new DecimalFormat("0.000");

	private Integer zaiZhiYuanGongZongShu;
	private BigDecimal yingAnPaiCanJiRen;
	private Integer yiAnPaiCanJiRen;
	private Integer yiLuRuCanJiRen;
	private Integer yuDingCanJiRen;

	private BigDecimal shangNianDuWeiJiaoBaoZhangJin;
	private BigDecimal yingJiaoJinE;
	private BigDecimal jianJiaoJinE;
	private BigDecimal buJiaoJinE;
	private BigDecimal shiJiaoJinE;
	private BigDecimal zhiNaJin;
	private Integer zhiNaJinTianShu;
	private BigDecimal shiJiaoZongJinE;
	private String year;
	private Integer companyId;
	private Boolean mianZhiNaJin;
	private Boolean mianJiao;

	private String s_zaiZhiYuanGongZongShu;
	private String s_yingAnPaiCanJiRen;
	private String s_yiAnPaiCanJiRen;
	private String s_yiLuRuCanJiRen;
	private String s_yuDingCanJiRen;

	private String s_shangNianDuWeiJiaoBaoZhangJin;
	private String s_yingJiaoJinE;
	private String s_jianJiaoJinE;
	private String s_buJiaoJinE;
	private String s_shiJiaoJinE;
	private String s_zhiNaJin;
	private String s_shiJiaoZongJinE;
	private String s_zhiNaJinTianShu;
	private List<AccountModel> weiShenMingXi;
	private List<AccountModel> qianJiaoMingXi;
	private List<AccountModel> weiJiaoMingXi;

	public Integer getZaiZhiYuanGongZongShu() {
		return zaiZhiYuanGongZongShu;
	}

	public void setZaiZhiYuanGongZongShu(Integer zaiZhiYuanGongZongShu) {
		if (zaiZhiYuanGongZongShu == null) {
			zaiZhiYuanGongZongShu = 0;
		}
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
		if (yuDingCanJiRen == null) {
			yuDingCanJiRen = 0;
		}
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

	public BigDecimal getYingJiaoJinE() {
		return yingJiaoJinE;
	}

	public void setYingJiaoJinE(BigDecimal yingJiaoJinE) {
		this.yingJiaoJinE = yingJiaoJinE;
		this.s_yingJiaoJinE = String.valueOf(df.format(yingJiaoJinE));
	}

	public BigDecimal getJianJiaoJinE() {
		return jianJiaoJinE;
	}

	public void setJianJiaoJinE(BigDecimal jianJiaoJinE) {
		if (jianJiaoJinE == null) {
			jianJiaoJinE = new BigDecimal(0.00);
		}
		this.jianJiaoJinE = jianJiaoJinE;
		this.s_jianJiaoJinE = String.valueOf(df.format(jianJiaoJinE));
	}

	public BigDecimal getBuJiaoJinE() {
		return buJiaoJinE;
	}

	public void setBuJiaoJinE(BigDecimal buJiaoJinE) {
		this.buJiaoJinE = buJiaoJinE;
		this.s_buJiaoJinE = String.valueOf(df.format(buJiaoJinE));
	}

	public BigDecimal getShiJiaoJinE() {
		return shiJiaoJinE;
	}

	public void setShiJiaoJinE(BigDecimal shiJiaoJinE) {
		this.shiJiaoJinE = shiJiaoJinE;
		this.s_shiJiaoJinE = String.valueOf(df.format(shiJiaoJinE));
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

	public String getS_yingJiaoJinE() {
		return s_yingJiaoJinE;
	}

	public void setS_yingJiaoJinE(String s_yingJiaoJinE) {
		this.s_yingJiaoJinE = s_yingJiaoJinE;
	}

	public String getS_jianJiaoJinE() {
		return s_jianJiaoJinE;
	}

	public void setS_jianJiaoJinE(String s_jianJiaoJinE) {
		this.s_jianJiaoJinE = s_jianJiaoJinE;
	}

	public String getS_buJiaoJinE() {
		return s_buJiaoJinE;
	}

	public void setS_buJiaoJinE(String s_buJiaoJinE) {
		this.s_buJiaoJinE = s_buJiaoJinE;
	}

	public String getS_shiJiaoJinE() {
		return s_shiJiaoJinE;
	}

	public void setS_shiJiaoJinE(String s_shiJiaoJinE) {
		this.s_shiJiaoJinE = s_shiJiaoJinE;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getZhiNaJin() {
		return zhiNaJin;
	}

	public void setZhiNaJin(BigDecimal zhiNaJin) {
		this.zhiNaJin = zhiNaJin;
		this.s_zhiNaJin = String.valueOf(df.format(zhiNaJin));
	}

	public BigDecimal getShiJiaoZongJinE() {
		return shiJiaoZongJinE;
	}

	public void setShiJiaoZongJinE(BigDecimal shiJiaoZongJinE) {
		this.shiJiaoZongJinE = shiJiaoZongJinE;
		this.s_shiJiaoZongJinE = String.valueOf(df.format(shiJiaoZongJinE));
	}

	public String getS_zhiNaJin() {
		return s_zhiNaJin;
	}

	public void setS_zhiNaJin(String s_zhiNaJin) {
		this.s_zhiNaJin = s_zhiNaJin;
	}

	public String getS_shiJiaoZongJinE() {
		return s_shiJiaoZongJinE;
	}

	public void setS_shiJiaoZongJinE(String s_shiJiaoZongJinE) {
		this.s_shiJiaoZongJinE = s_shiJiaoZongJinE;
	}

	public Integer getZhiNaJinTianShu() {
		return zhiNaJinTianShu;
	}

	public void setZhiNaJinTianShu(Integer zhiNaJinTianShu) {
		if (zhiNaJinTianShu == null) {
			zhiNaJinTianShu = 0;
		}
		this.zhiNaJinTianShu = zhiNaJinTianShu;
		this.s_zhiNaJinTianShu = zhiNaJinTianShu.toString();
	}

	public String getS_zhiNaJinTianShu() {
		return s_zhiNaJinTianShu;
	}

	public void setS_zhiNaJinTianShu(String s_zhiNaJinTianShu) {
		this.s_zhiNaJinTianShu = s_zhiNaJinTianShu;
	}

	public List<AccountModel> getWeiShenMingXi() {
		return weiShenMingXi;
	}

	public void setWeiShenMingXi(List<AccountModel> weiShenMingXi) {
		this.weiShenMingXi = weiShenMingXi;
	}

	public List<AccountModel> getQianJiaoMingXi() {
		return qianJiaoMingXi;
	}

	public void setQianJiaoMingXi(List<AccountModel> qianJiaoMingXi) {
		this.qianJiaoMingXi = qianJiaoMingXi;
	}

	public Boolean getMianZhiNaJin() {
		return mianZhiNaJin;
	}

	public void setMianZhiNaJin(Boolean mianZhiNaJin) {
		this.mianZhiNaJin = mianZhiNaJin;
	}

	public Boolean getMianJiao() {
		return mianJiao;
	}

	public void setMianJiao(Boolean mianJiao) {
		this.mianJiao = mianJiao;
	}

	public List<AccountModel> getWeiJiaoMingXi() {
		return weiJiaoMingXi;
	}

	public void setWeiJiaoMingXi(List<AccountModel> weiJiaoMingXi) {
		this.weiJiaoMingXi = weiJiaoMingXi;
	}

}
