package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：订单评价成功后回复提醒
 *
 */
public class OrderReplyMsg extends WeixinBaseTemplateMsg {
	private static String MSG_REMARK = "点击查看详情并回复";
	public OrderReplyMsg(){
		this.setTemplate_id(weixinCfg.getOrderReplyTemplate());
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info){}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_FIRST, "您的商品"+map.get("goods_name")+"有一条新评价");
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("customer_name"));//客户名称
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("evaluate_time"));//评价时间
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("evaluate_score"));//评价星级
		this.putInfoToData(MSG_KEY_KEYWORD4,map.get("evaluate_content"));//评价内容
	}
}

