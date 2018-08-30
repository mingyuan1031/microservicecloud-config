package com.lwxf.newstore.webapp.config;

import javax.annotation.Resource;
import javax.servlet.Filter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.shiro.*;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:01:38
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class ShiroConfig {
	@Value("${shiro.rememberme.max.age:604800}")
	private Integer shiroRememberMeMaxAge;
	@Value("${shiro.session.redis.expire:10800}")
	private Integer shiroSessionRedisExpire;
	@Resource
	private com.lwxf.newstore.webapp.common.utils.Configuration configuration;

	@Bean
	public LwxfShiroRealm shiroRealm() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(LwxfShiroRealm.HASH_ALGORITHM);
		matcher.setHashIterations(LwxfShiroRealm.HASH_INTERATIONS);
		LwxfShiroRealm realm = new LwxfShiroRealm();
		LwxfWildcardPermissionResolver resolver = new LwxfWildcardPermissionResolver();
		realm.setPermissionResolver(resolver);
		realm.setCredentialsMatcher(matcher);
		return realm;
	}
//	@Bean(name = "sessionIdCookie")
//	public SimpleCookie getSessionIdCookie() {
//		SimpleCookie cookie = new SimpleCookie("sid");
////		cookie.setHttpOnly(true);
//		cookie.setMaxAge(-1);
////		cookie.setName("PHPSESSID");
//		return cookie;
//	}
	@Bean
	public Cookie simpleCookie(){
		SimpleCookie rememberMe = new SimpleCookie("rememberMe");
		rememberMe.setHttpOnly(true);
		rememberMe.setMaxAge(this.shiroRememberMeMaxAge);
		if(configuration.isOnProd())
			rememberMe.setSecure(true);
		return rememberMe;
	}

	@Bean
	public LwxfRememberMeManager rememberMeManager(){
		LwxfRememberMeManager rememberMeManager = new LwxfRememberMeManager();
		rememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		rememberMeManager.setCookie(simpleCookie());
		return rememberMeManager;
	}

	@Bean
	public SessionManager sessionManager(){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		//sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(this.shiroRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		//securityManager.setSessionManager(sessionManager());  // TODO：暂时屏蔽sessionManger
		//securityManager.setCacheManager(this.cacheManager());
		return securityManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
		//配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/404", "anon");

		// 暂时屏蔽权限验证的配置
		filterChainDefinitionMap.put("/api/demos", "anon");

		// 匿名访问

		// 用户注册、激活、登录
		// 匿名url
		//TODO:数据库连接池监控

		filterChainDefinitionMap.put("/signup", "anon");
		filterChainDefinitionMap.put("/api/sys/touch", "anon");
		filterChainDefinitionMap.put("/api/site/touch", "anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/*.png", "anon");
		filterChainDefinitionMap.put("/*.jpg", "anon");
		filterChainDefinitionMap.put("/*.txt", "anon");
		filterChainDefinitionMap.put("/*.js", "anon");
		filterChainDefinitionMap.put("/*.css", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/sitemap.xml", "anon");
		filterChainDefinitionMap.put("/*.txt", "anon");
		filterChainDefinitionMap.put("/news/**", "anon");


		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");

		filterChainDefinitionMap.put("/auth/user", "anon");
		filterChainDefinitionMap.put("/auth/resource", "anon");
		filterChainDefinitionMap.put("/resetpass", "anon");
		filterChainDefinitionMap.put("/forgot", "anon");
		filterChainDefinitionMap.put("/resetpass/*", "anon");

		// 本地打包文件处理
		filterChainDefinitionMap.put("/*.js.map", "anon");

		// 匿名api
		filterChainDefinitionMap.put("/initsearch", "anon");
		filterChainDefinitionMap.put("/api/users/login", "anon");
		filterChainDefinitionMap.put("/api/users/wxlogin", "anon");
		filterChainDefinitionMap.put("/api/users/register", "anon");
		//用户忘记密码发的验证码
		filterChainDefinitionMap.put("/api/users/sendcode/forgot", "anon");
		filterChainDefinitionMap.put("/api/users/forgotcode/validate", "anon");

		// 站点的匿名路径
		filterChainDefinitionMap.put("/terms", "anon");
		filterChainDefinitionMap.put("/authCode", "anon");
		filterChainDefinitionMap.put("/dev/**", "anon");
		filterChainDefinitionMap.put("/resources/**", "anon");
		filterChainDefinitionMap.put("/error", "anon");
		filterChainDefinitionMap.put("/404", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/WEB-INF/**", "anon");
		filterChainDefinitionMap.put("/api/wx", "anon");
		filterChainDefinitionMap.put("/wx/wxlogin", "anon");
		filterChainDefinitionMap.put("/wx/auth", "anon");
		//微信授权回调分发请求
		filterChainDefinitionMap.put("/wx/login", "anon");
		// 微信支付成功的回调地址
		filterChainDefinitionMap.put("/wxpay/notify", "anon");

		//<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/api/users/baseinfos", "anon");//获取用户的所有信息

		filterChainDefinitionMap.put("/api/users/0/wx/menu", "anon"); // 创建微信菜单

		// 所有api请求暂时按匿名处理/api/wxPay
		filterChainDefinitionMap.put("/api/demos", "anon"); // 测试api
		filterChainDefinitionMap.put("/api/demos/**", "anon"); //测试api
		//微信支付
		filterChainDefinitionMap.put("/api/weiXinPay", "anon"); // 测试api
		filterChainDefinitionMap.put("/api/weiXinPay/**", "anon"); //测试api
		filterChainDefinitionMap.put("/api/wxPay", "anon"); // 测试api
		filterChainDefinitionMap.put("/api/wxPay/**", "anon"); //测试api
		filterChainDefinitionMap.put("/payment", "user"); //测试api
		filterChainDefinitionMap.put("/payment/", "user"); //测试api
		filterChainDefinitionMap.put("/payment/mppay", "user"); //测试api
		filterChainDefinitionMap.put("/common.js", "anon"); //测试api

		/**
		 * 配置信息
		 */
		filterChainDefinitionMap.put("/api/cfgdatas", "anon"); //加载配置数据
		filterChainDefinitionMap.put("/api/syscfgs/*", "anon"); //修改系统配置数据
		filterChainDefinitionMap.put("/api/storecfgs/*", "anon"); //修改商城配置数据

		/**
		 * 预约管理
		 */
		filterChainDefinitionMap.put("/api/reservations", "anon"); //显示预约订单信息
		filterChainDefinitionMap.put("/api/reservations/*", "anon"); //删除预约订单信息
		filterChainDefinitionMap.put("/api/reservations/*", "anon"); //修改预约订单信息
		filterChainDefinitionMap.put("/api/reservations", "anon"); //添加预约订单信息
		filterChainDefinitionMap.put("/api/reservations/params", "anon"); //按条件查询预约订单信息(未完成)
		filterChainDefinitionMap.put("/api/users/0/reservations", "anon"); //查看登陆用户的所有预约订单
		filterChainDefinitionMap.put("/api/storecfgs/*/files", "anon"); //上传图片

		/**
		 * 购物车管理
		 */
		filterChainDefinitionMap.put("/api/users/0/carts", "anon"); //获取登陆用户的购物车中所有商品
		filterChainDefinitionMap.put("/api/users/0/carts", "anon"); //删除购物车中多个选中的商品
		filterChainDefinitionMap.put("/api/users/0/carts/*", "anon"); //修改购物车中商品的数量
		filterChainDefinitionMap.put("/api/users/0/carts", "anon"); //向购物车中添加商品
		filterChainDefinitionMap.put("/api/users/0/carts/*", "anon"); //删除单个商品

		/**
		 * 首页导航栏管理
		 */
		filterChainDefinitionMap.put("/api/homenavs", "anon"); //倒叙显示8个首页图标
		filterChainDefinitionMap.put("/api/homenavs/*/files", "anon"); //修改图标
		filterChainDefinitionMap.put("/api/homenavs/*", "anon"); //修改信息

		/**
		 * 快享的API
		 */
		filterChainDefinitionMap.put("/api/microblogs/files", "anon"); //用户发帖上传图片
		filterChainDefinitionMap.put("/api/microblogs", "anon"); //添加快享贴子
		filterChainDefinitionMap.put("/api/microblogs/files/*", "anon"); //删除帖子图片，在发表前删除
		filterChainDefinitionMap.put("/api/microblogs/*", "anon"); //删除帖子及关联的图片
		filterChainDefinitionMap.put("/api/microblogs/*/comments", "anon"); //添加评论
		filterChainDefinitionMap.put("/api/microblogs/*/praises", "anon"); //给帖子点赞
		filterChainDefinitionMap.put("/api/microblogs/*/praises", "anon"); //取消帖子点赞
		filterChainDefinitionMap.put("/api/microblogs", "anon"); //查看普通帖子列表
		filterChainDefinitionMap.put("/api/microblogs/*", "anon"); //查看帖子信息
		filterChainDefinitionMap.put("/api/users/0/microblogs", "anon"); //查看当前用户的快享贴子
		/**
		 *  广告栏的API
		 */
//		filterChainDefinitionMap.put("/api/advertisings", "anon"); //添加广告
//		filterChainDefinitionMap.put("/api/advertisings/files", "anon"); //添加图片
//		filterChainDefinitionMap.put("/api/advertisings/*", "anon"); //添加图片

		/**
		 * 预加载的api
		 */
		filterChainDefinitionMap.put("/api/preload/**", "anon"); //预加载

		/**
		 * 日志的api
		 */

		filterChainDefinitionMap.put("/api/activitys", "anon");//查询
		filterChainDefinitionMap.put("/api/activitys/*", "anon");//删除


		//资讯
		filterChainDefinitionMap.put("/api/news/**", "anon");
		filterChainDefinitionMap.put("/api/newstypes/**", "anon");

		//goods 模块 董世博
		filterChainDefinitionMap.put("/api/brands", "anon"); //品牌
		filterChainDefinitionMap.put("/api/brands/**", "anon"); //品牌
		filterChainDefinitionMap.put("/api/goodstypes", "anon"); //商品分类
		filterChainDefinitionMap.put("/api/goodstypes/**", "anon"); //商品分类
		filterChainDefinitionMap.put("/api/goods", "anon"); //商品
		filterChainDefinitionMap.put("/api/goods/**", "anon"); //商品
		filterChainDefinitionMap.put("/api/goodstype", "anon"); //规格
		filterChainDefinitionMap.put("/api/goodstype/**", "anon"); //规格
		filterChainDefinitionMap.put("/api/goodscomments", "anon"); //商品评论
		filterChainDefinitionMap.put("/api/goodscomments/**", "anon"); //商品评论
		filterChainDefinitionMap.put("/api/tags", "anon"); //标签
		filterChainDefinitionMap.put("/api/tags/**", "anon"); //标签
		//video 模块 dongshibo
		filterChainDefinitionMap.put("/api/videofiles","anon");//视频
		filterChainDefinitionMap.put("/api/videofiles/**","anon");//视频
		filterChainDefinitionMap.put("/api/videotypes/**","anon");//视频分类
		filterChainDefinitionMap.put("/api/videotypes","anon");//视频分类
		//order模块 wangmingyaun
		filterChainDefinitionMap.put("/api/cities", "anon"); //行政区域
		filterChainDefinitionMap.put("/api/cities/**", "anon"); //行政区域
		filterChainDefinitionMap.put("/api/addresses", "anon"); //收货地址
		filterChainDefinitionMap.put("/api/addresses/**", "anon"); //收货地址
		filterChainDefinitionMap.put("/api/orders", "anon"); //订单处理
		filterChainDefinitionMap.put("/api/orders/**", "anon"); //订单处理
		filterChainDefinitionMap.put("/api/logistics", "anon"); //发货单处理
		filterChainDefinitionMap.put("/api/logistics/**", "anon"); //发货单处理
		filterChainDefinitionMap.put("/api/orderGoods", "anon"); //发货单处理
		filterChainDefinitionMap.put("/api/orderGoods/**", "anon"); //商品订单
		//User用户模块 wangmingyaun
//		filterChainDefinitionMap.put("/api/users", "anon");
//		filterChainDefinitionMap.put("/api/users/**", "anon");
//		filterChainDefinitionMap.put("/api/members", "anon");
//		filterChainDefinitionMap.put("/api/members/**", "anon");
//		filterChainDefinitionMap.put("/api/users/0/**", "anon");// 修改用户username

		//店员管理模块
		filterChainDefinitionMap.put("/api/clerks", "anon");//获取店员列表
		filterChainDefinitionMap.put("/api/clerks/**", "anon");

		/**
		 * 设计方案的api
		 */
		filterChainDefinitionMap.put("/api/schemes/*/files", "anon");
		filterChainDefinitionMap.put("/api/schemes", "anon");
		filterChainDefinitionMap.put("/api/schemes/*", "anon");
		filterChainDefinitionMap.put("/api/schemes/*/praise", "anon");
		filterChainDefinitionMap.put("/api/schemes/*/status/*", "anon");



		filterChainDefinitionMap.put("/druid/**", "user,shiroSysFilter");
		filterChainDefinitionMap.put("/mall", "user"); // 商城首页
		filterChainDefinitionMap.put("/admin", "user,shiroSysFilter"); // 店家后台
		filterChainDefinitionMap.put("/storecard", "user"); // 企业名片
		filterChainDefinitionMap.put("/quickshare", "user"); // 快享
		filterChainDefinitionMap.put("/reservation", "user"); // 预约
		/**
		 * 微信消息卡片的路径验证
		 */
		filterChainDefinitionMap.put("/api/users/0/mqtoken", "user");
		filterChainDefinitionMap.put("/api/users/0/preload", "user");
		filterChainDefinitionMap.put("/api/users/0", "user");
		filterChainDefinitionMap.put("/api/users/0/password", "user");
		filterChainDefinitionMap.put("/api/users/0/avatar", "user");
		filterChainDefinitionMap.put("/api/users/0/background", "user");
		filterChainDefinitionMap.put("/api/users/0/accredit", "user");
		filterChainDefinitionMap.put("/api/users/0/qrcode", "user");
		filterChainDefinitionMap.put("/api/users/0/wx", "user");
		filterChainDefinitionMap.put("/api/users/0/thirdinfo", "user");
		filterChainDefinitionMap.put("/api/users/0/sendcode/mobile", "user");
		filterChainDefinitionMap.put("/api/users/0/mobile", "user");
		filterChainDefinitionMap.put("/api/users/0/mobile/validate", "user");
		filterChainDefinitionMap.put("/api/users/0/msgcfg", "user");
		filterChainDefinitionMap.put("/api/users/0/mqconnections", "user");
		filterChainDefinitionMap.put("/api/users/0/authentication/sendcode", "user");
		filterChainDefinitionMap.put("/api/users/0/authentication/validate", "user");
		filterChainDefinitionMap.put("/api/wx/jscfg", "user");
		filterChainDefinitionMap.put("/api/search", "user");

		filterChainDefinitionMap.put("/api/sys/errors", "urlFilter");
		filterChainDefinitionMap.put("/api/**", "user,urlFilter");
		filterChainDefinitionMap.put("/*", "user,urlFilter");
		filterChainDefinitionMap.put("/*/", "user,urlFilter");
		filterChainDefinitionMap.put("/**", "notFoundFilter");


		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl(WebConstant.REDIRECT_PATH_LOGIN);
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/404");
		/**
		 * add filters
		 */
		Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
		filterMap.put("logout",new LogoutFilter(){{
			this.setRedirectUrl("login");
		}});
		filterMap.put("urlFilter",new LwxfShiroUrlFilter());
		filterMap.put("user",new LwxfUserFilter());
		filterMap.put("notFoundFilter", new NotFoundFilter());
		filterMap.put("shiroSysFilter",new LwxfSysFilter());
		return shiroFilterFactoryBean;
	}

	/*@Bean
	public RedisCacheManager cacheManager(){
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		return redisCacheManager;
	}

	@Bean
	public RedisSessionDAO redisSessionDAO(){
		return new RedisSessionDAO(this.shiroSessionRedisExpire);
	}*/

	/*@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}*/
}
