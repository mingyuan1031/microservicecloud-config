package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/7/26/026 12:01
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsPriceNotUpdateException extends LwxfException {

	public GoodsPriceNotUpdateException() {
		super("报价组合不可更改");
	}

	public GoodsPriceNotUpdateException(String message) {
		super(message);
	}

	public GoodsPriceNotUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsPriceNotUpdateException(Throwable cause) {
		super(cause);
	}

	protected GoodsPriceNotUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_GOODS_PRICE_COMBINATION_NOT_UPDATE_10060;
	}
}
