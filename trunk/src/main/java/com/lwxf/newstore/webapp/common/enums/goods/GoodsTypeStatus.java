package com.lwxf.newstore.webapp.common.enums.goods;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/20/020 16:20
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum GoodsTypeStatus {
	DISABLED((byte)1,"禁用该分类"),
	ENABLE((byte)0,"启用该分类");

	private byte value;
	private String name;


	GoodsTypeStatus(byte value, String name) {
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

	public static GoodsTypeStatus getByValue(Byte value){
		GoodsTypeStatus allVaues[] = GoodsTypeStatus.values();
		GoodsTypeStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}
}
