package com.lwxf.newstore.webapp.common.constant;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:53:57
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class BizConstant {
	/**
	 * 用户创建组织个数限制常量
	 */
	public static final int BIZ_SYS_CREATE_ORG_LIMIT =2;

	/**
	 * 容量单位M转换为byte单位换算常量:1024*1024
	 */
	public static final int BIZ_SYS_UNIT_M_TO_BYTE =1048576;
	/**
	 * 项目常量
	 */
	public static final String PRIVATE_PROJECT_NAME = "private_project";
	public static final Integer PRIVATE_PROJECT_NO = 0;
	/**
	 * 目录
	 **/
	public static final String DEFAULT_FOLDER_NAME_DATA = "BIZ_SYS_DEFAULT_NAME_FOLDER_DATA";

	public static final String DEFAULT_FOLDER_NAME_SYS = "BIZ_SYS_DEFAULT_NAME_FOLDER_SYSTEM";

	/**
	 * 看板
	 */
	public static final String BIZ_SYS_DEFAULT_NAME_PERSONAL_KANBAN = "BIZ_SYS_DEFAULT_NAME_PROJECT_KANBAN";

	public static final String BIZ_SYS_DEFAULT_NAME_PROJECT_KANBAN = "BIZ_SYS_DEFAULT_NAME_PROJECT_KANBAN";
	/**
	 * 新easypm4七牛地址:目前删除头相判断使用
	 */
	public static final String EASPYM4_FILE_DOMAIN = "https://cdn.easypm.cn";

	/**
	 * 0-邮箱,1-手机,2-微信,3-钉钉,4-github,5-qq,6-weibo
	 EMAIL(0), MOBILE(1), WEIXIN(2), DING(3), GITHUB(4), QQ(5),WEIBO(6)
	 */
	public static final String REGISTER_TYPE_EMAIL ="email";
	public static final String REGISTER_TYPE_MOBILE ="mobile";

	/**
	 * 对免费用户限制常量
	 */
	public static final int ORG_LICENCE_FREE_USER_LIMIT = 10;
	public static final int ORG_LICENCE_FREE_ENDPOINT_LIMIT = 50;
	public static final int ORG_LICENCE_FREE_FILE_LIMIT = 5;
	public static final int ORG_LICENCE_FREE_FILESPACE_LIMIT = 5;
	public static final int ORG_LICENCE_FREE_NOTIFY_SAVE_LIMIT = 1;
	/**
	 * 对付费用户限制常量
	 */
	public static final int ORG_LICENCE_PAID_USER_LIMIT = 10;
	public static final int ORG_LICENCE_PAID_ENDPOINT_LIMIT = -1;
	public static final int ORG_LICENCE_PAID_FILE_LIMIT = 100;
	public static final int ORG_LICENCE_PAID_FILESPACE_LIMIT = 100;
	public static final int ORG_LICENCE_PAID_NOTIFY_SAVE_LIMIT = -1;



}
