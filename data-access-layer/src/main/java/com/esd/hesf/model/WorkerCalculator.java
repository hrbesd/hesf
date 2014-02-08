package com.esd.hesf.model;

import java.math.BigDecimal;

/**
 * 残疾人计算类 不对应数据库表, 只为计算用
 * 
 * @author Administrator
 * 
 */
public class WorkerCalculator {

	private Integer type; // 残疾种类id
	private BigDecimal per;// 残疾人数对应比例
	private int lvl; // 残疾等级id

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getPer() {
		return per;
	}

	public void setPer(BigDecimal per) {
		this.per = per;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

}
