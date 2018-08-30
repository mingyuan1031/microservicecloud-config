package com.lwxf.newstore.webapp.common.worker.activity.advertising;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/20 16:12
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AdvertisingResEntity extends ResEntity {


	private String path;

	private Integer linkStart;

	private  String link;

	private Integer place;

	private Integer linkType;

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer isLinkStart() {
		return linkStart;
	}

	public void setLinkStart(Integer linkStart) {
		this.linkStart = linkStart;
	}
}
