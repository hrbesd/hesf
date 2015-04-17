package com.esd.cs.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.hesf.model.User;
import com.esd.hesf.service.LegalHolidayService;

/**
 * 法定假日 处理类
 * @author yufu
 * @email ilxly01@126.com
 * 2015-4-10
 */
@Service
public class LegalHolidayUtil {

	@Autowired
	private LegalHolidayService legalHolidayService;
	
	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		System.out.println(sdf.format(date));
	}
}
