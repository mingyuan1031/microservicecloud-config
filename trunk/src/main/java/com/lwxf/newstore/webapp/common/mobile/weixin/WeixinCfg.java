package com.lwxf.newstore.webapp.common.mobile.weixin;

import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lwxf.commons.security.DesTool;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:07:34
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("weixinCfg")
public class WeixinCfg {
	private static final DesTool desTool = new DesTool();
	@Value("${lwxf.wx.msg.templatge.bind.success:#{null}}")
	private String bindSuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.register.success:#{null}}")
	private String registerSuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.user.leave:#{null}}")
	private String userLeaveTemplate;
	@Value("${lwxf.wx.msg.templatge.reservation.success:#{null}}")
	private String reservationSuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.apply.success:#{null}}")
	private String applySuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.lack.goods:#{null}}")
	private String lackGoodsTemplate;
	@Value("${lwxf.wx.msg.templatge.addorder.success:#{null}}")
	private String addOrderSuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.addorder.fail:#{null}}")
	private String addOrderFailTemplate;
	@Value("${lwxf.wx.msg.templatge.pay.success:#{null}}")
	public String paySuccessTemplate;
	@Value("${lwxf.wx.msg.templatge.add.goods:#{null}}")
	public String addGoodsTemplate;
	@Value("${lwxf.wx.msg.order.delivery.success:#{null}}")
	public String orderDeliveryTemplate;
	@Value("${lwxf.wx.msg.templatge.order.evaluate:#{null}}")
	public String orderEvaluateTemplate;
	@Value("${lwxf.wx.msg.templatge.order.reply:#{null}}")
	public String orderReplyTemplate;
	@Value("${lwxf.wx.msg.templatge.order.close:#{null}}")
	public String orderCloseTemplate;

	//微信公众号配置
	private String appId;
	private String secret;
	private String token;
	private DesTool epmSecret;

	//web微信配置
	private String webAppId;
	private String webSecret;


	@Value("${lwxf.wx.access.cfg:#{null}}")
	public void setAccessCfg(String cfg){
		Assert.notNull(cfg,"微信配置不允许为空");
		cfg = desTool.decrypt(cfg);
		String[] cfgArr = cfg.split("=");
		Assert.isTrue(cfgArr.length==4);
		this.appId = cfgArr[0];
		this.secret = cfgArr[1];
		this.token = cfgArr[2];
		epmSecret = new DesTool(cfgArr[3]);
	}

	public String getAppId() {
		return appId;
	}

	public String getSecret() {
		return secret;
	}

	public String getToken() {
		return token;
	}

	public String encryptForEpmSceret(Object obj){
		return this.epmSecret.encrypt(obj.toString());
	}

	public String decryptForEpmSceret(String str){
		return this.epmSecret.decrypt(str);
	}

	public String getWebAppId() {
		return webAppId;
	}

	public void setWebAppId(String webAppId) {
		this.webAppId = webAppId;
	}

	public String getWebSecret() {
		return webSecret;
	}

	public void setWebSecret(String webSecret) {
		this.webSecret = webSecret;
	}

	public String getBindSuccessTemplate() {
		return bindSuccessTemplate;
	}

	public String getRegisterSuccessTemplate() {
		return registerSuccessTemplate;
	}

	public String getUserLeaveTemplate() {
		return userLeaveTemplate;
	}

	public String getReservationSuccessTemplate() {
		return reservationSuccessTemplate;
	}

	public String getApplySuccessTemplate() {
		return applySuccessTemplate;
	}
	public String getLackGoodsTemplate(){
		return lackGoodsTemplate;
	}
	public String getAddOrderSuccessTemplate(){
		return addOrderSuccessTemplate;
	}
	public String getAddOrderFailTemplate(){
		return addOrderFailTemplate;
	}
	public String getPaySuccessTemplate(){
		return paySuccessTemplate;
	}
	public String getAddGoodsTemplate(){
	    return addGoodsTemplate;
    }
    public String getOrderDeliveryTemplate(){
		return orderDeliveryTemplate;
	}
	public String getOrderEvaluateTemplate(){return orderEvaluateTemplate;}
	public String getOrderReplyTemplate(){return orderReplyTemplate;}
	public String getOrderCloseTemplate(){return orderCloseTemplate;}
}
