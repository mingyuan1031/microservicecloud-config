package com.lwxf.newstore.webapp.common.enums.payment;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:07:23
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum CouponCodeState {
	NORMAL(0),
	USED(1),
	OBSOLETE(2),
	EXPIRED(3);
	private int value;
	CouponCodeState(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public static boolean validValue(int value){
		CouponCodeState[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
