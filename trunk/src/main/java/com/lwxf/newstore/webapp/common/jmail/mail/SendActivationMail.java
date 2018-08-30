package com.lwxf.newstore.webapp.common.jmail.mail;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：激活注册账号邮件
 *
 * @User：sunchangji()
 * @create：2018-05-24 13:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SendActivationMail extends ResetPasswordMail {
	protected static final String INVITE_URL_PREFIX = "/activate/";
	@Override
	public String getSubject(User sender) {
		return "请激活您的账号";
	}

	@Override
	public String getContent() {
		String invitationCode="";
		//batchjoin_code invitation_code
		if(this.getCodeMap()!=null){
			if(this.getCodeMap().containsKey("batchjoin_code")){
				invitationCode="?batchjoin_code=".concat(this.getCodeMap().getTypedValue("batchjoin_code",String.class));
			}else if(this.getCodeMap().containsKey("invitation_code")){
				invitationCode="?invitation_code=".concat(this.getCodeMap().getTypedValue("invitation_code",String.class));
			}
		}
		StringBuilder pathSb = new StringBuilder();
		pathSb.append(WebUtils.getDomainUrl() ).append(INVITE_URL_PREFIX).append(this.getResetPass().getId()).append(invitationCode);

		StringBuilder sb = new StringBuilder();
		sb.append("<p>您好，" + this.getUser().getEmail() + "！</p>")
				.append("<p>感谢您于 ")
				.append(DateUtil.dateTimeToString(this.getResetPass().getCreated()))
				.append(" 注册EasyPM！</p>")
				.append("<p>请点击以下链接激活你的账户并设置您的账号密码：</p>")
				.append("<p style=\"padding:10px;border-radius:5px;background:#474d57;margin-left:20px;margin-right:20px\">")
				.append("<a style=\"word-break:break-all;line-height:23px;color:white;font-size:15px;text-decoration:none;\" href='")
				.append(pathSb.toString())
				.append("'>")
				.append(pathSb.toString())
				.append("</a></p>");
		return LwxfStringUtils.format(CONTENT_TEMPLATE, sb.toString());
	}
}
