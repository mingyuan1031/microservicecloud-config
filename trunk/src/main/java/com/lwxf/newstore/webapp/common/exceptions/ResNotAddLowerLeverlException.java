package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/20/020 15:49
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ResNotAddLowerLeverlException extends LwxfException {
	public ResNotAddLowerLeverlException() {
		super("该资源下不可添加下级");
	}

	public ResNotAddLowerLeverlException(String message) {
		super(message);
	}

	public ResNotAddLowerLeverlException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResNotAddLowerLeverlException(Throwable cause) {
		super(cause);
	}

	protected ResNotAddLowerLeverlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	@Override
	public String getErrorCode() {
		return ErrorCodes.VALIDATE_RES_NOT_ADD_LOWER_LEVERL_20037;
	}
}
