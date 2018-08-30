package com.lwxf.newstore.webapp.common.enums;

public enum SearchCategory {
	/**
	 * 全部
	 */
	ALL(0),
	/**
	 * 任务
	 */
	TASK(1),
	/**
	 * 文档
	 */
	DOC(2),
	/**
	 * 任务附件
	 */
	TASK_ATTACHMENT(3),
	/**
	 * 任务检查项
	 */
	TASK_CHECKLIST(4);
	private int value;

	SearchCategory(int value) {
		this.value = value;
	}

	public static boolean contains(int value) {
		return value >= 0 && value <= 4;
	}

	public int getValue() {
		return value;
	}
}