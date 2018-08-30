package com.lwxf.newstore.webapp.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageEntity;

/**
 * 功能：MQ操作一些工具方法
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:21:09
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class MQUtil {
	public static void addMessageEntityToCurrThread(String key,MessageEntity messageEntity){
		List<MessageEntity> msgs = (List<MessageEntity>) WebUtils.getDataFromRequestMap(key);
		if(msgs == null){
			msgs = new ArrayList<>();
			WebUtils.putDataToRequestMap(key,msgs);
		}
		msgs.add(messageEntity);
	}

	public static List<MessageEntity> getMessageEntitiesFromCurrThread(String key){
		return (List<MessageEntity>) WebUtils.getDataFromRequestMap(key);
	}

}
