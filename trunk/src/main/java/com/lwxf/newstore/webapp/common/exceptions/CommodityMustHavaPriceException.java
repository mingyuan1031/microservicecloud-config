package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/23/023 18:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CommodityMustHavaPriceException extends LwxfException {
	public CommodityMustHavaPriceException() {
		super("商品必须有价格");
	}

	public CommodityMustHavaPriceException(String message) {
		super(message);
	}

	public CommodityMustHavaPriceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommodityMustHavaPriceException(Throwable cause) {
		super(cause);
	}

	protected CommodityMustHavaPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_A_COMMODITY_MUST_HAVA_A_PRICE_10062;
	}
}
