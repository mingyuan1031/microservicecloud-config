package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：订单发货的消息
 *
 */
public class OrderDeliveryMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您购买的订单已经发货啦，正快马加鞭向您飞奔而去。";
	private static String MSG_REMARK = "请保持收件手机畅通！";
	public OrderDeliveryMsg(){
		this.setTemplate_id(weixinCfg.getOrderDeliveryTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("order_number"));//订单编号
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("order_time"));//发货时间
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("logistics_company"));//物流公司
		this.putInfoToData(MSG_KEY_KEYWORD4,map.get("courier_number"));//快递单号
		this.putInfoToData(MSG_KEY_KEYWORD5,map.get("pickup_information"));//收件信息
	}
}

