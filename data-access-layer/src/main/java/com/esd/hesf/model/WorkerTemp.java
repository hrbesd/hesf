package com.esd.hesf.model;

/**
 * 员工信息缓存类_验证通过的
 * 
 * @author Administrator
 * 
 */
public class WorkerTemp {

	private Integer id;
	private Integer companyId;
	private String workerName; // 员工名
	private String workerGender;// 性别
	private String workerBirth;// 生日年月
	private String workerBirthYear;// 出生年份--供后台查询使用
	private String workerIdCard;// 身份证号
	private String workerHandicapCode;// 残疾证号
	private Integer workerHandicapType;// 残疾类型
	private Integer workerHandicapLevel;// 残疾等级
	private String currentJob;// 当前职位
	private String careerCard;// 职业证号
	private String phone; // 电话
	private Boolean isOk; // 是否通过了验证, 默认为false(未通过)
	private Boolean isCadre; // 是否干部
	private String remark; // 备注
	private Integer userId; // 操作用户id
	private String checkCode; // 检查码, 防止数据冲突
	private Integer preId; // 校验时检出为已经存在的数据, 此字段保存该员工在员工表worker中的真实id
	private String picTitle; // 精神残疾的图片 图片名
	private byte[] pic; // 精神残疾的图片

	@Override
	public String toString() {
		return "WorkerTemp [id=" + id + ", companyId=" + companyId
				+ ", workerName=" + workerName + ", workerGender="
				+ workerGender + ", workerBirth=" + workerBirth
				+ ", workerBirthYear=" + workerBirthYear + ", workerIdCard="
				+ workerIdCard + ", workerHandicapCode=" + workerHandicapCode
				+ ", workerHandicapType=" + workerHandicapType
				+ ", workerHandicapLevel=" + workerHandicapLevel
				+ ", currentJob=" + currentJob + ", careerCard=" + careerCard
				+ ", phone=" + phone + ", isOk=" + isOk + ", isCadre="
				+ isCadre + ", remark=" + remark + ", userId=" + userId
				+ ", checkCode=" + checkCode + ", preId=" + preId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getWorkerHandicapCode() {
		return workerHandicapCode;
	}

	public void setWorkerHandicapCode(String workerHandicapCode) {
		this.workerHandicapCode = workerHandicapCode;
	}

	public Integer getWorkerHandicapType() {
		return workerHandicapType;
	}

	public void setWorkerHandicapType(Integer workerHandicapType) {
		this.workerHandicapType = workerHandicapType;
	}

	public Integer getWorkerHandicapLevel() {
		return workerHandicapLevel;
	}

	public void setWorkerHandicapLevel(Integer workerHandicapLevel) {
		this.workerHandicapLevel = workerHandicapLevel;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
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

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public Boolean getIsCadre() {
		return isCadre;
	}

	public void setIsCadre(Boolean isCadre) {
		this.isCadre = isCadre;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Integer getPreId() {
		return preId;
	}

	public void setPreId(Integer preId) {
		this.preId = preId;
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