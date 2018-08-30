package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.HashMap;
import java.util.Map;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：激活注册账号邮件
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:24
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SendActivationMail extends ResetPasswordMail {
	public SendActivationMail() {
		this.INVITE_URL_PREFIX = "/activate/";
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
	}

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


		Map<String, Object> vars = new HashMap<>();
		String[] tos = new String[]{this.tos.iterator().next()};
		vars.put("to", tos);
		String[] urls = new String[]{pathSb.toString()};
		String[] userNames = new String[]{this.getUser().getName()};
		String[] dates = new String[]{DateUtil.dateTimeToString(this.getResetPass().getCreated())};

		Map<String, Object> sub = new HashMap<>();
		sub.put("%url%", urls);
		sub.put("%userName%", userNames);
		sub.put("%date%", dates);
		vars.put("sub", sub);
		return json.toJson(vars);
	}
}
