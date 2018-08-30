package com.lwxf.newstore.webapp.domain.dto.goods;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/13/013 17:22
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsTagsInfo {
	private String goodsTagId;
	private String goodsId;
	private String tagId;
	private String name;
	private String color;
	private boolean disabled;

	public String getGoodsTagId() {
		return goodsTagId;
	}

	public void setGoodsTagId(String goodsTagId) {
		this.goodsTagId = goodsTagId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "GoodsTagsInfo{" +
				"goodsTagId='" + goodsTagId + '\'' +
				", goodsId='" + goodsId + '\'' +
				", tagId='" + tagId + '\'' +
				", name='" + name + '\'' +
				", color='" + color + '\'' +
				", disabled='" + disabled + '\'' +
				'}';
	}
}
