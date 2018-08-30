package com.lwxf.newstore.webapp.common.enums.cfgs;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/28 15:00
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum CfgsType {
	LOGO(0,"logo"),
	HEADER(1,"header"),
	POSTER(2,"poster"),
	QRCODE(3,"qrcode"),
	STORECARD(4,"storecard");

	private Integer value;
	private String name;
	CfgsType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue(){
		return this.value;
	}

	public String getName(){
		return this.name;
	}

	public static String getDesc(Integer value) {
		CfgsType[] businessModeEnums = values();
		for (CfgsType businessModeEnum : businessModeEnums) {
			if (businessModeEnum.getValue().equals(value)) {
				return businessModeEnum.getName();
			}
		}
		return null;
	}
}
