package com.esd.hesf.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 所有类都具有的属性
 * 
 * @author Administrator
 * 
 */
public class CommonKey {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime; // 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date updateTime; // 更新时间
	private Integer userId; // 操作人id
	private Boolean isActive; // 逻辑删除标识 是否逻辑删除: false--no(即未被删除),
								// true--yes(即被删除)
	private Integer version; // 版本号标示符
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
