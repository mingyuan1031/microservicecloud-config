package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import org.springframework.util.Assert;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:26
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class WeixinBaseTemplateMsg extends BaseTemplateMsg {
	public void setRemark(String remark){
		if(LwxfStringUtils.isBlank(remark)){
			return;
		}
		this.putInfoToData(MSG_KEY_REMARK,remark);
	}
	public void setUserInfo(User user){
		Assert.notNull(user,"用户不能为空");
		this.putInfoToData(MSG_KEY_KEYWORD1,user.getName());
	}
}
