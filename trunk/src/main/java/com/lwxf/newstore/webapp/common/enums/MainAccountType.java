package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:58:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum MainAccountType {
	//0-邮箱,1-手机,2-微信,3-钉钉,4-github,5-qq,6-weibo
	EMAIL(0), MOBILE(1), WEIXIN(2), DING(3), GITHUB(4), QQ(5),WEIBO(6);

	private Integer value;

	MainAccountType(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
