package com.lwxf.newstore.webapp.common.exceptions;

/**
 * 功能：ErrorCodes扩展定义
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-04 11:12
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class ErrorCodes extends com.lwxf.commons.exception.ErrorCodes {
	//图片资源数量超过限制
	public final static String BIZ_IMAGE_NUM_LIMIT_10057 = "10057";
	//商品未下架不可修改
	public final static String BIZ_GOODS_NOT_DISABLED_UPDATA_10058 = "10058";
	//商品未收货不可评论
	public final static String BIZ_GOODS_NOT_RECEIVED_NOT_COMMENTABLE_10059 = "10059";
	//报价组合不可更改
	public final static String BIZ_GOODS_PRICE_COMBINATION_NOT_UPDATE_10060="10060";
	//回复只允许一次
	public final static String BIZ_REPLIES_ARE_ALLOWED_ONLY_ONCE_10061="10061";
	//该资源下不可添加下级
	public final static String VALIDATE_RES_NOT_ADD_LOWER_LEVERL_20037 = "20037";
	//商品必须有价格
	public final static String BIZ_A_COMMODITY_MUST_HAVA_A_PRICE_10062="10062";
	//库存不足
	public final static String BIZ_LACK_OF_STOCK_10063="10063";
	//不可重复上传 Non-repeatable upload
	public final static String BIZ_NON_REPEATABLE_UPLOAD_10064="10064";
}
