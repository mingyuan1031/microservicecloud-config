package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/7/28/028 17:50
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RepliesAllowedOnlyOnceException extends LwxfException {

	public RepliesAllowedOnlyOnceException() {
		super("回复只允许一次");
	}

	public RepliesAllowedOnlyOnceException(String message) {
		super(message);
	}

	public RepliesAllowedOnlyOnceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepliesAllowedOnlyOnceException(Throwable cause) {
		super(cause);
	}

	protected RepliesAllowedOnlyOnceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_REPLIES_ARE_ALLOWED_ONLY_ONCE_10061;
	}
}
