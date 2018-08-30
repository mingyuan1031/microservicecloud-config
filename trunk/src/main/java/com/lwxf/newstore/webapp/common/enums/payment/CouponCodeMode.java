package com.lwxf.newstore.webapp.common.enums.payment;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:04:41
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum CouponCodeMode {
	DISCOUNT(0), 		// 折扣
	CASH(1),	 		// 直减现金
	REDEEM(2),			// 积分兑换
	REACH_REDUCE(3);	// 满多少减多少

	private int value;
	CouponCodeMode(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}

	public static boolean validValue(int value){
		CouponCodeMode[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
