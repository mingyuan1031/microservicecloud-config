package com.lwxf.newstore.webapp.common.enums.order;

import com.lwxf.newstore.webapp.common.enums.quickshare.MicroblogStatus;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/27 11:07
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  OrderStatusValue {
	UNPAID((byte) 0,"0未付款"),//0未付款
	ALREADYPAID((byte) 1,"已付款"),//已付款
	PARTIALDELIVER((byte) 2,"部分发货"),//部分发货
	SHIPPED((byte) 3,"已发货"),//已发货
	RECEIVED((byte) 4,"已收货"),//已收货
	REVOKED((byte) 5,"已撤销"), //已撤销
	REFUNDED((byte) 6,"已退款");//已退款

	private byte value;
	private String name;

	OrderStatusValue(Byte value,String name){
		this.value = value;
		this.name = name;
	}

	/**
	 * 数据范围校验
	 *
	 * @param value
	 * @return
	 */
	public static boolean contains(Byte value) {
		return null == getByValue(value)?Boolean.FALSE.booleanValue():Boolean.TRUE.booleanValue();
	}

	public Byte getValue() {
		return this.value;
	}

	public String getName(){
		return this.name;
	}

	public static OrderStatusValue getByValue(Byte value) {
		OrderStatusValue allVaues[] = OrderStatusValue.values();
		OrderStatusValue status;
		for (int i = 0, len = allVaues.length; i < len; i++) {
			status = allVaues[i];
			if (status.getValue() == value.byteValue()) {
				return status;
			}
		}
		return null;
	}
}
