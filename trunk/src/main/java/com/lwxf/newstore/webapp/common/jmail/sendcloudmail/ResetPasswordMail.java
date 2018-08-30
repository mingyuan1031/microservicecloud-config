package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.HashMap;
import java.util.Map;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.jmail.ResetPasswordMailSend;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:18
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ResetPasswordMail extends AbstractSendCloudMailSend implements ResetPasswordMailSend {
	protected String INVITE_URL_PREFIX = "/resetpass/";
	private UserResetpass resetPass;
	private User user;
	private MapContext codeMap;
	public ResetPasswordMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
	}

	@Override
	public String getSubject(User sender) {
		return "重置密码申请";
	}

	@Override
	public String getContent() {
		//id将数字与62个字符进行转换，可以有效缩减数字长度
		String hrefPath = WebUtils.getDomainUrl() + INVITE_URL_PREFIX + this.resetPass.getId();

		Map<String, Object> vars = new HashMap<>();
		String[] tos = new String[]{user.getEmail()};
		vars.put("to", tos);
		String[] urls = new String[]{hrefPath};
		//dangdong 2017年8月1日 老大要求重置密码时候name改为邮箱
		String[] userNames = new String[]{user.getEmail()};
//		String userNames[] = new String[]{user.getName()};
		String[] dates = new String[]{DateUtil.dateTimeToString(this.resetPass.getCreated())};

		Map<String, Object> sub = new HashMap<>();
		sub.put("%url%", urls);
		sub.put("%date%", dates);
		sub.put("%userName%", userNames);
		vars.put("sub", sub);
		return json.toJson(vars);
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
