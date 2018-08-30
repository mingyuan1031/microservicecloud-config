package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.common.mobile.IMobileMsg;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseJsonMsg implements IMobileMsg {
	@JsonIgnore
	private JsonMapper json = JsonMapper.nonEmptyMapper();
	private String touser;
	private String msgtype;

	public String getTouser() {
		return touser;
	}

	@Override
	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	@Override
	public String serialized(){
		return this.json.toJson(this);
	}

	@Override
	public void setUrl(String url) {

	}

	@Override
	public String getUrl() {
		return null;
	}
}
