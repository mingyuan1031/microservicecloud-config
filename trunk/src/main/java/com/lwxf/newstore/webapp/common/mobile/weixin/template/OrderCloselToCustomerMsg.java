package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：订单关闭发送给客户的消息
 *
 */
public class OrderCloselToCustomerMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您的订单已关闭，需要购买请再次下单！";
	private static String MSG_REMARK = "点击查看详情";
	public OrderCloselToCustomerMsg(){
		this.setTemplate_id(weixinCfg.getOrderCloseTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("order_number"));//订单号
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("goods_name"));//商品名称
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("order_price"));//订单价格
		this.putInfoToData(MSG_KEY_KEYWORD4,"感谢您使用本平台，欢迎下次购买！");//温馨提醒
	}
}

