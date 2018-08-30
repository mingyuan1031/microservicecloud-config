package com.lwxf.newstore.webapp.common.worker.activity.order;

import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;
import com.lwxf.newstore.webapp.domain.entity.order.Order;

import java.math.BigDecimal;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/26 17:24
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OrderResEntity extends ResEntity {

	 private BigDecimal paidPrice;

	private BigDecimal freight;

	private String descr;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getPaidPrice() {
		return paidPrice;
	}

	public void setPaidPrice(BigDecimal paidPrice) {
		this.paidPrice = paidPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}
