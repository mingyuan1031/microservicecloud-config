package com.lwxf.newstore.webapp.common.enums.reservation;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/4 18:38
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum ReservationStatus {
	UNTREATED((byte)0,"未处理"),
	NEGOTIATE((byte)1,"商谈中"),
	ORDERED((byte)2,"已下单"),
	CANCELED((byte)3,"已取消");

	private byte value;
	private String name;

	ReservationStatus(byte value,String name) {
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

	public static ReservationStatus getByValue(Byte value){
		ReservationStatus allVaues[] = ReservationStatus.values();
		ReservationStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}
}
