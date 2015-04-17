package com.esd.cs.settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.cs.Constants;
import com.esd.hesf.model.LegalHoliday;
import com.esd.hesf.model.PermissionType;
import com.esd.hesf.service.LegalHolidayService;
import com.esd.hesf.service.PermissionTypeService;

/**
 * 节假日和各种用户登陆时间设置 controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/security/settings/holidayset")
public class HolidaySetController {
	private static final Logger logger = LoggerFactory.getLogger(HolidaySetController.class);

	@Autowired
	private LegalHolidayService legalHolidayService;
	
	@Autowired
	private PermissionTypeService permissionTypeService;

	/**
	 * get方式跳转到节假日和各种用户登陆时间列表页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("settings/holiday_set");
	}
	
	/**
	 * post 请求 得到节假日 列表
	 * @return
	 */
	@RequestMapping(value = "/legalholidaylist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> legalholidaylist(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		List<LegalHoliday> legalHolidayList = legalHolidayService.getAll();	//得到所有节假日列表
		entity.put("legalHolidayList", legalHolidayList);	
		return entity;
	}

	
	/**
	 * post 新增 节假日 对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "/savelegalholiday", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savelegalholiday(LegalHoliday legalHoliday,HttpServletRequest request,HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		if(legalHoliday==null){
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
			return entity;
		}
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);	//当前操作人
		legalHoliday.setUserId(userId);
		Boolean bl = legalHolidayService.save(legalHoliday);
		if(bl){
			entity.put(Constants.NOTICE, Constants.NOTICE_SUCCESS);
		}else{
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
		}
		return entity;
	}
	
	/**
	 * post 查证 节假日  是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkholidays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkholidays(HttpServletRequest request,HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		String legaldate = request.getParameter("legaldate");
		//先查看有没有相同的, 没有的话再添加
		LegalHoliday temp = legalHolidayService.getByLegaldate(legaldate);
		if(temp!=null){
			entity.put(Constants.NOTICE, Constants.NOTICE_SUCCESS);
		}else{
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
		}
		return entity;
	}
	
	/**
	 * post 删除 节假日 对象
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deletelegalholiday/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletelegalholiday(@PathVariable(value="id") String id,HttpServletRequest request,HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		Boolean bl = legalHolidayService.delete(id);
		if(bl){
			entity.put(Constants.NOTICE, Constants.NOTICE_SUCCESS);
		}else{
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
		}
		return entity;
	}

	
	/**
	 * post 请求 权限类别 列表
	 * @return
	 */
	@RequestMapping(value = "/permissiontypelist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> permissiontypelist(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		List<PermissionType> permissionTypeList = permissionTypeService.getNeedSetTime();	// 得到需要设置操作时间权限种类
		entity.put("permissionTypeList", permissionTypeList);	
		return entity;
	}
	
	
	
	/**
	 * post 更新不同权限类别 审核有效时间
	 * 
	 * @param reply
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updatepermissiontype", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatepermissiontype(PermissionType permissionType, HttpServletRequest req,HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		if(permissionType==null){
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
			return entity;
		}
		logger.debug(permissionType.toString());
		Boolean bl = permissionTypeService.update(permissionType);
		if(bl){
			entity.put(Constants.NOTICE, Constants.NOTICE_SUCCESS);
		}else{
			entity.put(Constants.NOTICE, Constants.NOTICE_FAILURE);
		}
		return entity;
	}

}
