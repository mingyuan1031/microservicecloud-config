package com.lwxf.newstore.webapp.common.worker.activity.quickshare.microblogComment;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/19 11:43
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class MicroblogCommentResEntity extends ResEntity {
	//评论的内容
	private String content;
	//被评论内容的ID
	private  String parentId;
	//被评论人的ID
	private  String parentCreator;
	//被评论帖子的ID
	private String microblogId;

	public String getMicroblogId() {
		return microblogId;
	}

	public void setMicroblogId(String microblogId) {
		this.microblogId = microblogId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentCreator() {
		return parentCreator;
	}

	public void setParentCreator(String parentCreator) {
		this.parentCreator = parentCreator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
