package com.lwxf.newstore.webapp.domain.dto.user;

import com.lwxf.newstore.webapp.domain.entity.order.PaidRecords;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 16:33
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class PaidDto extends PaidRecords {
	private String orderNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
}
