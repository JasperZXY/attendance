package com.jasper.attendance;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		SimpleDateFormat WORK_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
		System.out.println(WORK_DATE_FORMAT.format(new Date()));
		int a = (int) (5.0/3);
		System.out.println(a);
	}

}
