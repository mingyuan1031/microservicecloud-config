package com.lwxf.newstore.webapp.common.worker.activity.base;

import java.util.Map;

/**
 * 功能：活动日志R3事件格式封装
 *
 * @author：zhaozhenyi(zhenyi.zhao@ihydt.com)
 * @create：2017-05-26 10:43:28
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
public class ActivityInfoEntity {

	private static final long serialVersionUID = -1029954512405144980L;

	private ResEntity res;
	private Map attr;
	private Object data;



	public ResEntity getRes() {
		return res;
	}

	public void setRes(ResEntity res) {
		this.res = res;
	}

	public Map getAttr() {
		return attr;
	}

	public void setAttr(Map attr) {
		this.attr = attr;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}


