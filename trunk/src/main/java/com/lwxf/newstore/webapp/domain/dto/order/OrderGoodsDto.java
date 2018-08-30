package com.lwxf.newstore.webapp.domain.dto.order;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/3 9:44
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OrderGoodsDto extends OrderGoods {
	private String orderNumber;
	private String memberId;
	private BigDecimal price;
	private Date paidTime;
	private Integer orderStatus;
	private Date created;
	private String name;
	private String options;
	private String goodsId;
	private BigDecimal originalprice;
	private String smallimage;
	private String bigimage;
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

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

	public String getBigimage() {
		return bigimage;
	}

	public void setBigimage(String bigimage) {
		this.bigimage = bigimage;
	}
}
