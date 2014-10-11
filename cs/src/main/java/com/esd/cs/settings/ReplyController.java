package com.esd.cs.settings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.hesf.model.Reply;
import com.esd.hesf.service.ReplyService;

/**
 * 复审意见设置 controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/security/settings/reply")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService rService;

	/**
	 * get方式跳转到复审意见列表页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("settings/reply_list");
	}

	/**
	 * post方式请求复审意见数据
	 * 
	 * @param page
	 * @param rows
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> post(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows, HttpServletRequest req) {
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Reply, Number> prn = rService.getPaginationRecords(null, page, rows);
		Integer total = prn.getNumber().intValue();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Iterator<Reply> iterator = prn.getRecords().iterator(); iterator.hasNext();) {
			Reply reply = iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", reply.getId());
			map.put("title", reply.getTitle());
			map.put("createTime", getStringDate(reply.getCreateTime()));
			map.put("updateTime", getStringDate(reply.getUpdateTime()));
			list.add(map);
		}
		entity.put("total", total);
		entity.put("rows", list);
		return entity;
	}

	/**
	 * get方式跳转到 新增复审意见 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView addGet() {
		return new ModelAndView("settings/reply_save");
	}

	/**
	 * post方式 保存 复审意见
	 * 
	 * @param reply
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addPost(Reply reply, HttpServletRequest req,HttpSession session) {
		logger.debug(reply.toString());
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		reply.setUserId(userId);
		Boolean bl = rService.save(reply);
		return bl;
	}

	/**
	 * 删除复审意见
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delete(@RequestParam(value = "params[]") int[] ids) {
		logger.debug(ids.toString());
		for (int i = 0; i < ids.length; i++) {
			if (rService.delete(ids[i]) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * get方式跳转到 修改复审意见 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateGet(@RequestParam(value = "id") Integer id) {
		logger.debug("id: " + id);
		Reply reply = rService.getByPrimaryKey(id);
		return new ModelAndView("settings/reply_update", "entity", reply);
	}

	/**
	 * post方式 修改 复审意见
	 * 
	 * @param reply
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updatePost(Reply reply, HttpServletRequest req,HttpSession session) {
		logger.debug(reply.toString());
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		reply.setUserId(userId);
		Boolean bl = rService.update(reply);
		return bl;
	}

	// 将Date 转成 yyyy-MM-dd 格式的字符串
	private String getStringDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
