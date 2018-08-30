package com.lwxf.newstore.webapp.common.enums.permission;

import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;

/**
 * 功能：权限验证辅助类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-06 10:05
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class PermissionIndex {
	private PermissionIndex(){

	}

	/**
	 * 配置权限串前缀
	 */
	public static final String PERMISSION_CFG_PREFIX = "cfg:";
	/**
	 * 预约权限串前缀
	 */
	public static final String PERMISSION_RES_PREFIX = "res:";
	/**
	 * 快享权限串前缀
	 */
	public static final String PERMISSION_QUICKSHARE_PREFIX = "qs:";
	/**
	 * 商品字典操作权限串前缀
	 */
	public static final String PERMISSION_GOODS_DICT_PREFIX = "gd:";
	/**
	 * 商品操作权限串前缀
	 */
	public static final String PERMISSION_GOODS_PREFIX = "g:";
	/**
	 * 商品评论操作权限串前缀
	 */
	public static final String PERMISSION_GOODS_COMMENT_PREFIX = "gc:";
	/**
	 * 订单操作权限串前缀
	 */
	public static final String PERMISSION_ORDER_PREFIX = "o:";
	/**
	 * 地址操作权限串前缀
	 */
	public static final String PERMISSION_ADDRESS_PREFIX = "a:";
	/**
	 *商品订单管理权限串前缀
	 */
	public static final String PERMISSION_ORDERGOODS_PREFIX = "og:";
	/**
	 *商品订单管理权限串前缀
	 */
	public static final String PERMISSION_LOGISTICS_PREFIX = "l:";
	/**
	 * 店员管理操作权限串前缀
	 */
	public static final String PERMISSION_CLERKMANAGER_PREFIX = "c:";
	/**
	 * 会员管理操作权限串前缀
	 */
	public static final String PERMISSION_MEMBERMANAGER_PREFIX = "m:";
	/**
	 * 权限定义说明：PERMISSION开头的常量表示各个角色的权限串定义
	 * 1.
	/**
	 * 店主权限
	 */
	public static final String PERMISSION_CFG_SHOPKEEPER = PERMISSION_CFG_PREFIX.concat(ShiroUtil.WILDCARD_TOKEN);
	/**
	 * 店长权限定义，普通店员和会员不具有系统和店铺设置权限
	 */
	public static final String PERMISSION_CFG_MANAGER = PERMISSION_CFG_PREFIX.concat("00111");
	// --- 预约操作权限
	/**
	 * 店主权限
	 */
	public static final String PERMISSION_RES_SHOPKEEPER = PERMISSION_RES_PREFIX.concat("*");
	// 店长权限
	public static final String PERMISSION_RES_MANAGER = PERMISSION_RES_PREFIX.concat("*");
	// 店员权限
	public static final String PERMISSION_RES_CLERK = PERMISSION_RES_PREFIX.concat("*");
	// 普通会员权限
	public static final String PERMISSION_RES_MEMBER = PERMISSION_RES_PREFIX.concat("11100");

	// -- 商品字典权限
	public static final String PERMISSION_GOODS_DICT_SHOPKEEPER= PERMISSION_GOODS_DICT_PREFIX.concat("*");
	public static final String PERMISSION_GOODS_DICT_MANAGER= PERMISSION_GOODS_DICT_PREFIX.concat("1111");
	public static final String PERMISSION_GOODS_DICT_CLERK= PERMISSION_GOODS_DICT_PREFIX.concat("1111");
	public static final String PERMISSION_GOODS_DICT_MEMBER= PERMISSION_GOODS_DICT_PREFIX.concat("0000");

	// -- 商品管理权限
	public static final String PERMISSION_GOODS_SHOPKEEPER= PERMISSION_GOODS_PREFIX.concat("*");
	public static final String PERMISSION_GOODS_MANAGER= PERMISSION_GOODS_PREFIX.concat("1111");
	public static final String PERMISSION_GOODS_CLERK= PERMISSION_GOODS_PREFIX.concat("0000");
	public static final String PERMISSION_GOODS_MEMBER= PERMISSION_GOODS_PREFIX.concat("0000");

	// -- 商品评价管理权限
	public static final String PERMISSION_GOODS_COMMENT_SHOPKEEPER= PERMISSION_GOODS_COMMENT_PREFIX.concat("*");
	public static final String PERMISSION_GOODS_COMMENT_MANAGER= PERMISSION_GOODS_COMMENT_PREFIX.concat("1111");
	public static final String PERMISSION_GOODS_COMMENT_CLERK= PERMISSION_GOODS_COMMENT_PREFIX.concat("1111");
	public static final String PERMISSION_GOODS_COMMENT_MEMBER= PERMISSION_GOODS_COMMENT_PREFIX.concat("1100");

	// -- 商品订单管理权限
	public static final String PERMISSION_ORDER_SHOPKEEPER= PERMISSION_ORDER_PREFIX.concat("*");
	public static final String PERMISSION_ORDER_MANAGER= PERMISSION_ORDER_PREFIX.concat("110111111111");
	public static final String PERMISSION_ORDER_CLERK= PERMISSION_ORDER_PREFIX.concat("110111111111");
	public static final String PERMISSION_ORDER_MEMBER= PERMISSION_ORDER_PREFIX.concat("100100001011");

	// --会员地址管理权限
	public static final String PERMISSION_ADDRESS_SHOPKEEPER= PERMISSION_ADDRESS_PREFIX.concat("*");
	public static final String PERMISSION_ADDRESS_MANAGER= PERMISSION_ADDRESS_PREFIX.concat("*");
	public static final String PERMISSION_ADDRESS_CLERK= PERMISSION_ADDRESS_PREFIX.concat("*");
	public static final String PERMISSION_ADDRESS_MEMBER= PERMISSION_ADDRESS_PREFIX.concat("*");

	// --商品订单权限管理
	public static final String PERMISSION_ORDERGOODS_SHOPKEEPER= PERMISSION_ORDERGOODS_PREFIX.concat("*");
	public static final String PERMISSION_ORDERGOODS_MANAGER= PERMISSION_ORDERGOODS_PREFIX.concat("1111");
	public static final String PERMISSION_ORDERGOODS_CLERK= PERMISSION_ORDERGOODS_PREFIX.concat("1111");
	public static final String PERMISSION_ORDERGOODS_MEMBER= PERMISSION_ORDERGOODS_PREFIX.concat("1101");

	// --发货单权限管理
	public static final String PERMISSION_LOGISTICS_SHOPKEEPER= PERMISSION_LOGISTICS_PREFIX.concat("*");
	public static final String PERMISSION_LOGISTICS_MANAGER= PERMISSION_LOGISTICS_PREFIX.concat("111");
	public static final String PERMISSION_LOGISTICS_CLERK= PERMISSION_LOGISTICS_PREFIX.concat("111");
	public static final String PERMISSION_LOGISTICS_MEMBER= PERMISSION_LOGISTICS_PREFIX.concat("000");
	// -- 店员管理权限
	public static final String PERMISSION_CLERKMANAGER_SHOPKEEPER= PERMISSION_CLERKMANAGER_PREFIX.concat("*");
	public static final String PERMISSION_CLERKMANAGER_MANAGER= PERMISSION_CLERKMANAGER_PREFIX.concat("1111");
	public static final String PERMISSION_CLERKMANAGER_CLERK= PERMISSION_CLERKMANAGER_PREFIX.concat("0000");

	// -- 会员管理权限
	public static final String PERMISSION_MEMBERMANAGER_SHOPKEEPER= PERMISSION_MEMBERMANAGER_PREFIX.concat("*");
	public static final String PERMISSION_MEMBERMANAGER_MANAGER= PERMISSION_MEMBERMANAGER_PREFIX.concat("011");
	public static final String PERMISSION_MEMBERMANAGER_CLERK= PERMISSION_MEMBERMANAGER_PREFIX.concat("000");

	// --- 快享权限
	public static final String PERMISSION_QUICKSHARE= PERMISSION_QUICKSHARE_PREFIX.concat("1111111111");

	/**
	 * 1. 系统配置和商城配置权限定义
	 */
	public enum CfgPermission {
		SYS(0),
		STORE(1),
		ADD_MEMBER(2),
		DISABLED_MEMBER(3),
		RESET_PASSWORD(4);
		int index;
		CfgPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 2. 预约权限定义
	 */
	public enum ResPermission {
		ADD(0), // 添加
		MD_BASE(1), // 修改基本信息
		DELETE(2), // 删除
		MD_STATUS(3), // 修改状态
		MD_DESCR(4) // 修改备注
		;
		int index;
		ResPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 3. 商品字典数据的权限定义
	 */
	public enum GoodsDictPermission {
		ADD(0),
		MD(1),
		DELETE(2),
		DISABLED_OR_ENABLED(3);
		int index;
		GoodsDictPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 4. 商品管理的权限定义
	 */
	public enum GoodsPermission {
		ADD(0),
		MD(1),
		DELETE(2),
		// 下架或上架
		DISABLED_OR_ENABLED(3);
		int index;
		GoodsPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 5. 商品评论的权限定义
	 */
	public enum GoodsCommentPermission {
		ADD(0),
		MD(1),
		DELETE(2),
		// 不显示或显示
		DISABLED_OR_ENABLED(3);
		int index;
		GoodsCommentPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 6. 商品订单管理的权限定义
	 */
	public enum OrderPermission {
		ADD(0),
		MD(1),
		DELETE(2),
		// 会员付款
		MEMBER_PAY(3),
		// 后台付款
		ADMIN_PAY(4),
		// 撤销付款
		CANCEL_PAY(5),
		// 确认发货
		SEND_OUT(6),
		// 取消发货
		CANCEL_SEND_OUT(7),
		// 撤销订单
		CANCEL_ORDER(8),
		// 修改订单商品价格
		MD_GOODS_PRICE(9),
		//生成支付记录
		ADD_PAIDRECORDS(10),
		//关闭订单
		CLOSEORDER(11);

		int index;

		OrderPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 7. 店员管理的权限定义
	 */
	public enum ClerkManagePermission {
		ADD(0),
		// 设置店员角色（店主角色不允许修改，可将普通店员设为店长，也可将店长设为普通店员）
		DELETE(1),
		SET_ROLE(2),
		// 重置密码
		RESET_PASSWORD(3);
		int index;

		ClerkManagePermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 8. 会员管理的权限定义
	 */
	public enum MemberManagePermission {
		MD(0),
		// 设置店员角色（店主角色不允许修改，可将普通店员设为店长，也可将店长设为普通店员）
		DELETE(1),
		// 禁用或启用会员
		DISABLED_OR_ENABLED(2);
		int index;

		MemberManagePermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}

	/**
	 * 9. 快享的权限定义
	 * 	- 1. 普通用户只能操作自己的
	 * 	- 2. 用户自己不能回复自己
	 */
	public enum MicroblogPermission {
		ADD(0),
		MD(1),
		DELETE(2),
		ADD_COMMENT(3),
		MD_COMMENT(4),
		DELETE_COMMENT(5),
		ADD_PRAISE(6),
		DELETE_PRAISE(7),
		ADD_FILE(8),
		DELETE_FILE(9);
		int index;
		MicroblogPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}
	/**
	 * 10. 地址的权限定义
	 */
	public enum  AddressPermission{
		ADD(0),
		MD(1),
		DELETE(2),
		MDSTATUS(3),
		MDDEFAULT(4);
		int index;
		AddressPermission(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}
	/**
	 * 11. 商品订单管理权限定义
	 */
	public  enum  OrderGoodsPermssion{
		ADD(0),
		MD(1),
		DELETE(2),
		MDSTATUS(3);
		int index;
		OrderGoodsPermssion(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}
	/**
	 * 12. 发货单的权限定义
	 */
	public  enum  LogisticsPermssion{
		ADD(0),
		MD(1),
		DELETE(2);
		int index;
		LogisticsPermssion(int index){
			this.index = index;
		}

		public int getIndex(){
			return this.index;
		}
	}
}
