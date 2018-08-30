package com.lwxf.newstore.webapp.baseservice.cache.constant;

/**
 * 功能：Redis缓存配置静态Hash、Set...键名及前缀
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:17:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RedisConstant {
	public static final String SEPARATOR = ":";

	/**
	 * demo Hash表名
	 */
	public static final String DemoHashTableName = "DEMO:";
	/**
	 * 默认锁超时时长　单位：毫秒  当前设置30秒
	 */
	public static final int DEFAULT_LOCK_TIME_OUT = 30000;
	/**
	 * 单个：默认锁自动释放时长　单位：毫秒 当前设置1分钟
	 */
	public static final int DEFAULT_SINGLE_EXPIRE_TIME = 60000;
	/**
	 * 批量：默认锁自动释放时长　单位：毫秒 当前设置60秒
	 */
	public static final int DEFAULT_BATCH_EXPIRE_TIME = 60000;
	/**
	 * 锁key 前缀
	 */
	public static final String LOCK_PREFIX = "L:";
	/**
	 * 缓存默认失效天数
	 */
	public static final long CACHE_DEFAULT_EXPIRE = 30L;
	/***************************** Session ********************************/
	/**
	 * session名称
	 */
	public static final String EASYPM_SESSION = "S:";
	/**
	 * session默认过期时间:单位天
	 */
	public static final long SESSION_TIMEOUT = 30L;
	/***************************** APP级 ********************************/
	/**
	 * 系统级过滤器
	 */
	public static final String APP_SEARCHFILTER ="SSF";
	/**
	 * 系统级看板
	 */
	public static final String APP_KANBANLAYOUT ="SKB";
	/***************************** 组织级 *******************************/
	/**
	 * 组织
	 */
	public static final String ORG = "ORG";
	/**
	 * 组织与项目
	 * OP:orgId
	 */
	public static final String ORG_PROJECT ="OP"+SEPARATOR;
	/**
	 * 组织与用户
	 * OU:orgId
	 */
	public static final String ORG_USER ="OU"+SEPARATOR;
	/**
	 * 组织与任务类型
	 * OTT:orgId
	 */
	public static final String ORG_TASKTYPE="OTT"+SEPARATOR;
	/**
	 * 组织任务类型与自定义字段关系
	 */
	public static final String ORG_TASKTYPE_TASKFILED = "OTTTF" + SEPARATOR;
	/**
	 * 任务自定义字段
	 * OTF：orgId
	 */
	public static final String ORG_TASKFIELD = "OTF"+SEPARATOR;
	/**
	 * 任务自定义字段数据源
	 * TFS:taskFieldId
	 */
	public static final String ORG_TASKFIELDSOURCE ="TFS"+SEPARATOR;
	/***************************** 项目级 *******************************/
	/**
	 * 项目成员
	 * PU:prjId
	 */
	public static final String PROJECT_USRE = "PU"+SEPARATOR;
	/**
	 * 项目看板
	 * PKB:projectId
	 */
	public static  final String PROJECT_KANBANLAYOUT = "PKB"+SEPARATOR;
	/**
	 * 项目任务类型
	 * PTT:projectId
	 */
	public static  final String PROJECT_TASKTYPE = "PTT"+SEPARATOR;
	/***************************** 用户组织级 ***************************/
	/**
	 * 用户在组织下可见项目
	 * OUP:orgId：userId
	 */
	public static final String ORG_USER_PROJECT = "OUP" + SEPARATOR;
	/**
	 * 用户组织下收藏
	 * UOF: orgId：userId
	 *
	 */
	public static  final String USER_ORG_FAVOR = "UOF"+SEPARATOR;
	/**
	 * 用户过滤器
	 * USF:userId
	 */
	public static  final String USER_SEARCHFILTER ="USF"+SEPARATOR;


	public static final String PLATFORM_TAG = "PT";

	/**
	 * 用户消息配置
	 */
	public static  final String USER_USERMSGCFG = "UMC";
	/**
	 * 用户权限
	 */
	//public static  final String USER_PERMISSION = "UOP:";
	/***************************** 用户全局级 ***************************/
	/**
	 * 用户表
	 */
	public static final String USER = "USER";
	/**
	 * 组织统计
	 */
	public static final String ORG_STATS = "ORG_STATS";
	/**
	 * 用户组织Set
	 */
	public static final String USER_ORG = "UO:";


	/**************************** 任务自定义字段 ************************/

	/**************************** 锁操作名定义 ************************/
	/**
	 * 操作看板列 操作方法名
	 */
	public static final String LOCK_KANBANCOL = "KANBANCOL:";

	/**
	 * 操作看板 操作方法名
	 */
	public static final String LOCK_PRO_KANBAN = "KANBAN:";

	/**
	 * 操作任务类型 操作方法名
	 */
	public static final String LOCK_ORG_TASKTYPE = "TASKTYPE:";

	/**
	 * 操作组织项目序号 操作方法名
	 */
	public static final String LOCK_ORG_PROJECTNO = "PRJNO:";
	/**
	 * 操作组织任务序号 操作方法名
	 */
	public static final String LOCK_ORG_TASKNO = "TASKNO:";

	/**
	 * 操作组织看板序号 操作方法名
	 */
	public static final String LOCK_ORG_KANBANNO = "KANBANNO:";

	/**
	 * 操作创建任务 锁定此看板的项目
	 */
	public static final String LOCK_PROJECT_KANBAN = "PROJECTKANBAN:";

	/**
	 * 微信用户绑定
	 */
	public static final String LOCK_WEIXIN_USER_BIND = "LOCK_WEIXIN_USER_BIND:";

	/**
	 * SQL 解析缓存锁
	 */
	public static final String LOCK_TSMANGER_SQLINFO = "LOCK_TSMANGER_SQLINFO:";
	/**
	 * 手机注册验证码
	 */
	public static final String SMS_REGISTER_CODE = "sms:r:";
	/**
	 * 修改信息验证码
	 */
	public static final String SMS_C_CODE = "sms:c:";
	/**
	 * 组织API项目统计缓存
	 * ors:orgId
	 */
	public static final String ORG_RAP_STATS ="ORS"+SEPARATOR;
	/**
	 *企业授权缓存
	 * OL:orgId
	 */
	public static final String ORG_LICENCE ="OL";

	/**
	 * 绑定账号验证码有效期
	 */
	public static final int CODE_TIME_OUT = 15;

	/**
	 * 绑定账号的email和验证token
	 */
	public static final String EMAIL_BIND = "email:bind:";

	public static final String EMAIL_VALIDATE = "email:validate:";

	/**
	 * 身份认证
	 */
	public static final String AUTHENTICATION_CODE = "authentication:code:{0}:{1}";
	public static final String AUTHENTICATION_CODE_VALIDATED = "authentication:code:validated:{0}:{1}";



	/**
	 * 绑定手机号的email和验证token
	 */
	public static final String MOBILE_BIND = "mobile:bind:";

	public static final String MOBILE_VALIDATE = "mobile:validate:";

	/**
	 * 忘记密码：找回验证码，邮箱
	 */
	public static final String FORGOT_CODE_EMAIL = "fce:";
	/**
	 * 忘记密码：找回验证码，手机号
	 */
	public static final String FORGOT_CODE_MOBILE = "fcm:";
	/**
	 * 忘记密码：验证码有效期 30分钟
	 */
	public static final int FORGOT_CODE_TIME_OUT = 30;
	/**
	 * 企业ip白名单
	 * OIPW:orgId:ip
	 */
	public static final String ORG_IP_WHITELIST ="OIPW"+SEPARATOR;
	/**
	 * 企业ip白名单:不受限制用户
	 * OIPW:orgId:userId
	 */
	public static final String ORG_IP_WHITELIST_USER ="OIPWU"+SEPARATOR;

	/**
	 * 用户注册防攻击码及数量控制
	 */
	public static final String CODE_ATTACK_LOCK_REGISTER ="code:r:";
	public static final String CODE_ATTACK_LOCK_REGISTER_IP ="code:r:ip:";
	public static final int CODE_ATTACK_LOCK_REGISTER_LIMIT =3;
	public static final int CODE_ATTACK_LOCK_REGISTER_IP_LIMIT =1000;
	public static final int CODE_ATTACK_LOCK_REGISTER_TIMEOUT =1;
	public static final int CODE_ATTACK_LOCK_REGISTER_IP_TIMEOUT =1;
	/**
	 * 用户找回密码防攻击码及数量控制
	 */
	public static final String CODE_ATTACK_LOCK_FORGOT ="code:r:";
	public static final String CODE_ATTACK_LOCK_FORGOT_IP ="code:r:ip:";
	public static final int CODE_ATTACK_LOCK_FORGOT_LIMIT =3;
	public static final int CODE_ATTACK_LOCK_FORGOT_IP_LIMIT =1000;
	public static final int CODE_ATTACK_LOCK_FORGOT_TIMEOUT =1;
	public static final int CODE_ATTACK_LOCK_FORGOT_IP_TIMEOUT =1;

	/**
	 * 用户验证
	 */
}