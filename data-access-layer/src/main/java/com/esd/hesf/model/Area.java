package com.esd.hesf.model;

/**
 * 地区类
 * 
 * @author Administrator
 * 
 */
public class Area extends CommonKey{

	private String code; // 地区代码 - 主键
	private String name; // 地名
	private String pyName; // 地名全拼
	private String abbr; // 地名缩写
	private String mark; // 标记，例如热点城市等等

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "area [code = " + code + ", name = " + name + ", pyName = " + pyName + ", mark = " + mark + "]";
	}

	public Area() {
	}

	public Area(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}