package com.esd.cs.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CommonUtil {
	public static String formatData() {
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); // 格式化当前系统日期
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
	
	/**
	 * 检测文件夹是否存在，不存在则创建
	 * @param Path
	 * @param onePath
	 * @param towPath
	 */
	static public void chineseFolder(String Path,String onePath,String towPath){
		File oneFolder=new File(Path+onePath);
		File towFolder=new File(oneFolder+File.separator+towPath);
		if(!(oneFolder.exists())){
			oneFolder.mkdir();
		}
		if(!(towFolder.exists())){
			towFolder.mkdir();
		}
		
	}
}
