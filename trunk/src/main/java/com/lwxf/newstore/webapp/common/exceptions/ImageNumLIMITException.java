package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/4/004 12:02
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ImageNumLIMITException extends LwxfException {

	public ImageNumLIMITException() {
		super("图片资源数量超过限制");
	}

	public ImageNumLIMITException(String message) {
		super(message);
	}

	public ImageNumLIMITException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageNumLIMITException(Throwable cause) {
		super(cause);
	}

	protected ImageNumLIMITException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_IMAGE_NUM_LIMIT_10057;
	}
}
