package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;

/**
 * 功能：注册成功通知模版
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RegisterSuccessMsg extends WeixinBaseTemplateMsg {
	public RegisterSuccessMsg(){
		this.setTemplate_id(weixinCfg.getRegisterSuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "有新的会员加入");
		this.setContentInfo(DateUtil.dateTimeToString(DateUtil.getSystemDate(),DateUtil.FORMAT_DATETIME));
		this.setRemark("恭喜您又有新的会员注册成功，祝您生意兴隆！");
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD2,info);
	}
}
