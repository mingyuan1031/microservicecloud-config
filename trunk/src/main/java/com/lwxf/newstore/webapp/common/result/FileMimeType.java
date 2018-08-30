package com.lwxf.newstore.webapp.common.result;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:39:42
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class FileMimeType {
	/**
	 * 原始mime type
	 */
	String originalType;
	/**
	 * 最终mime type
	 */
	String realType;

	public String getOriginalType() {
		return originalType;
	}

	public void setOriginalType(String originalType) {
		this.originalType = originalType;
	}

	public String getRealType() {
		return realType;
	}

	public void setRealType(String realType) {
		this.realType = realType;
	}
}
