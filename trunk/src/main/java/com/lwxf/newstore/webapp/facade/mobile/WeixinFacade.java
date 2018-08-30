package com.lwxf.newstore.webapp.facade.mobile;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.Map;

import com.lwxf.newstore.webapp.common.dto.UserInfoObj;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.result.RequestResult;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:09
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface WeixinFacade {
	void doRequestXmlData(String queryString, Map<String, String> reqXmlData);
	void bindWeixin(String userId, String orgUserId, String openId);
	RequestResult unbindWeixinByUserId(String userId);
	String createMenu(String menuJson);
	String deleteMenu();
	String getJSSDKConfig(String url);
	String upload(String filePath,String fileType);

	/**
	 * 创建新的账号
	 * @param wxMpUser
	 * @param userRole
	 * @return
	 */
	UserInfoObj createUser(WxMpUser wxMpUser, UserRole userRole);
}
