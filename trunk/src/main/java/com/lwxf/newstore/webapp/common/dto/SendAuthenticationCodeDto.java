package com.lwxf.newstore.webapp.common.dto;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:53:00
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SendAuthenticationCodeDto {
	/**
	 * 身份认证的场景：user - 用户信息的修改；org - 组织的解散和转让；project - 项目的删除
	 */
	private String scene;
	/**
	 * 验证码的接收者：mobile - 手机号；emial - 邮箱；weixin - 微信号
	 */
	private String receiver;

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
