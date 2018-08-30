package com.lwxf.newstore.webapp.common.wxpayutil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 生成当前日期时间串
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sdf.format(date);
	}
	public  static  String getTimeStamp()
	{
		long time=System.currentTimeMillis();

		String s = String.valueOf(time / 1000).substring(0,10);
		return  s;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getCurrentDateStr());
	}

	
}
