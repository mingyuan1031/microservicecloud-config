package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.util.Assert;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:54
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CommonMail extends AbstractSendCloudMailSend {
	private User toUser;
	private String content;
	private String subject="EasyPM客服";

	public CommonMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
		this.message = "<p>{0}，您好！</p><p>{1}</p>";
	}

	public void setContentData(User toUser,String content){
		this.toUser = toUser;
		this.content = content;
		this.tos = new HashSet<>();
		this.tos.add(this.toUser.getEmail()); // TODO：需要没有邮箱的情况
	}

	public void setSubject(String subject) {
		if(LwxfStringUtils.isEmpty(subject)){
			return;
		}
		this.subject = subject;
	}

	@Override
	public String getSubject(User sender) {
		return this.subject;
	}

	@Override
	public String getContent() {
		Assert.notNull(this.toUser,"接收人不能为空");
		Map<String, Object> vars = new HashMap<>();
		int tosSize = 1;
		String[] tos = new String[tosSize];
		vars.put("to", this.tos.toArray(tos));
		String[] contents = new String[tosSize];
		for (int m = 0; m < tosSize; m++) {
			contents[m] = LwxfStringUtils.format(message,this.toUser.getName(),this.content);
		}
		Map<String, Object> sub = new HashMap<>();
		sub.put("%content%", contents);
		vars.put("sub", sub);
		return json.toJson(vars);
	}
}
