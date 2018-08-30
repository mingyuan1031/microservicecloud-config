package com.lwxf.newstore.webapp.common.constant;

import com.lwxf.commons.constant.CommonConstant;

/**
 * 功能：与web相关的常量定义
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:41:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class WebConstant extends CommonConstant {
	/**
	 * 存储在当前线程中的缓存数据的key定义（存在WebUtil中的request map中）
	 */
	public static final String REQUEST_MAP_KEY_API_MEMBER_INSTANCE = "api_member_instance";
	public static final String REQUEST_MAP_KEY_API_RAP_INSTANCE = "api_rap_instance";

	public static final String REQUEST_METHOD_GET = "get";
	public static final String REQUEST_ACTION_CREATE = "create";
	public static final String REQUEST_ACTION_READ = "read";
	public static final String REQUEST_ACTION_UPDATE = "update";
	public static final String REQUEST_ACTION_DELETE = "delete";

	public static final String TIMEZONE_DEFAULT = "GMT+:08:00";
	public static final String LANGUAGE_DEFAULT = "zh-CN";
	public static final String REQUEST_RESULT_AJAX_EMPTY_OBJ = "{}";
	public static final String REDIRECT_PATH_TEMPLATE = "redirect:{0}";
	public static final String REDIRECT_PATH_ERROR = "/error";
	public static final String REDIRECT_PATH_404 = "/404";
	public static final String REDIRECT_PATH_MALL = "/mall";
	public static final String REDIRECT_PATH_ADMIN = "/admin";
	public static final String REDIRECT_PATH_LOGIN = "/login";
	/**
	 * 验证码key register TODO:AuthCodeServlet重构生成逻辑
	 */
	public static final String SESSION_KEY_AUTHCODE_REGISTER ="authCode";

	public static final String SESSION_KEY_CURR_USER="s_curr_user";
	/**
	 * 在TsManager中用于手动处理业务的标记
	 */
	public static final String TSMANAGER_MANUAL_ACTION_FLAG="tsmanager_manual_action";

	/**
	 *  原easypm 微信公众平台配置路径
	 */
	public static final String WEIXIN_PATH_V1 = "https://free.easypm.cn/api/weixin";
	//埋数据标志点：为了新增组织时候以下mq组装消息忽略:组织增加成员、创建私人项目、私人看板、任务类型、企业文档目录
	public static final String MQ_ORG_ADD_IGNORE = "mq_org_add_ignore";

	/**
	 * mq token前缀
	 */
	public static final String MQ_TOKEN_PREFIX = "MQ:";

	/**
	 * key 定义
	 */
	// common key 定义
	public static final String KEY_COMMON_ADD="add";
	public static final String KEY_COMMON_EMAIL ="email";
	public static final String KEY_COMMON_LINK ="link";
	public static final String KEY_COMMON_CURR_PROJECT_ID ="currProjectId";
	public static final String KEY_COMMON_ORG_USER_ID ="orgUserId";

	public static final String KEY_ENTITY_ID = "id";
	public static final String KEY_ENTITY_STATE="state";
	public static final String KEY_ENTITY_ROLE="role";
	public static final String KEY_ENTITY_VISIBILITY="visibility";
	public static final String KEY_ENTITY_NAME="name";
	public static final String KEY_ENTITY_COLOR="color";
	public static final String KEY_ENTITY_DISABLED="disabled";
	/**
	 * 预约单key定义
	 */
	public static final String KEY_ENTITY_PHONE="phone";
	public static final String KEY_ENTITY_STARTTIME="startTime";
	public static final String KEY_ENTITY_ENDTIME="endTime";

	//goods
	public static final String KEY_ENTITY_GOODS = "goods";
	/**
	 * 快享key定义
	 */
	public static final String KEY_ENTITY_CONTENT="content";
	public static final String KEY_ENTITY_CREATOR="creator";

	//order
	public static final String KEY_ENTITY_MEMBER_ID = "memberId";
	public static final String KEY_ENTITY_STATUS = "status";
	public static final String KEY_ENTITY_DEFAULTED = "defaulted";
	public static final String KEY_ENTITY_PARENTID = "parentId";
	public static final String KEY_ENTITY_LEVELTYPE = "levelType";
	public static final String KEY_ENTITY_KEYWORDS = "keywords";
	public static final String KEY_ENTITY_UPDATED="updated";
	public static final String KEY_ENTITY_RECEIPTTIME="receiptTime";
	public static final String KEY_ENTITY_DESCR="descr";
	public static final String KEY_ENTITY_PAIDTIME="paidTime";
    public static final String KEY_ENTITY_WXMENUS="wxmenus";
	public static final String KEY_ENTITY_TRADENO="tradeNo";

	public static final String KEY_RESET_PASSWORD_USER_ID = "RESET_PASSWORD_USER_ID";
	/**
	 * 获取日志相关常量定义
	 */
	public static final String KEY_ACTIVITY_REF_ID = "refId";
	public static final String KEY_ACTIVITY_GROUPING = "grouping";
	public static final String KEY_ACTIVITY_STATUS_NAME = "statusName";

	public static final String KEY_KANBAN_KBC_TASKS_REORDER = "kbcTasksReorder";
	public static final String KEY_BE_DELETE_ENTITY_ORGUSER = "beDeleteEntityOrgUser";

	public static final String KEY_USER_NOTIFY_TOP_TIME= "topTime";
	public static final String KEY_USER_NOTIFY_READED= "readed";

	public static final String KEY_PRELOAD_PERMISSIONS = "permissions";
	public static final String KEY_PRELOAD_USER = "user";
	public static final String KEY_PRELOAD_COMPANY = "company";
	public static final String KEY_PRELOAD_SYSCFG = "syscfg";
	public static final String KEY_PRELOAD_STORECFG = "storecfg";
	/**
	 * 微信支付
	 */
	public static final String KEY_WxPay_APPID = "appid";
	public static final String KEY_WxPay_MCH_ID = "mch_id";
	public static final String KEY_WxPay_DEVICE_INFO = "device_info";
	public static final String KEY_WxPay_NONCE_STR = "nonce_str";
	public static final String KEY_WxPay_SIGN = "sign";
	public static final String KEY_WxPay_BODY = "body";
	public static final String KEY_WxPay_OUT_TRADE_NO = "out_trade_no";
	public static final String KEY_WxPay_TOTAL_FEE = "total_fee";
	public static final String KEY_WxPay_SPBILL_CREATE_IP = "spbill_create_ip";
	public static final String KEY_WxPay_NOTIFY_URL = "notify_url";
	public static final String KEY_WxPay_TRADE_TYPE = "trade_type";
	public static final String KEY_WxPay_TRADE_OPENID = "openid";
	public static final String KEY_WxPay_OPENID = "openid";
	public static final String KEY_WxPay_KEY = "key";

	/**
	 * user
	 */
	public static final String KEY_USER_EXTRA_SALT="salt";
	public static final String KEY_USER_EXTRA_PASSWORD="password";
	public static final String KEY_USER_EXTRA_TOKEN="token";

	/**
	 *  设计案例
	 */
	public static final String KEY_SCHEME_SCHEMEID = "schemeId";
	public static final String KEY_SCHEME_PAGEVIEW = "pageView";

	/**
	 * 企业动态
	 */
	public static final String KEY_NEWS_HTML=".html";
	/**
	 * Shiro用key定义
	 */
	public static final String KEY_SHIRO_ORG_DISABLE_USER_PROJECTNEWASSIGNS = "org_disable_user_projectnewassigns";

	/**
	 * EasyPM新版的微信授权回调路径
	 */
	public static final String WEIXIN_OAUTH_REDIRECT_URL = "/wx/auth?wxOpenId={0}";

	/**
	 * 正则的主键匹配
	 */
	public static final String REG_ID_MATCH = "[a-zA-Z0-9]{10,13}";

	public static final String IP_DEFAULT = "0.0.0.0";
	public static final String USER_DEFAULT_PASSWORD = "lwxf@26370";

	public static final String WX_TEMPLATE_MSG_USER_LEAVE_DATA = "wx_template_msg_user_leave_data";

	public static final String WX_TEMPLATE_MSG_APPLY_SUCCESS = "wx_template_msg_apply_success";

	private WebConstant(){

	}
}
