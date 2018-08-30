package com.lwxf.newstore.webapp.common.worker.activity.base.goods;

import java.util.Date;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/21/021 15:44
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsCommentResEntity extends ResEntity {
	private String content;
	private Date created;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
