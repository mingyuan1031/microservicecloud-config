package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：预约成功通知（店员）
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ReservationSuccessForClerkMsg extends WeixinBaseTemplateMsg {
	public ReservationSuccessForClerkMsg(){
		this.setTemplate_id(weixinCfg.getReservationSuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "恭喜您有新的预约了");
	}

	@Override
	public void setUserInfo(User user) {

	}

	public void setReservationInfo(Reservation reservation){
		this.putInfoToData(MSG_KEY_KEYWORD1,reservation.getName());
		this.setContentInfo(reservation.getPhone());
		StringBuilder sb = new StringBuilder();
		sb.append("客户的预约面积为").append(reservation.getArea()).append("㎡，请尽快联系客户！");
		this.setRemark(sb.toString());
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD2,info);
	}
}
