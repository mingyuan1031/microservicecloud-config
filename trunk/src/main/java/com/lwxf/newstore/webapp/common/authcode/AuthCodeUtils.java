package com.lwxf.newstore.webapp.common.authcode;

import java.util.Random;

import org.springframework.util.Assert;

/**
 * 功能：验证码工具类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:24:52
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class AuthCodeUtils {
	private static int defaultLen = 4;
	private static Random random = new Random();
	private AuthCodeUtils(){
		Assert.isTrue(false,"静态工具类不允许实例化");
	}

	/**
	 * 字母及数字混合种字
	 */
	private static char[] randomCodeSet = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
		'X', 'Y', 'Z',
		'1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	/**
	 * 纯数字种子
	 */
	private static char[] randomNumberCodeSet = {
			'0','1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	public static char getRandomChar(){
		return randomCodeSet[random.nextInt(randomCodeSet.length)];
	}

	public static String getRandomCode(){
		return getRandomCode(defaultLen);
	}

	/**
	 * 字母数字混合验证码
	 * @param len
	 * @return
	 */
	public static String getRandomCode(int len){
		if(len<=0){
			len = defaultLen;
		}
		int charslen = randomCodeSet.length;
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<len; i++){
			sb.append(randomCodeSet[random.nextInt(charslen)]);
		}
		return sb.toString();
	}

	/**
	 * 纯数字验证码支持
	 * @param len
	 * @return
	 */
	public static String getRandomNumberCode(int len){
		if(len<=0){
			len = defaultLen;
		}
		int charslen = randomNumberCodeSet.length;
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<len; i++){
			sb.append(randomNumberCodeSet[random.nextInt(charslen)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getRandomNumberCode(6));
		System.out.println(getRandomNumberCode(6));
		System.out.println(getRandomNumberCode(6));
		System.out.println(getRandomNumberCode(6));
	}
}