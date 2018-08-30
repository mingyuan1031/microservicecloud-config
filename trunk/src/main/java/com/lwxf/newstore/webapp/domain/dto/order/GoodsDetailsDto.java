package com.lwxf.newstore.webapp.domain.dto.order;

import java.math.BigDecimal;

import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/8/9 14:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsDetailsDto extends OrderGoods {
	private String name;
	private String options;
	private String goodsId;
	private BigDecimal originalprice;
	private String smallimage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(BigDecimal originalprice) {
		this.originalprice = originalprice;
	}

	public String getSmallimage() {
		return smallimage;
	}

	public void setSmallimage(String smallimage) {
		this.smallimage = smallimage;
	}

	@Override
	public String toString() {
		return "GoodsDetailsDto{" +
				"name='" + name + '\'' +
				", options='" + options + '\'' +
				", goodsId='" + goodsId + '\'' +
				", originalprice=" + originalprice +
				", smallimage='" + smallimage + '\'' +
				'}';
	}
}
