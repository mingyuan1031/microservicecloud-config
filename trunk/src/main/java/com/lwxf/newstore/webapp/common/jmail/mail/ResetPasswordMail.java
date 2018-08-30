package com.lwxf.newstore.webapp.common.jmail.mail;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.jmail.ResetPasswordMailSend;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;

/**
 * 功能：重置密码邮件
 *
 * @User：sunchangji()
 * @create：2018-05-24 13:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ResetPasswordMail extends AbstractMailSend implements ResetPasswordMailSend {
	protected String INVITE_URL_PREFIX = "/resetpass/";
	private UserResetpass resetPass;
	private User user;
	private MapContext codeMap;
	@Override
	public String getSubject(User sender) {
		return "重置密码申请";
	}

	@Override
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		String hrefPath = WebUtils.getDomainUrl() + INVITE_URL_PREFIX + this.getResetPass().getId();

		sb.append("<p>")
				.append("您于 ")
				.append(DateUtil.dateTimeToString(resetPass.getCreated()))
				.append(" 申请重置密码，请点击下方链接或拷贝至浏览器地址栏进行密码重置：")
				.append("</p>")
				.append("<p style=\"padding:10px;border-radius:5px;background:#474d57;margin-left:20px;margin-right:20px\">")
				.append("<a target='_blank' style=\"word-break:break-all;line-height:23px;color:white;font-size:15px;text-decoration:none;\" href='")
				.append(hrefPath)
				.append("'>")
				.append(hrefPath)
				.append("</a>")
				.append("</p>");
		return LwxfStringUtils.format(CONTENT_TEMPLATE, sb.toString());
	}

	public UserResetpass getResetPass() {
		return resetPass;
	}

	@Override
	public void setResetPass(UserResetpass resetPass) {
		this.resetPass = resetPass;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public MapContext getCodeMap() {
		return codeMap;
	}

	@Override
	public void setCodeMap(MapContext codeMap) {
		this.codeMap = codeMap;
	}
}
