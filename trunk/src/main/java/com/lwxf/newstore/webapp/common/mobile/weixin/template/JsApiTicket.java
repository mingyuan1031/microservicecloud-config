package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.Date;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:17
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class JsApiTicket {
	private String ticket;
	private int limitSecond = (60*60*2-30)*1000;//7170000;
	private Long invalidationTime=null;
	public boolean isRefresh(){
		Date curDate = new Date();
		if(null == invalidationTime || curDate.getTime() > this.invalidationTime){
			return true;
		}
		return false;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket,Date newDate) {
		this.ticket = ticket;
		this.invalidationTime = newDate.getTime()+this.limitSecond;
	}

	public void setLimitSecond(int limitSecond) {
		this.limitSecond = limitSecond;
	}
}
