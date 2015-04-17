package com.esd.cs.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esd.cs.Constants;
import com.esd.hesf.service.LegalHolidayService;
import com.esd.hesf.service.UserService;

/**
 * 里面的对象不能实例化, 暂时不用, 待找到bug后再启用.
 * @author yufu
 * @email ilxly01@126.com
 * 2015-4-15
 */
public class HolidayFilter extends OncePerRequestFilter {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LegalHolidayService legalHolidayService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("---------------------------自定义的filter啊----------------------------");
		if (request.getRequestURI().indexOf("/security") != -1) {
			Object obj = request.getSession().getAttribute(Constants.USER_ID);
			PrintWriter writer = null;
			writer = response.getWriter();
			//登陆用户id为空则跳转到登陆页面
			if (obj == null) {
				try {
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("window.top.location.href='");
					builder.append("/cs/login");
					builder.append("';");
					builder.append("</script>");
					writer.print(builder.toString());
					writer.close();
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			}else{
				//用户id不为空时则进行法定假日, 及各用户组用户登陆时间限定判断
				if(!legalHolidayService.checkIsRestDay(userService.getByPrimaryKey(Integer.parseInt(obj.toString())))){
					writer.write("乌拉拉");
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
