package com.esd.hesf.model;

/**
 * 员工信息类
 * 
 * @author Administrator
 * 
 */
public class Worker extends PrimaryKey_Int {

	private String workerName; // 员工名
	private String workerGender;// 性别
	private String workerBirth;// 生日年月
	private String workerBirthYear;// 出生年份--供后台查询使用
	private String workerIdCard;// 身份证号
	private String careerCard;// 职业证号
	private String phone;// 电话
	private String currentJob;// 当前职位
	private String workerAddress;// 工作地点
	private Area area;// 所属地区
	private String workerHandicapCode;// 残疾证号
	private WorkerHandicapType workerHandicapType;// 残疾类型
	private WorkerHandicapLevel workerHandicapLevel;// 残疾等级
	private Boolean isCollege; // 是否是大学生
	private Boolean isRetired; // 是否内退
	private Boolean isCadre; // 是否干部
	private String remark;// 备注
	private String picTitle; // 精神残疾的图片 图片名
	private byte[] pic; // 精神残疾的图片

	public Worker() {
	}

	public Worker(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Worker [workerName=" + workerName + ", workerGender="
				+ workerGender + ", workerBirth=" + workerBirth
				+ ", workerIdCard=" + workerIdCard + ", careerCard="
				+ careerCard + ", phone=" + phone + ", currentJob="
				+ currentJob + ", workerAddress=" + workerAddress + ", area="
				+ area + ", workerHandicapCode=" + workerHandicapCode
				+ ", workerHandicapType=" + workerHandicapType
				+ ", workerHandicapLevel=" + workerHandicapLevel
				+ ", isCollege=" + isCollege + ", remark=" + remark
				+ ", getId()=" + getId() + ", getIsActive()=" + getIsActive()
				+ ", getVersion()=" + getVersion() + "]";
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getWorkerGender() {
		return workerGender;
	}

	public void setWorkerGender(String workerGender) {
		this.workerGender = workerGender;
	}

	public String getWorkerBirth() {
		return workerBirth;
	}

	public void setWorkerBirth(String workerBirth) {
		this.workerBirth = workerBirth;
	}

	public String getWorkerBirthYear() {
		return workerBirthYear;
	}

	public void setWorkerBirthYear(String workerBirthYear) {
		this.workerBirthYear = workerBirthYear;
	}

	public String getWorkerIdCard() {
		return workerIdCard;
	}

	public void setWorkerIdCard(String workerIdCard) {
		this.workerIdCard = workerIdCard;
	}

	public String getCareerCard() {
		return careerCard;
	}

	public void setCareerCard(String careerCard) {
		this.careerCard = careerCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getWorkerAddress() {
		return workerAddress;
	}

	public void setWorkerAddress(String workerAddress) {
		this.workerAddress = workerAddress;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getWorkerHandicapCode() {
		return workerHandicapCode;
	}

	public void setWorkerHandicapCode(String workerHandicapCode) {
		this.workerHandicapCode = workerHandicapCode;
	}

	public WorkerHandicapType getWorkerHandicapType() {
		return workerHandicapType;
	}

	public void setWorkerHandicapType(WorkerHandicapType workerHandicapType) {
		this.workerHandicapType = workerHandicapType;
	}

	public WorkerHandicapLevel getWorkerHandicapLevel() {
		return workerHandicapLevel;
	}

	public void setWorkerHandicapLevel(WorkerHandicapLevel workerHandicapLevel) {
		this.workerHandicapLevel = workerHandicapLevel;
	}

	public Boolean getIsCollege() {
		return isCollege;
	}

	public void setIsCollege(Boolean isCollege) {
		this.isCollege = isCollege;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsRetired() {
		return isRetired;
	}

	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}

	public Boolean getIsCadre() {
		return isCadre;
	}

	public void setIsCadre(Boolean isCadre) {
		this.isCadre = isCadre;
	}

	public String getPicTitle() {
		return picTitle;
	}

	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}