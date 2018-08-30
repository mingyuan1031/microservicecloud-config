package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/16/016 17:27
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LackOfStockException extends LwxfException {
	public LackOfStockException() {
		super("库存不足");
	}

	public LackOfStockException(String message) {
		super(message);
	}

	public LackOfStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public LackOfStockException(Throwable cause) {
		super(cause);
	}

	protected LackOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_LACK_OF_STOCK_10063;
	}
}
