package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：订单关闭发送给管理员的消息
 *
 */
public class OrderCloselToClerksMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好，有客户订单取消";
	private static String MSG_REMARK = "点击查看详情";
	public OrderCloselToClerksMsg(){
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
		this.putInfoToData(MSG_KEY_KEYWORD4,"请及时查看取消原因");//温馨提醒
	}
}

