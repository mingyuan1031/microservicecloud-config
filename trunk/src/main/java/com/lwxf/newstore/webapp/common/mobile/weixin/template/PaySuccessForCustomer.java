package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：支付成功该用户的通知
 *
 */
public class PaySuccessForCustomer extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="商品购买成功，请您注意物流信息，及时收取货物";
	private static String MSG_REMARK = "感谢您的使用，祝您生活愉快！！！";
	public PaySuccessForCustomer(){
		this.setTemplate_id(weixinCfg.getPaySuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info){}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("goods"));//订单商品
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("order_number"));//订单编号
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("paid_price"));//订单金额
		this.putInfoToData(MSG_KEY_KEYWORD4,map.get("paid_time"));//支付时间
		this.putInfoToData(MSG_KEY_KEYWORD5,map.get("receipt_id"));//收货信息
	}
}

