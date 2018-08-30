package com.lwxf.newstore.webapp.domain.dto.order;

import java.math.BigDecimal;
import java.sql.Types;

import com.lwxf.mybatis.annotation.Column;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/30 10:02
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsDto  {

	private String id;
	private String goodsExtendId;
	private String orderId;
	private Integer goodsAmount;
	private BigDecimal paidPrice;
	private String descr;
	private Integer status;
	private String name;
	private String goodsId;
	private String options;
	private String smallimage;
	private Boolean disabled;
	private String bigimage;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getSmallimage() {
		return smallimage;
	}

	public void setSmallimage(String smallimage) {
		this.smallimage = smallimage;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getPaidPrice() {
		return paidPrice;
	}

	public String getGoodsExtendId() {
		return goodsExtendId;
	}

	public void setGoodsExtendId(String goodsExtendId) {
		this.goodsExtendId = goodsExtendId;
	}

	public void setPaidPrice(BigDecimal paidPrice) {
		this.paidPrice = paidPrice;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getBigimage() {
		return bigimage;
	}

	public void setBigimage(String bigimage) {
		this.bigimage = bigimage;
	}

	@Override
	public String toString() {
		return "GoodsDto{" +
				"id='" + id + '\'' +
				", goodsExtendId='" + goodsExtendId + '\'' +
				", orderId='" + orderId + '\'' +
				", goodsAmount=" + goodsAmount +
				", paidPrice=" + paidPrice +
				", descr='" + descr + '\'' +
				", status=" + status +
				", name='" + name + '\'' +
				", goodsId='" + goodsId + '\'' +
				", options='" + options + '\'' +
				", smallimage='" + smallimage + '\'' +
				", disabled=" + disabled +
				", bigimage='" + bigimage + '\'' +
				'}';
	}
}
