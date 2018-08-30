package com.lwxf.newstore.webapp.common.exceptions;

import com.lwxf.commons.exception.LwxfException;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/7/25/025 19:38
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsNotReceivedNotCommentException extends LwxfException {
	public GoodsNotReceivedNotCommentException() {
		super("商品未收货不可评论");
	}

	public GoodsNotReceivedNotCommentException(String message) {
		super(message);
	}

	public GoodsNotReceivedNotCommentException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsNotReceivedNotCommentException(Throwable cause) {
		super(cause);
	}

	protected GoodsNotReceivedNotCommentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	@Override
	public String getErrorCode() {
		return ErrorCodes.BIZ_GOODS_NOT_RECEIVED_NOT_COMMENTABLE_10059;
	}
}
