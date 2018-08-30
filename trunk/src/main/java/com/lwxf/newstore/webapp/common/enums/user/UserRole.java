package com.lwxf.newstore.webapp.common.enums.user;

/**
 * 功能：用户角色
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-08 11:57
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum UserRole {
	MEMBER(0,"会员"),
	CLERK(1,"店员"),
	MANAGER(2,"店长"),
	SHOPKEEPER(3,"店主");

	private int value;
	private String name;
	UserRole(int value,String name){
		this.value = value;
		this.name = name;
	}

	public int getValue(){
		return this.value;
	}

	public String getName(){
		return this.name;
	}

	public static boolean validValue(int value){
		UserRole[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
