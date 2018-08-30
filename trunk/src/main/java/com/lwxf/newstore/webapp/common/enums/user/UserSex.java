package com.lwxf.newstore.webapp.common.enums.user;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-08 11:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  UserSex {
	MAN(0), 		// 男
	WOMEN(1);	// 女

	private int value;
	UserSex(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public static boolean validValue(int value){
		UserSex[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
