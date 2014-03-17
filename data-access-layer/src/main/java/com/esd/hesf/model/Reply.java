package com.esd.hesf.model;

/**
 * 复审意见表
 * 
 * @author Administrator
 * 
 */
public class Reply extends PrimaryKey_Int {

	private String title; // 显示的标题
	private String content; // 详细内容

	@Override
	public String toString() {
		return "Reply [title=" + title + ", content=" + content + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
