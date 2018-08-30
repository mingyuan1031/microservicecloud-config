package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/22/022 14:59
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class NonRepeatableUploadException extends LwxfException {
	public NonRepeatableUploadException() {
		super("不可重复上传");
	}

	public NonRepeatableUploadException(String message) {
		super(message);
	}

	public NonRepeatableUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonRepeatableUploadException(Throwable cause) {
		super(cause);
	}

	protected NonRepeatableUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_NON_REPEATABLE_UPLOAD_10064;
	}
}
