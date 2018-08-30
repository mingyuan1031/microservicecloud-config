package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.user.User;

import java.util.Map;

/**
 * 功能：新订单成功给管理员的通知
 *
 */
public class AddOrderSuccessForClerkMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好，您有一条新客户订单";
	private static String MSG_REMARK = "请尽快配送！！！";
	public AddOrderSuccessForClerkMsg(){
		this.setTemplate_id(weixinCfg.getAddOrderSuccessTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("order_number"));//订单编号(商品明细)
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("order_time"));//下单时间
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("receipt_id"));//配送地址
		this.putInfoToData(MSG_KEY_KEYWORD4,map.get("contacts"));//联系人
		this.putInfoToData(MSG_KEY_KEYWORD5,map.get("status"));//付款状态
	}
}

