package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.HashMap;
import java.util.Map;

import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：给老用户发送新版上线邮件：模板内容特殊,其他情况勿调用
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:09
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OnlineMail extends AbstractSendCloudMailSend {

	public OnlineMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
	}

	@Override
	public String getSubject(User sender) {
		return null;
	}

	@Override
	public String getContent() {
		Map<String, Object> vars = new HashMap<>(1);
		int tosSize = this.tos.size();
		String[] tos = this.tos.toArray(new String[tosSize]);
		vars.put("to", tos);
		Map<String, Object> sub = new HashMap<>(1);
		sub.put("%toUser%", tos);
		vars.put("sub", sub);
		return json.toJson(vars);
	}
}
