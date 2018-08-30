package com.lwxf.newstore.webapp.domain.dto.order;

import java.util.Arrays;

import com.lwxf.newstore.webapp.domain.entity.order.Logistics;

/**
 * 功能：
 * 创建发货单时使用
 * @author：Administrator
 * @create：2018/7/11 16:44
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LogisticsPostDto {
	private  String [] ids;
	private  String orderId;
	private  Logistics logistics;

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Logistics getLogistics() {
		return logistics;
	}

	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}

	@Override
	public String toString() {
		return "LogisticsPostDto{" +
				"ids=" + Arrays.toString(ids) +
				", orderId='" + orderId + '\'' +
				", logistics=" + logistics +
				'}';
	}
}
