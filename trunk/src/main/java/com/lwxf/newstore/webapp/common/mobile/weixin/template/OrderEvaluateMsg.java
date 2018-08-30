package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：商品收货成功后评价的消息
 *
 */
public class OrderEvaluateMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好,你的订单已经收货成功";
	private static String MSG_REMARK = "点击详情,您可以对订单进行评价";
	public OrderEvaluateMsg(){
		this.setTemplate_id(weixinCfg.getOrderEvaluateTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("order_number"));//订单编号
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("order_time"));//订单时间
	}
}

