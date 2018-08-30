package com.lwxf.newstore.webapp.common.enums.order;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/5 15:19
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum GoodsOrderStatus {
	NOTSHIPPED(0),//未发货
	SHIPPED(1),//已发货
	RECEIVED(2),//已收货
	COMMENTED(3),//已评论
	ADDEDCOMMENT(4);//已加评

	private int value;
	GoodsOrderStatus(int value) {
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	public static boolean validValue(int value){
		GoodsOrderStatus [] arr = GoodsOrderStatus.values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].value == value){
				return true;
			}
		}
		return false;
	}
}
