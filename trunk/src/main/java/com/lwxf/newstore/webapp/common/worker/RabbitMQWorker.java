package com.lwxf.newstore.webapp.common.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageEntity;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.RabbitMQSender;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterCommitEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeUpdateEventListener;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.mq.base.MQBuilder;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:10:08
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Component("rabbitMQWorker")
public class RabbitMQWorker extends AbstractAutowireWorker implements BeforeUpdateEventListener, AfterCommitEventListener {
	private static Logger logger = LoggerFactory.getLogger(RabbitMQWorker.class);
	private Map<Class, MQBuilder> registerMqs = new HashMap<>();
	//存放额外消息
	public static final String RABBITMQ_EXTRA_MSG = "rabbitmq_extra_msg";


	@Resource
	private RabbitMQSender rabbitMQSender;

	/**
	 * 注册
	 * @param clazz
	 * @param mqBuilder
	 */
	public void register(Class clazz, MQBuilder mqBuilder) {
		if (registerMqs.containsKey(clazz)) {
			Assert.isTrue(false, "class: " + clazz.getName() + " 重复注册，请检查代码");
		}
		this.registerMqs.put(clazz, mqBuilder);
	}

	@Override
	public void beforeUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		//线程放置数据
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		//埋点数据
		if (tsManualData != null) {
			MQBuilder mqBuilder = registerMqs.get(tsManualData.getClazz());
			if (mqBuilder != null) {
				mqBuilder.doBefore(tsManualData);
			}
		}

		//解析sql
		if (tsManagerEntity != null) {
			MQBuilder mqBuilder = registerMqs.get(tsManagerEntity.getClazz());
			if (mqBuilder != null) {
				mqBuilder.doBefore(tsManagerEntity, tsManagerEntity.getSqlType());
			}
		}
	}

	@Override
	public void afterCommit(List<TSManagerEntity> tsManagerEntitys) {
		List<MessageEntity> list = new ArrayList<>();

		//线程放置数据
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		//埋点数据
		if (tsManualData != null) {
			MQBuilder mqBuilder = registerMqs.get(tsManualData.getClazz());
			if (mqBuilder != null) {
				Object obj = mqBuilder.build(tsManualData);
				addMessageEntity(list, obj);
			}
		}

		//解析sql,TSManager中已经对影响行数为0的sql进行处理
		if (tsManagerEntitys != null && tsManagerEntitys.size() > 0) {
			for (TSManagerEntity tsManagerEntity : tsManagerEntitys) {
				MQBuilder mqBuilder = registerMqs.get(tsManagerEntity.getClazz());
				if (mqBuilder != null) {
					Object obj = mqBuilder.build(tsManagerEntity, tsManagerEntity.getSqlType());
					addMessageEntity(list, obj);
				}
			}
		}

		//线程中单独的消息合并
		addMessageEntity(list,WebUtils.getDataFromRequestMap(RABBITMQ_EXTRA_MSG));

        //发送消息
		if (list.size() > 0) {
			rabbitMQSender.sendAsynMessage(list);
		}
	}

	/**
	 * 封装数据
	 *
	 * @param list
	 * @param object
	 */
	private void addMessageEntity(List<MessageEntity> list, Object object) {
		if (object != null) {
			if (object instanceof MessageEntity) {
				MessageEntity messageEntity = (MessageEntity) object;
				list.add(messageEntity);
			} else if (object instanceof List) {
				list.addAll((List) object);
			} else {
				throw new RuntimeException("返回类型不支持");
			}
		}
	}
}
