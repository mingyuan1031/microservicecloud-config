package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;

/**
 * 功能：绑定店主通知模版
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:07
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class BindShopkeeperMsg extends WeixinBaseTemplateMsg {
	public BindShopkeeperMsg(){
		this.setTemplate_id(weixinCfg.getBindSuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "您的微信已与店主帐号绑定");
		this.putInfoToData(MSG_KEY_KEYWORD2,DateUtil.dateTimeToString(DateUtil.getSystemDate(),DateUtil.FORMAT_DATETIME));
		this.setContentInfo("绑定为店主后，您会及时收到重要的微信消息通知");
		// this.setRemark("");
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD3,info);
	}
}
