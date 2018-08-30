package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import org.apache.commons.collections4.CollectionUtils;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.uniquekey.IdGererateFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

import static com.lwxf.newstore.webapp.config.RabbitMQConfig.LWXF_DEFAULT_EXCHANGE_PREFIX;

/**
 * 功能：
 *		MQ工具类
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:21:26
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Component
public class RabbitMQSender implements Sender{
	private final static Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);
	private static final int exchangeMode = 5;
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();//排除null
	private JsonMapper jsonNullMapper = new JsonMapper();//不排除null
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Resource
	private RabbitAdmin rabbitAdmin;
	@Resource
	private IdGererateFactory idGererateFactory;
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	//属性过虑
	{
		jsonMapper.addFilterAllExclude(MessageEntity.class, "scope", "scopeId", "orgId", "notNull");
	}

	/**
	 * 发送异步消息
	 * @param messageEntitys
	 */
	@Override
	public void sendAsynMessage(List<MessageEntity> messageEntitys){
		if(CollectionUtils.isEmpty(messageEntitys)){
			logger.debug("send messages is empty........");
			return;
		}
		final String userId = WebUtils.getCurrUserId();
		final String _tag = WebUtils.getRabbitMQUserTag();

		threadPoolTaskExecutor.execute(()->{
			WebUtils.setCurrUserId(userId);
			WebUtils.setRabbitMQUserTag(_tag);

			if(messageEntitys != null){
				for (MessageEntity messageEntity : messageEntitys) {
					sendMQMessage(messageEntity);
				}
			}
		});
	}

	@Override
	public void sendMessage(List<MessageEntity> messageEntitys) {
		if(messageEntitys != null){
			for (MessageEntity messageEntity : messageEntitys) {
				sendMQMessage(messageEntity);
			}
		}
	}

	@Override
	public void sendMessage(boolean isAsync, MessageEntity... messageEntities) {
		if(messageEntities == null || messageEntities.length==0){
			return;
		}
		Stream<MessageEntity> stream =Arrays.stream(messageEntities);
		if(isAsync){
			final String userId = WebUtils.getCurrUserId();
			final String companyId = WebUtils.getCurrCompanyId();
			final String _tag = WebUtils.getRabbitMQUserTag();
			threadPoolTaskExecutor.execute(()->{
				WebUtils.setCurrUserId(userId);
				WebUtils.setCurrCompanyId(companyId);
				WebUtils.setRabbitMQUserTag(_tag);
				stream.forEach(me -> {
					sendMQMessage(me);
				});
			});
		}else{
			stream.forEach(me -> {
				sendMQMessage(me);
			});
		}
	}

	/**
	 * 发送消息
	 */
	private void sendMQMessage(MessageEntity messageEntity){
		check(messageEntity);
		//生成routingkey
		String routingKey = createRoutingKey(messageEntity);
		//发送消息
		logger.debug("send rabbitmq message ...." );
		rabbitTemplate.convertAndSend (getExchange(messageEntity.getCompanyId()), routingKey, jsonMapper.toJson(messageEntity), createCorrelationData());
	}


	/**
	 * 生成routingkey
	 * @param messageEntity
	 * @return
	 */
	private String createRoutingKey(MessageEntity messageEntity) {
		MessageScope scope = messageEntity.getScope();
		String prefix = null;
		switch (scope){
			case USER:
				return MessageUtils.getUserRoutingKey(messageEntity.getScopeId());
			default:
				Assert.isTrue(false, "枚举类型不支持");
		}
		return null;
	}

	/**
	 * 检查
	 * @param messageEntity
	 */
	private void check(MessageEntity messageEntity){
		if (logger.isDebugEnabled()) {
			logger.debug("messageEntity:" + jsonMapper.toJson(messageEntity));
		}
		Assert.isTrue(messageEntity.getEvent() != null, "事件类型不能为空");
		Assert.isTrue(messageEntity.getScope() != null, "消息通道不能为空");
		Assert.isTrue(messageEntity.getScopeId() != null, "消息scopeId不能为空");
		if(messageEntity.getX_TAG() == null)
			logger.error("X_TAG不能为空,"+messageEntity.toString());
		//Assert.isTrue(messageEntity.getOrgId() != null, "orgId不能为空");
		Assert.isTrue(messageEntity.getData() != null, "消息data不能为空");
	}

	/**
	 * 根据companyId获取exchange
	 * @return
	 */
	private String getExchange(String companyId){
		String name = LWXF_DEFAULT_EXCHANGE_PREFIX + 0;
		//rabbitAdmin.declareExchange(new TopicExchange(name));
		return name;
	}

	/**
	 * 消息编号
	 * @return
	 */
	private CorrelationData createCorrelationData(){
		return new CorrelationData(idGererateFactory.nextId().toString());
	}
}
