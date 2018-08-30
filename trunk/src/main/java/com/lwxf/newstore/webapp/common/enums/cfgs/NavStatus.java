package com.lwxf.newstore.webapp.common.enums.cfgs;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/23 14:25
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum NavStatus {

	SHOW((byte) 0,"显示"),
	HIDE((byte) 1,"隐藏");

	private byte value;
	private String name;

	NavStatus(byte value,String name) {
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

	public static NavStatus getByValue(Byte value){
		NavStatus allVaues[] = NavStatus.values();
		NavStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}

}
