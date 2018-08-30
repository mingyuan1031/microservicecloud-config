package com.lwxf.newstore.webapp.common.enums.goods;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/17/017 18:06
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  GoodsStatus {
	DISABLED((byte)1,"该商品下架"),
	ENABLE((byte)0,"该商品上架");

	private byte value;
	private String name;


	GoodsStatus(byte value, String name) {
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

	public static GoodsStatus getByValue(Byte value){
		GoodsStatus allVaues[] = GoodsStatus.values();
		GoodsStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}
}
