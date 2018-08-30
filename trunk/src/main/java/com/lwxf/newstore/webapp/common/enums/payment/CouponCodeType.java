package com.lwxf.newstore.webapp.common.enums.payment;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:10:10
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum CouponCodeType {
	CODE(0), // 优惠码优惠卷
	COUPON(1); //

	private int value;
	CouponCodeType(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public static boolean validValue(int value){
		CouponCodeType[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
