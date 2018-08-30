package com.lwxf.newstore.webapp.common.enums.activity;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;

/**
 * 功能：操作事件枚举
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:45:15
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum SystemActivityEvent {
	COMPANY_MD("company:md"),
	/*
	 * 定义配置相关事件
	 */
	SYSCFG_MD("syscfg:md"),
	STORECFG_MD("storecfg:md"),
	/**
	 * 定义预约相关事件
	 */
	// 预约 - 添加
	RESERVATION_CREATE("reservation:create"),
	// 预约 - 状态修改
	RESERVATION_STATUS_MD("reservation:status:md"),
	// 预约 - 基本信息修改
	RESERVATION_MD("reservation:md"),
	// 预约 - 预约单删除
	RESERVATION_DELETE("reservation:delete"),
	/**
	 * 定义购物车相关事件
	 */
	// 购物车 - 添加
	CART_CREATE("cart:create"),
	// 购物车 - 修改
	CART_MD("cart:md"),
	// 购物车 - 删除
	CART_DELETE("cart:delete"),
	/**
	 * 定义商城首页导航相关事件
	 */
	// 导航栏 - 修改
	NAV_MD("nav:delete"),
	// 导航栏 - 状态管理
	NAV_STATUS_MD("nav:status:md"),

	/**
	 * 定义商品相关事件
	 */
	// 商品 - 添加
	GOODS_CREATE("goods:create"),
	// 商品 - 修改基本信息
	GOODS_MD("goods:md"),
	// 商品 - 上架
	GOODS_ENABLED("goods:enabled"),
	// 商品 - 下架
	GOODS_DISABLED("goods:disabled"),
	// 商品 - 报价 - 添加
	GOODS_PRICES_CREATE("goods:prices:create"),
	// 商品 - 报价 - 修改
	GOODS_PRICES_MD("goods:prices:md"),
	// 商品 - 标签 - 增加
	GOODS_TAG_CREATE("goods:tag:create"),
	// 商品 - 标签 - 删除
	GOODS_TAG_DELETE("goods:tag:delete"),
	/**
	 * 定义品牌相关事件
	 */
	// 品牌 - 添加
	BRAND_CREATE("brand:create"),
	// 品牌 - 状态禁用
	BRAND_DISABLED("brand:disabled"),
	// 品牌 - 基本信息修改
	BRAND_MD("brand:md"),
	//品牌 - 状态启用
	BRAND_ENABLED("brand:enabled"),
	//品牌 - 删除品牌
	BRAND_DELETE("brand:delete"),
	/**
	 * 定义标签相关事件
	 */
	// 标签 - 添加
	TAG_CREATE("tag:create"),
	// 标签 - 删除
	TAG_DELETE("tag:delete"),
	// 标签 - 修改
	TAG_MD("tag:md"),
	// 标签 - 禁用
	TAG_DISABLED("tag:disabled"),
	// 标签 - 启用
	TAG_ENABLED("tag:enabled"),
	/**
	 * 定义商品分类相关事件
	 */
	// 商品分类 - 创建
	GOODSTYPE_CREATE("goodstype:create"),
	// 商品分类 - 修改
	GOODSTYPE_MD("goodstype:md"),
	// 商品分类 - 删除
	GOODSTYPE_DELETE("goodstype:delete"),
	// 商品分类 - 禁用
	GOODSTYPE_DISABLED("goodstype:disabled"),
	// 商品分类 - 启用
	GOODSTYPE_ENABLED("goodstype:enabled"),
	// 商品分类 - 规格 - 创建
	GOODSTYPE_SPEC_CREATE("goodstype:spec:create"),
	// 商品分类 - 规格 - 删除
	GOODSTYPE_SPEC_DELETE("goodstype:spec:delete"),
	// 商品分类 - 规格 - 修改
	GOODSTYPE_SPEC_MD("goodstype:spec:md"),
	// 商品分类 - 规格 - 规格选项 - 创建
	GOODSTYPE_SPEC_OPTIONS_CREATE("goodstype:spec:option:create"),
	// 商品分类 - 规格 - 规格选项 - 删除
	GOODSTYPE_SPEC_OPTIONS_DELETE("goodstype:spec:option:delete"),
	// 商品分类 - 规格 - 规格选项 - 修改
	GOODSTYPE_SPEC_OPTIONS_MD("goodstype:spec:option:md"),
	/**
	 * 商品评论相关时间
	 */
	// 商品评论 - 用户 - 创建
	GOODSCOMMENT_USER_CREATE("goodscomment:user:create"),
	// 商品评论 - 店家 - 创建
	GOODSCOMMENT_ADMIN_CREATE("goodscomment:admin:create"),
	// 商品评论 - 隐藏
	GOODSCOMMENT_DISABLED("goodscomment:disabled"),
	// 商品评论 - 显示
	GOODSCOMMENT_ENABLED("goodscomment:enabled"),
	// 商品评论 - 删除
	GOODSCOMMENT_DELETE("goodscomment:delete"),
	/**
	 * 定义会员相关事件
	 */
	// 新增会员
	MEMBER_CREATE("member:create"),
	// 会员角色修改
	MEMBER_ROLE_MD("member:role:md"),
	// 禁用会员
	MEMBER_DISABLED("member:disabled"),
	// 启用会员
	MEMBER_ENABLED("member:enabled"),
	// 会员信息修改
	MEMBER_MD("member:md"),
	// 解除微信绑定（取消公众号关注）
	MEMBER_WX_UNBIND("member:wx:unbind"),
	/**
	 * 定义个人消息相关事件
	 */
	USER_NOTIFY_CREATE("user:notify:create"),
	USER_NOTIFY_MD("user:notify:md"),
	USER_NOTIFY_DELETE("user:notify:delete"),


	/**
	 * 定义快享的相关事件
	 */
	//添加帖子
	QUICKSHARE_CREATE("quickshare:create"),
	QUICKSHARE_DELETE("quickshare:delete"),
	QUICKSHARE_MD("quickshare:md"),
	//添加评论
	QUICKSHARE_COMMENT_CREATE("quickshare:comment:create"),
	// 修改评论
	QUICKSHARE_COMMENT_UPDATE("quickshare:comment:update"),
	QUICKSHARE_COMMENT_DELETE("quickshare:comment:delete"),
	//点赞
	QUICKSHARE_PRAISE_CREATE("quickshare:praise:create"),
	QUICKSHARE_PRAISE_DELETE("quickshare:praise:delete"),
	/**
	 * 定义广告位的相关事件
	 */
	ADVERTISING_CREATE("advertising:create"),
	ADVERTISING_MD("advertising:md"),
	ADVERTISING_LINKSTART_MD("advertising:linkStart:md"),
	ADVERTISING_DELETE("advertising:delete"),

	/**
	 * 定义店员管理相关的事件
	 */
	//新增店员
	ClERK_CREATE("clerk:create"),
	//店员角色修改
	CLERK_ROLE_MD("clerk:role:md"),
	//用户状态修改
	USER_STATE_MD("user:state:md"),

	/**
	 * 订单_用户地址
	 */
	//创建用户地址
	ADDRESS_CREATE("address:create"),
	ADDRESS_DELETE("address:delete"),
	ADDRESS_MD("address:md"),
	ADDRESS_STATUS_MD("address:status:md"),
	/**
	 * 订单_物流
	 */
	LOGISTICS_CREATE("logistics:create"),
	LOGISTICS_DELETE("logistics:delete"),
	LOGISTICS_MD("logistics:md"),
	

	/**
	 * 订单_商品
	 */
	// 订单 商品 修改
	ORDERGOODS_STATUS_MD("ordergoods:status:md"),


	/**
	 * 订单_订单
	 */
	ORDER_CREATE("order:create"),
	ORDER_MD("order:md"),
	ORDER_STATUS_MD("order:status:md");

	private final static Set<String> updateEvents = new HashSet<String>(){{
		add("md");
		add("disabled");
		add("undisable");
		add("enabled");
		add("unbind");
	}};
	private static Logger logger = LoggerFactory.getLogger(SystemActivityEvent.class);
	private String value;

	SystemActivityEvent(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static final SQLType parseEventToSQLType(String activityEvent){
		if(LwxfStringUtils.isBlank(activityEvent)){
			Assert.isTrue(false,"事件不能为空");
			return null;
		}
		String eventOp = activityEvent.substring(activityEvent.lastIndexOf(":")+1);
		if(WebConstant.REQUEST_ACTION_CREATE.equals(eventOp) || WebConstant.KEY_COMMON_ADD.equals(eventOp)){
			return SQLType.INSERT;
		}

		if(WebConstant.REQUEST_ACTION_DELETE.equals(eventOp)){
			return SQLType.DELETE;
		}

		if(updateEvents.contains(eventOp)){
			return SQLType.UPDATE;
		}
		logger.error("******** 出现未在解析范围内的事件，请检查代码确认 ********");
		logger.error("******** 传入的事件字符串：activityEvent = {}" ,activityEvent);
		return null;
	}

	public static final boolean validEvent(String event){
		SystemActivityEvent[] oees = SystemActivityEvent.values();
		for(int m=0,len=oees.length;m<len;m++){
			if(oees[m].getValue().equals(event)){
				return true;
			}
		}
		return false;
	}
}
