package com.lwxf.newstore.webapp.common.model;

import java.io.Serializable;
/**
 * 功能：分页基本信息
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:32:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class Range<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private T from;
	private T to;

	public Range() {
		//
	}

	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public static <T> Range<T> newOne() {
		return new Range<T>();
	}

	public static <T> Range<T> newOne(T from, T to) {
		return new Range<T>(from, to);
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

}
