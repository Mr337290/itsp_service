package com.itsp.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

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

}
