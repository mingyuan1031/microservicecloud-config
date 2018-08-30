package com.lwxf.newstore.webapp.common.enums.order;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/7/27/027 15:42
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum OrderGoodsStatus {
	NOTSHIPPED((byte)0,"未发货"),//未发货
	SHIPPED((byte)1,"已发货"),//已发货
	RECEIVED((byte)2,"已收货"),//已收货
	COMMENTED((byte)3,"已评论"),//已评论
	ADDEDCOMMENT((byte)4,"已加评");//已加评

	private byte value;
	private String name;


	OrderGoodsStatus(byte value, String name) {
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

	public static OrderGoodsStatus getByValue(Byte value){
		OrderGoodsStatus allVaues[] = OrderGoodsStatus.values();
		OrderGoodsStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}
}
