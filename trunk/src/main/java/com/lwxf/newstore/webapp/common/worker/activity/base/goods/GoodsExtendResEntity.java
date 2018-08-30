package com.lwxf.newstore.webapp.common.worker.activity.base.goods;

import java.math.BigDecimal;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/18/018 19:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsExtendResEntity extends ResEntity {
	private BigDecimal price;
	private BigDecimal originalprice;
	private String options;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(BigDecimal originalprice) {
		this.originalprice = originalprice;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}




}
