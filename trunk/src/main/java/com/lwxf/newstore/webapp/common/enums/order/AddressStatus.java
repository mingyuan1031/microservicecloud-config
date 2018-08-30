package com.lwxf.newstore.webapp.common.enums.order;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/8/10 11:50
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  AddressStatus {
	DELETE(-1),
	NORMAL(0),
	DISABLE(1);
	private int value;
	AddressStatus(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static boolean validValue(int value){
		AddressStatus [] arr = AddressStatus.values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}

}
