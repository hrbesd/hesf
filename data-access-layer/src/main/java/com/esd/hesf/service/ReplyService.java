package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.Reply;

/**
 * 复审意见 service接口
 * @author Administrator
 *
 */
public interface ReplyService extends BaseService<Reply> {

	/**
	 * 获得所有数据
	 * @return
	 */
	List<Reply> getAll();
}
