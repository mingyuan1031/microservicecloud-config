package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：申请为店员成功的通知
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-05 15:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ApplyClerkSuccessMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好，您已成为本公司店员，请妥善保管账号信息";
	private static String MSG_ADMIN_URL = WebUtils.getDomainUrl().concat("/admin");
	private static String MSG_VALIDITY_PERIOD="永久";
	private static String MSG_REMARK = "为确保账号安全，登录账号后请在第一时间修改用户登录名和登录密码";
	public ApplyClerkSuccessMsg(String storeName){
		this.setTemplate_id(weixinCfg.getApplySuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.putInfoToData(MSG_KEY_KEYWORD1,MSG_ADMIN_URL);
		this.putInfoToData(MSG_KEY_KEYWORD3,WebConstant.USER_DEFAULT_PASSWORD);
		this.putInfoToData(MSG_KEY_KEYWORD4,storeName);
		this.putInfoToData(MSG_KEY_KEYWORD5,MSG_VALIDITY_PERIOD);
		this.setContentInfo(MSG_REMARK);
	}

	@Override
	public void setUserInfo(User user) {
		this.putInfoToData(MSG_KEY_KEYWORD2,user.getUsername());
	}

	@Override
	public void setContentInfo(String info) {
		this.putInfoToData(MSG_KEY_REMARK,MSG_REMARK);
	}
}
