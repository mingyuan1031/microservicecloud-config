package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Map;

/**
 * 功能：新商品入库的消息
 *
 */
public class AddGoodsMsg extends WeixinBaseTemplateMsg {
	private static String MSG_FIRST="您好，您有新商品被添加";
	private static String MSG_REMARK = "如有疑问，请联系相关人员";
	public AddGoodsMsg(){
		this.setTemplate_id(weixinCfg.getAddGoodsTemplate());
		this.putInfoToData(MSG_KEY_FIRST, MSG_FIRST);
		this.setRemark(MSG_REMARK);
	}

	@Override
	public void setContentInfo(String info) {
	}

	public void setContentMsg(Map<String,String> map){
		this.putInfoToData(MSG_KEY_KEYWORD1,map.get("goods_name"));//商品名称
		this.putInfoToData(MSG_KEY_KEYWORD2,map.get("goods_count"));//入库数量
		this.putInfoToData(MSG_KEY_KEYWORD3,map.get("creat_time"));//入库时间
	}
}

