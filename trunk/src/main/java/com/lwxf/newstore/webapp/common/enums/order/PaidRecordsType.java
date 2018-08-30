package com.lwxf.newstore.webapp.common.enums.order;

/**
 * 功能：支付记录类型定义
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-06 16:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  PaidRecordsType {
	ORDER_PAID((byte) 0),//订单支付
	ORDER_REFUND((byte) 1);//订单退款
	private byte value;
	PaidRecordsType(byte value){
		this.value = value;
	}

	public byte getValue(){
		return this.value;
	}

	public static boolean validValue(byte value){
		PaidRecordsType[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
