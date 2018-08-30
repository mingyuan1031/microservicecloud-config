package com.lwxf.newstore.webapp.common.mobile.weixin.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.mobile.IMobileMsg;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:37
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseMsgService implements IMsgService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract String getUrl();
	@Override
	public void pushMsg(final IMobileMsg msgTemplate) {
		final String msgContent = msgTemplate.serialized();
		new Thread(){
			@Override
			public void run() {
				try {
					HttpURLConnection http = WeixinUtils.createConnection(LwxfStringUtils.format(getUrl(), AppBeanInjector.wxMpService.getAccessToken()), "POST");

					http.connect();
					OutputStream os = http.getOutputStream();
					os.write(msgContent.getBytes("UTF-8"));// 传入参数
					InputStream is = http.getInputStream();
					int size = is.available();
					byte[] jsonBytes = new byte[size];
					is.read(jsonBytes);
					String result = new String(jsonBytes, "UTF-8");
					logger.debug("发送的微信消息内容：" + msgContent);
					logger.debug("发送微信消息结果：" + result);
					os.flush();
					is.close();
					os.close();
					http.disconnect();
				} catch (Exception e) {
					logger.error("发送微信消息失败",e);
				}
			}
		}.start();
	}
}
