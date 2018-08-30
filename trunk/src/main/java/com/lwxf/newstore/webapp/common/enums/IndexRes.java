package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:45
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum IndexRes {
	TASK("0"),
	ARTICLE("1"),
	FILE("2"),
	ATTACHMENT("3"),
	TASK_CHECKLIST("4");
	private String value;

	IndexRes(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
