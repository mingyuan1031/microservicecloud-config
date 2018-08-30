package com.lwxf.newstore.webapp.common.enums.order;





/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/5 14:26
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum OrderStatus {
	UNPAID(0),//0未付款
	ALREADYPAID(1),//已付款
	PARTIALDELIVER(2),//部分发货
	SHIPPED(3),//已发货
	RECEIVED(4),//已收货
	REVOKED(5), //已撤销
	REFUNDED(6);//已退款
	private int value;

	OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	public static boolean validValue(int value){
		OrderStatus [] arr = OrderStatus.values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
