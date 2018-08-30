package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：预约成功通知（用户）
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ReservationSuccessForUserMsg extends WeixinBaseTemplateMsg {
	public ReservationSuccessForUserMsg(){
		this.setTemplate_id(weixinCfg.getReservationSuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "您已经预约成功");
	}

	@Override
	public void setUserInfo(User user) {

	}

	public void setReservationInfo(Reservation reservation){
		this.putInfoToData(MSG_KEY_KEYWORD1,reservation.getName());
		this.setContentInfo(reservation.getPhone());
		StringBuilder sb = new StringBuilder();
		sb.append("您的预约面积为").append(reservation.getArea()).append("㎡，请保持电话畅通，我们会尽快联系您！");
		this.setRemark(sb.toString());
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD2,info);
	}
}
