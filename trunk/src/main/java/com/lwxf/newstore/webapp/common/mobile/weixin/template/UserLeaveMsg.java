package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：会员注销通知消息
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UserLeaveMsg extends WeixinBaseTemplateMsg {
	public UserLeaveMsg(){
		this.setTemplate_id(weixinCfg.getUserLeaveTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "有会员离开了");
		this.setContentInfo(DateUtil.dateTimeToString(DateUtil.getSystemDate(),DateUtil.FORMAT_DATETIME));
		this.setRemark("有会员流失了，您需要加把劲了！");
	}

	@Override
	public void setUserInfo(User user) {
		super.setUserInfo(user);
		this.putInfoToData(MSG_KEY_KEYWORD2,AppBeanInjector.userThirdInfoFacade.findByUserId(user.getId()).getWxNickname());
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD3,info);
	}
}
