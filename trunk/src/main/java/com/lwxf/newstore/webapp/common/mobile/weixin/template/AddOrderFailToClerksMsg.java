package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：订单提交失败发送给管理员的消息
 *
 */
public class AddOrderFailToClerksMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好，有客户订单提交异常";
	private static String MSG_REMARK = "请尽快查看";
	public AddOrderFailToClerksMsg(){
		this.setTemplate_id(weixinCfg.getAddOrderFailTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("order_number"));//订单编号
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("paid_price"));//订单金额
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("created"));//创建时间
	}
}

