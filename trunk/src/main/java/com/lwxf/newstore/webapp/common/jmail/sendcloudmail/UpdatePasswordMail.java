package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UpdatePasswordMail extends AbstractSendCloudMailSend {
	protected String INVITE_URL_PREFIX = "/resetpass";
	private User user;
	private Date updateDate;

	public UpdatePasswordMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
	}

	@Override
	public String getSubject(User sender) {
		return "您的账号密码发生了变化";
	}

	@Override
	public String getContent() {
		Assert.notNull(this.updateDate,"更新日志不允许为空");
		String hrefPath = WebUtils.getDomainUrl() + INVITE_URL_PREFIX ;

		Map<String, Object> vars = new HashMap<>();
		String[] tos = new String[]{user.getEmail()};
		vars.put("to", tos);
		String[] urls = new String[]{hrefPath};
		String[] userNames = new String[]{user.getName()};
		String[] dates = new String[]{DateUtil.dateTimeToString(updateDate)};
		String[] ip = new String[]{WebUtils.getClientIpAddress()};

		Map<String, Object> sub = new HashMap<>();
		sub.put("%url%", urls);
		sub.put("%date%", dates);
		sub.put("%userName%", userNames);
		sub.put("%id%", userNames);
		sub.put("%ip%", ip);
		vars.put("sub", sub);
		return json.toJson(vars);
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
