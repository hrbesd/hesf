package com.esd.hesf.model;

/**
 * 法定假日pojo类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-10
 */
public class LegalHoliday extends PrimaryKey_String {

	private String legaldate; // 法定假日
	private String name; // 名称
	private String remark; // 备注名称

	@Override
	public String toString() {
		return "LegalHoliday [legaldate=" + legaldate + ", name=" + name
				+ ", remark=" + remark + "]";
	}

	public String getLegaldate() {
		return legaldate;
	}

	public void setLegaldate(String legaldate) {
		this.legaldate = legaldate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}