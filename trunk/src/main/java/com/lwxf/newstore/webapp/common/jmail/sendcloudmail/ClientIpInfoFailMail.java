package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.HashMap;
import java.util.Map;

import com.lwxf.newstore.webapp.domain.entity.user.User;


/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:54
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ClientIpInfoFailMail extends AbstractSendCloudMailSend {
	public ClientIpInfoFailMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
		this.message = "获取登录用户的客户端IP地址详细信息失败";
	}

	@Override
	public String getSubject(User sender) {
		return "获取客户端地址异常";
	}

	@Override
	public String getContent() {
		Map<String, Object> vars = new HashMap<>();
		int tosSize = this.tos.size();
		String[] tos = new String[tosSize];
		vars.put("to", this.tos.toArray(tos));
		String[] contents = new String[tosSize];
		for (int m = 0; m < tosSize; m++) {
			contents[m] = message;
		}
		Map<String, Object> sub = new HashMap<>();
		sub.put("%content%", contents);
		vars.put("sub", sub);
		return json.toJson(vars);
	}
}
