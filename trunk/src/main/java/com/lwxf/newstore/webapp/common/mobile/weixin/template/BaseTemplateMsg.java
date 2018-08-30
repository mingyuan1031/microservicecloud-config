package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.mobile.IMobileMsg;
import com.lwxf.newstore.webapp.common.mobile.weixin.WeixinCfg;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.domain.entity.order.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:43
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseTemplateMsg implements IMobileMsg {
	protected static String MSG_KEY_FIRST="first";
	protected static String MSG_KEY_KEYWORD1="keyword1";
	protected static String MSG_KEY_KEYWORD2="keyword2";
	protected static String MSG_KEY_KEYWORD3="keyword3";
	protected static String MSG_KEY_KEYWORD4="keyword4";
	protected static String MSG_KEY_KEYWORD5="keyword5";
	protected static String MSG_KEY_REMARK="remark";
	protected static WeixinCfg weixinCfg;
	static {
		weixinCfg = (WeixinCfg)SpringContextUtil.getBean("weixinCfg");
	}
	@JsonIgnore
	private JsonMapper json = JsonMapper.nonEmptyMapper();
	private String touser;
	private String template_id;
	private String url;
	private Map<String,Object> data = new HashMap<>();

	public String getTouser() {
		return touser;
	}

	@Override
	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	protected void putInfoToData(String key,String info){
		Assert.notNull(this.data,"消息内容不能为空");
		if(LwxfStringUtils.isEmpty(info)){
			this.data.put(key, null);
			return;
		}
		Map<String,Object> infoMap = new HashMap<>();
		infoMap.put("value",info);
		infoMap.put("color","#173177");
		this.data.put(key, infoMap);
	}



	@Override
	public String serialized() {
		return this.json.toJson(this);
	}
}
