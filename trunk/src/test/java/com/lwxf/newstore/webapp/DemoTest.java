package com.lwxf.newstore.webapp;

import org.junit.Test;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/8/8 15:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class DemoTest {

	@Test
	public void StringIndexofTest()
	{
		String qq="234567487";
		 String regex="[1-9][0-9]{4,14}";//正则表达式
		boolean matches = qq.matches(regex);
		System.out.println(matches);
	}
}
