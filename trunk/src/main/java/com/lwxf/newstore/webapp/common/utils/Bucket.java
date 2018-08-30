package com.lwxf.newstore.webapp.common.utils;

import java.util.regex.Pattern;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:31
 * @Copyright：2018 Version：1.0
 * @Company：老屋新房 Created with IntelliJ IDEA
 */
public class Bucket {
	private Pattern pattern;
	private String bucketName;
	private String bucketDomain;

	public Bucket(String bucketName, String bucketDomain, String regExpStr) {
		this.bucketName = bucketName;
		this.bucketDomain = bucketDomain;
		pattern = Pattern.compile(regExpStr);
	}

	public String getBucketName() {
		return this.bucketName;
	}

	public String getBucketDomain() {
		return this.bucketDomain;
	}

	public boolean matcher(String servletPath) {
		return this.pattern.matcher(servletPath).matches();
	}
}
