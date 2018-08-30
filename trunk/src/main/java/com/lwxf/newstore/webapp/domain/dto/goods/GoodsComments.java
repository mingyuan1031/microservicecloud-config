package com.lwxf.newstore.webapp.domain.dto.goods;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/9/009 19:43
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsComments {
	private String id;
	private String memberId;
	private String goodsId;
	private String content;
	private Boolean disabled;
	private Date created;
	private String goodsExtendId;
	private String score;
	private String reply;
	private Date replyCreated;
	private String goodsName;
	private String replyId;
	private List<UploadFiles> imgList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getGoodsExtendId() {
		return goodsExtendId;
	}

	public void setGoodsExtendId(String goodsExtendId) {
		this.goodsExtendId = goodsExtendId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getReplyCreated() {
		return replyCreated;
	}

	public void setReplyCreated(Date replyCreated) {
		this.replyCreated = replyCreated;
	}

	public List<UploadFiles> getImgList() {
		return imgList;
	}

	public void setImgList(List<UploadFiles> imgList) {
		this.imgList = imgList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	@Override
	public String toString() {
		return "GoodsComments{" +
				"id='" + id + '\'' +
				", memberId='" + memberId + '\'' +
				", goodsId='" + goodsId + '\'' +
				", content='" + content + '\'' +
				", disabled='" + disabled + '\'' +
				", created='" + created + '\'' +
				", goodsExtendId='" + goodsExtendId + '\'' +
				", score='" + score + '\'' +
				", reply='" + reply + '\'' +
				", replyCreated='" + replyCreated + '\'' +
				", imgList=" + imgList +
				'}';
	}
}
