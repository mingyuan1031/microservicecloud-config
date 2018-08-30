package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.util.Assert;

import com.lwxf.commons.utils.DateUtil;
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
public class AuthCodeMail extends AbstractSendCloudMailSend {
	private User toUser;
	private String authCode;
	private Date createTime;
	private int activeTime=1; // 单位为分钟

	public AuthCodeMail() {
		this.sendCloudCfg = SendCloudCfgFactory.createSendCloudCfg(this);
		this.message = "<p>{0}，您好！</p>" +
				"<p>您本次使用EasyPM的验证码为 <b>{1}</b> </p>"+
				"<p>本验证码生成于{2}，将在{3}分钟后失效，请妥善保管，不要发给任何人</p>";
	}

	public void setContentData(User toUser, String authCode, Date createTime, Integer activeTime){
		this.toUser = toUser;
		this.authCode = authCode;
		this.createTime = createTime;
		if(null != activeTime){
			this.activeTime = activeTime;
		}
		this.tos = new HashSet<>();
		this.tos.add(this.toUser.getEmail()); // TODO：需要没有邮箱的情况
	}

	@Override
	public String getSubject(User sender) {
		return "来自EasyPM的验证码";
	}

	@Override
	public String getContent() {
		Assert.notNull(this.toUser,"接收人不允许为空");
		Assert.notNull(this.createTime,"创建时间不允许为空");
		Assert.isTrue(LwxfStringUtils.isNotEmpty(this.authCode),"验证码不允许为空");
		Map<String, Object> vars = new HashMap<>();
		int tosSize = 1;
		String[] tos = new String[tosSize];
		vars.put("to", this.tos.toArray(tos));
		String[] contents = new String[tosSize];
		for (int m = 0; m < tosSize; m++) {
			contents[m] = LwxfStringUtils.format(message,this.toUser.getName(),this.authCode, DateUtil.dateTimeToString(this.createTime),this.activeTime);
		}
		Map<String, Object> sub = new HashMap<>();
		sub.put("%content%", contents);
		vars.put("sub", sub);
		return json.toJson(vars);
	}
}
