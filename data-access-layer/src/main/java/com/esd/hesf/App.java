package com.esd.hesf;

import java.io.IOException;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("java -jar mybatis-generator-core-1.3.2.jar -configfile generator.xml -overwrite");
		System.out.println(proc);
		System.out.println("----------	success	-----------");
	}

	public void aa() {
	}

}
