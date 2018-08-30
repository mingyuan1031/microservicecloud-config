package com.lwxf.newstore.webapp.common.mobile.weixin;

import java.util.Date;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:17
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AccessToken {
	private String token;
	private int limitSecond = (60*60*2-30)*1000;//7170000;
	private Long invalidationTime=null;

	public boolean isRefresh(){
		Date curDate = new Date();
		if(null == invalidationTime || curDate.getTime() > this.invalidationTime){
			return true;
		}
		return false;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token,Date newDate) {
		this.token = token;
		this.invalidationTime = newDate.getTime()+this.limitSecond;
	}

	public void setLimitSecond(int limitSecond) {
		this.limitSecond = limitSecond;
	}
}
