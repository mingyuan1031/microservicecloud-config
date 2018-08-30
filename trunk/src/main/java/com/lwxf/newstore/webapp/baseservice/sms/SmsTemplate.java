package com.lwxf.newstore.webapp.baseservice.sms;

import com.lwxf.mybatis.utils.MapContext;

/**
 * 功能：短信消息模板
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:51:29
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SmsTemplate {
	/**
	 * 短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
	 * 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	 */
	private String phoneNumbers;
	/**
	 * 短信签名
	 */
	private String signName;
	/**
	 * 短信模板ID
	 */
	private String templateCode;
	/**
	 *短信模板变量替换JSON串,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
	 * 比如短信内容中包含\r\n的情况在JSON中需要表示成\r\n,否则会导致JSON在服务端解析失败
	 * {“code”:”1234”,”product”:”ytx”}
	 */
	private String templateParam;



	/**
	 * 短信模板参数
	 */
	private MapContext templateParamMapContext;
	/**
	 * 90999	上行短信扩展码,无特殊需要此字段的用户请忽略此字段
	 */
	private String smsUpExtendCode;
	/**
	 *外部流水扩展字段
	 */
	private String outId;

	public SmsTemplate(String phoneNumbers, String signName, String templateCode, String templateParam) {
		this.phoneNumbers = phoneNumbers;
		this.signName = signName;
		this.templateCode = templateCode;
		this.templateParam = templateParam;
	}

	public SmsTemplate() {
	}

	public String getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public String getSmsUpExtendCode() {
		return smsUpExtendCode;
	}

	public void setSmsUpExtendCode(String smsUpExtendCode) {
		this.smsUpExtendCode = smsUpExtendCode;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public MapContext getTemplateParamMapContext() {
		return templateParamMapContext;
	}

	public void setTemplateParamMapContext(MapContext templateParamMapContext) {
		this.templateParamMapContext = templateParamMapContext;
	}
}
