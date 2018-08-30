package com.lwxf.newstore.webapp.common.worker.activity.order;

import java.sql.Types;

import com.lwxf.mybatis.annotation.Column;
import com.lwxf.newstore.webapp.common.worker.activity.base.ResEntity;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/24 16:20
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AddressResEntity extends ResEntity {

	private String memberId;
	private String receiver;
	private String receiverPhone;
	private String receiverAddress;
	private String cityAreaId;
	private Boolean defaulted;
	private Integer status;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public String getCityAreaId() {
		return cityAreaId;
	}

	public void setCityAreaId(String cityAreaId) {
		this.cityAreaId = cityAreaId;
	}

	public Boolean getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Boolean defaulted) {
		this.defaulted = defaulted;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
