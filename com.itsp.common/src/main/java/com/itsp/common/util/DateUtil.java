package com.itsp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String string(Date date) {
		if (date == null || "".equals(date))
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
		return sdf.format(date);
	}

	public static String string(Date date, String format) {
		if (date == null || "".equals(date))
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static Date autoFormat(String s){
		Date date = null;
		if(s!=null && !"".equals(s)){
			String t = s.replaceAll("[^0-9]", "");
			while(t.length() < YYYYMMDDHHMMSS.length()){
				t = t + "0";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
			try {
				date = sdf.parse(t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
}
