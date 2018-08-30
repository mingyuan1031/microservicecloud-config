package com.lwxf.newstore.webapp.common.worker.activity.base.goods;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/17/017 11:03
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class BrandResEntity extends ResEntity {
	private String content;
	private Boolean disabled;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}
