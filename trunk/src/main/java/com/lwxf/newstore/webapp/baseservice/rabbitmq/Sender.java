package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import java.util.List;

/**
 * 功能：
 *		消息发送接口
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:59:11
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface Sender {

	/**
	 * 发送异步消息
	 * @param messageEntitys
	 */
	void sendAsynMessage(List<MessageEntity> messageEntitys);


	/**
	 * 发送同步消息
	 * @param messageEntitys
	 */
	void sendMessage(List<MessageEntity> messageEntitys);

	void sendMessage(boolean isAsync,MessageEntity... messageEntities);
}
