package com.lwxf.newstore.webapp.common.mobile.weixin.template;

/**
 * 功能：库存不足提醒通知模版
 *
 */
public class LackGoodsMsg extends WeixinBaseTemplateMsg {
	public LackGoodsMsg(){
		this.setTemplate_id(weixinCfg.getLackGoodsTemplate());
		this.putInfoToData(MSG_KEY_FIRST, "有商品库存不足");
		this.setRemark("请尽快添加");
	}

	public void setGoodsId(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD1,info);
	}
	public void setGoodName(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD2,info);
	}
	public void setGoodsCount(String info) {
		this.putInfoToData(MSG_KEY_KEYWORD3,info);
	}

	@Override
	public void setContentInfo(String info) {
	}
}
