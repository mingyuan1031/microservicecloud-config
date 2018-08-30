package com.lwxf.newstore.webapp.common.enums.order;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/6 14:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum PaidMethod {
	WeiXin((int)0);
	private int value;

	PaidMethod(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	public static boolean validValue(int value){
		PaidMethod [] arr = PaidMethod.values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
