package com.lwxf.newstore.webapp.common.enums.goods;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/23/023 10:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum GoodsCommentStatus {
	DISABLED((byte)1,"隐藏该评论"),
	ENABLE((byte)0,"显示该评论");

	private byte value;
	private String name;


	GoodsCommentStatus(byte value, String name) {
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

	public static GoodsCommentStatus getByValue(Byte value){
		GoodsCommentStatus allVaues[] = GoodsCommentStatus.values();
		GoodsCommentStatus status;
		for (int i=0,len = allVaues.length;i<len;i++){
			status = allVaues[i];
			if(status.getValue()==value.byteValue()){
				return status;
			}
		}
		return null;
	}
}
