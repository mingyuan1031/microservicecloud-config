package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *		MQ事件
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:13:36
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum  MQEventEnum {
	//组织级
	ORG("org"),
	ORG_INVITE("org:invite"),
	ORG_BATCHJOIN("org:batchjoin"),
	ORG_MEMBER("org:member"),
	ORG_DELETE("org:delete"),
	DEPT("dept"),
	DEPT_MEMBER("dept:member"),
	PROJECT("project"),
	TASK_TYPE("tasktype"),
	TASK_FIELD("taskfield"),
	TASKTYPE_TASKFIELD("tasktype:taskfield"),
	ORG_LINK_POOL("orglinkpool"),
	TASK_TAG_POOL("tasktagpool"),
	//项目级的
	PROJECT_MEMBER("project:member"),
	PROJECT_STATS("project:stats"),
	KANBAN("kanban"),
	KANBAN_MOVE("kanban:move"),
	KANBAN_STATS("kanban:stats"),
	KANBAN_KBC("kanban:kbc"),
	KANBAN_KBC_COPY("kanban:kbc:copy"),
	KANBAN_KBC_MOVE("kanban:kbc:move"),
	KANBAN_KBC_STATS("kanban:kbc:stats"),
	TASK("task"),
	TASK_MOVE("task:move"),//任务拖拽
	TASK_REORDER("task:reorder"),//看板列任务重新排序
	TASK_ACTIVITY("task:activity"),//任务活动日志
	TASK_COMMENT("task:comment"),
	TASK_ATTACHMENT("task:attachment"),
	TASK_CHECKLIST("task:checklist"),
	TASK_LINK("task:link"),
	TASK_STATS("task:stats"),
	FOLDER("folder"),
	FOLDER_MEMBER("folder:member"),
	//文档
	DOC("doc"),
	DOC_ARTICLE("doc:article"),
	DOC_PRAISER("doc:praiser"),
	DOC_READER("doc:reader"),
	DOC_STATS("doc:stats"),
	DOC_COMMENT("doc:comment"),
	DOC_VERSION("doc:version"),
	DOC_FILE_VERSION_RECOVER("doc:file:version:recover"),
	DOC_ARTICLE_VERSION_RECOVER("doc:article:version:recover"),
	//用户个人消息
	NOTIFY_PERSONAL("notify:personal"),
	//用户系统消息
	NOTIFY_SYS("notify:sys"),

	//客服消息
	FEEDBACK_COMMENT("feedback:comment"),
	FEEDBACK_READ("feedback:read"),
	FEEDBACK_USER("feedback:user"),

	//用户同步数据:个人收藏，收藏分组，过滤器
	USER_FAVORITE("user:favorite"),
	USER_FAVORITEGROUP("user:favoritegroup"),
	USER_FILTER("user:filter"),

	// 微信消息事件
	USER_WX_BOUND_OTHER_ERROR("error:user:wx:bound_other"), // 微信账号已被绑定
	// 微信账号已被绑定
	USER_WX_BOUND_ERROR("error:user:wx:bound"),
	// 微信账号已经被解绑
	USER_WX_UNBIND_ERROR("error:user:wx:unbind"),
	// 绑定微信号
	USER_WX_BINGING("user:wx:binging"),
	// 取消微信号订阅
	USER_WX_UNSUBSCRIBE("user:wx:unsubscribe"),
	// 同步消息
	USER_THIRDINFO("user:thirdinfo"),
	//个人中心配置
	NOTIFY_MSGCFG("notify:msgcfg"),
	//用户在线状态推送
	USER_ONLINE("user:online"),
	//用户基本信息
	USER("user");

	private String value;

	MQEventEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
