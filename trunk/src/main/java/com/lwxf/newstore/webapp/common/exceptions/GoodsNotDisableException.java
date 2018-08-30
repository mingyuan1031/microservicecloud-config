package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/4/004 18:04
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsNotDisableException extends LwxfException {
	public GoodsNotDisableException() {
		super("商品未下架不可修改");
	}

	public GoodsNotDisableException(String message) {
		super(message);
	}

	public GoodsNotDisableException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsNotDisableException(Throwable cause) {
		super(cause);
	}

	protected GoodsNotDisableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058;
	}
}
