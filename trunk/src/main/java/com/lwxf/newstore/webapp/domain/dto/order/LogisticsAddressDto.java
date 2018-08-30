package com.lwxf.newstore.webapp.domain.dto.order;

import java.sql.Types;

import com.lwxf.mybatis.annotation.Column;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/2 15:30
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LogisticsAddressDto {
	private String orderId;
	private String orderGoodsId;
	private String logisticsName;
	private String logisticsNumber;
	private String receiptId;
	private String receiver;
	private String receiverPhone;
	private String receiverAddress;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(String orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	@Override
	public String toString() {
		return "LogisticsAddressDto{" +
				"orderId='" + orderId + '\'' +
				", orderGoodsId='" + orderGoodsId + '\'' +
				", logisticsName='" + logisticsName + '\'' +
				", logisticsNumber='" + logisticsNumber + '\'' +
				", receiptId='" + receiptId + '\'' +
				", receiver='" + receiver + '\'' +
				", receiverPhone='" + receiverPhone + '\'' +
				", receiverAddress='" + receiverAddress + '\'' +
				'}';
	}
}
