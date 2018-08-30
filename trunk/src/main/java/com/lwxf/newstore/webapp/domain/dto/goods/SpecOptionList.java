package com.lwxf.newstore.webapp.domain.dto.goods;

import java.util.List;

import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 17:55
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SpecOptionList {
	private String id;
	private String name;
	private String goodsTypeId;
	private List<SpecOption> optionList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public List<SpecOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<SpecOption> optionList) {
		this.optionList = optionList;
	}

	@Override
	public String toString() {
		return "SpecOptionList{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", goodsTypeId='" + goodsTypeId + '\'' +
				", optionList=" + optionList +
				'}';
	}
}
