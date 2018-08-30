package com.lwxf.newstore.webapp.common.enums.user;

/**
 * 功能：用户状态枚举
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:46:01
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum UserState {
	DISABLED((byte) 0),// 0 禁用
	ENABLED((byte) 1);//1 启用

	private byte value;

	UserState(byte value) {
		this.value = value;
	}

	/**
	 * 数据范围校验
	 *
	 * @param value
	 * @return
	 */
	public static boolean contains(Byte value) {
		return value != null && value >= 0 && (value <= 1);
	}

	public Byte getValue() {
		return value;
	}
}
