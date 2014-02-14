package com.esd.cs.common;

import java.text.SimpleDateFormat;

public class CommonUtil {
	public static String formatData() {
		SimpleDateFormat dateFm = new SimpleDateFormat("yy-MM-dd_HH:mm:ss"); // 格式化当前系统日期
		return dateFm.format(new java.util.Date());
	}

	/**
	 * 中文字符检验
	 * 
	 * @param s
	 *            String
	 * @return 包含中文字符返回true,否则返回false
	 */
	static public boolean chineseValid(String s) {
		int length = s.length();
		byte[] b;
		for (int i = 0; i < length; i++) {
			b = s.substring(i).getBytes();
			if ((b[0] & 0xff) > 128)
				return true;
		}
		return false;
	}
}
