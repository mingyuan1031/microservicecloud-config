package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.util.Set;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.common.jmail.MailSend;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class AbstractSendCloudMailSend implements MailSend {
	protected static final JsonMapper json = new JsonMapper();
	protected Set<String> tos; // 防止出现重复地址
	protected String message = "";
	protected SendCloudCfg sendCloudCfg;

	@Override
	public String getPersonal() {
		return sendCloudCfg.getFromname();
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Set<String> getTo() {
		return this.tos;
	}

	@Override
	public void setTo(Set<String> tos) {
		this.tos = tos;
	}

	public SendCloudCfg getSendCloudCfg() {
		return sendCloudCfg;
	}
}
