package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：用户个人消息分组枚举
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:46:01
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum UserNotifyGroup {
	TASK_ASSIGNEE((byte) 0),//任务负责人修改 通知：任务成员
	TASK_COMMENT((byte) 1),//任务评论和@ 通知：任务成员
	TASK_MD((byte) 2),//任务结束时间、完成状态修改 通知：任务成员
	TASK_DELETED((byte) 3),//任务伪删除、恢复 通知：任务成员
	DOC_COMMENT((byte) 4),//文档的评论 根据@情况发送通知
	DOC_MD((byte) 5),//文档修改
	DOC_DELETED((byte) 6),//文档删除 通知负责人，企业文档通知管理员，讨论组通知所有成员（不包含访客）
	PROJECT_ADMIN((byte) 7),//
	TASK_CREATE((byte) 8),//创建任务 通知：通知任务成员
	ORG_USER_JOINED_FROM_EMAIL((byte) 9),
	ORG_USER_JOINED_FROM_LINK((byte) 10),
	ORG_USER_TASK_DEFER((byte) 11),//任务延误提醒 通知有延期任务的用户
	;

	private byte value;

	UserNotifyGroup(byte value) {
		this.value = value;
	}

	/**
	 * 数据范围校验
	 *
	 * @param value
	 * @return
	 */
	public static boolean contains(Byte value) {
		if (value == null) {
			return false;
		}
		if (value >= 0 && (value <= 8)) {
			return true;
		}
		return false;
	}

	public byte getValue() {
		return value;
	}
}
