package com.lwxf.newstore.webapp.common.enums.quickshare;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/4 11:24
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum MicrologType {

	COMMON(0,"普通"),
	STYLIST(1,"设计师"),
	ERECTOR(2,"安装工"),
	SHOPKEEPER(3,"店主");

	private int value;
	private String name;
	MicrologType(int value,String name){
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
		MicrologType[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}

}
