package com.lwxf.newstore.webapp.config;

import javax.annotation.Resource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

import com.lwxf.commons.security.DesTool;
import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.RabbitMQService;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能： rabbitmq配置
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:37:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Configuration
public class RabbitMQConfig {
	private static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
	public final static String LWXF_DEFAULT_EXCHANGE_PREFIX = "lwxf_e_";//exchange名称
	private static final DesTool desTool = new DesTool();
	public static final String RABBITMQ_EVENT_EXCHANGE = "amq.rabbitmq.event";
	public static final String LWXF_ONLINE_USER_QUEUE = "lwxf_online_user_queue";
	public static final String USER_ONLINE = "UL:";
	public static final String USER_ONLINE_RELATION = "UL:RT";

	@Resource
	private RabbitMQProperties rabbitMQProperties;
	@Resource
	private RabbitMQService rabbitMQService;
	@Resource
	private RedisUtils redisUtils;

	@Bean(destroyMethod = "destroy")
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setRequestedHeartBeat(rabbitMQProperties.getHeartbeat());
		connectionFactory.setHost(desTool.decrypt(rabbitMQProperties.getServerHost()));
		connectionFactory.setPort(Integer.parseInt(desTool.decrypt(rabbitMQProperties.getPort())));
		connectionFactory.setUsername(desTool.decrypt(rabbitMQProperties.getUsername()));
		connectionFactory.setPassword(desTool.decrypt(rabbitMQProperties.getPassword()));
		connectionFactory.setVirtualHost(rabbitMQProperties.getVirtualHost());
		connectionFactory.setConnectionTimeout(rabbitMQProperties.getConnectionTimeout());
		connectionFactory.setPublisherConfirms(true); //消息确认配置
		return connectionFactory;
	}

	/**
	 * 消费mq事件的连接，此连接是连接到默认的VirtualHost上
	 * @return
	 */
	@Bean(name="customerConnectionFactory", destroyMethod = "destroy")
	public CachingConnectionFactory customerConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setRequestedHeartBeat(rabbitMQProperties.getHeartbeat());
		connectionFactory.setHost(desTool.decrypt(rabbitMQProperties.getServerHost()));
		connectionFactory.setPort(Integer.parseInt(desTool.decrypt(rabbitMQProperties.getPort())));
		connectionFactory.setUsername(desTool.decrypt(rabbitMQProperties.getUsername()));
		connectionFactory.setPassword(desTool.decrypt(rabbitMQProperties.getPassword()));
		connectionFactory.setVirtualHost("/");
		connectionFactory.setConnectionTimeout(rabbitMQProperties.getConnectionTimeout());
		connectionFactory.setPublisherConfirms(true); //消息确认配置
		return connectionFactory;
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory customerConnectionFactory, RedisUtils redisUtils) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(customerConnectionFactory);
		Queue queue = new Queue(LWXF_ONLINE_USER_QUEUE);
		String queueName = rabbitAdmin.declareQueue(queue);
		logger.debug("rabbitmq创建在线用户队列");
		if(queueName == null){
			logger.error("rabbitmq创建{}队列失败", LWXF_ONLINE_USER_QUEUE);
			return null;
		}
		Binding bingding =  BindingBuilder.bind(queue).to(new TopicExchange(RABBITMQ_EVENT_EXCHANGE)).with("connection.*");
		rabbitAdmin.declareBinding(bingding);
		logger.debug("rabbitmq创建队列绑定成功");

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(customerConnectionFactory);
		container.setQueues(queue);
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				MessageProperties messageProperties = message.getMessageProperties();
				Map<String, Object> headers = messageProperties.getHeaders();
				String event = messageProperties.getReceivedRoutingKey();
				try {
					switch (event){
						case "connection.closed":
							doConnectionClosed(headers);
							break;
						case "connection.created":
							doConnectionCreated(headers);
							break;
						default:
							logger.debug("此消息未处理");
							break;
					}
				}catch (Exception e){
					logger.error(e.getMessage());
				}finally {
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
				}
			}
		});
		return container;
	}

	private void doConnectionCreated(Map<String, Object> headers) {
		String user = (String)headers.get("user");
		String pid = (String)headers.get("pid");
		if(rabbitMQProperties.getVirtualHost().equals(headers.get("vhost"))){
			String[] str = user.split("_");
			String companyId = WebUtils.getCurrCompanyId();
			if(str.length == 2 && companyId != null){
				String userId = str[1];
				redisUtils.hPut(USER_ONLINE_RELATION, pid, userId);
				User userEntity = AppBeanInjector.userService.findById(userId);
				if(null !=userEntity){
					String hashKey = USER_ONLINE.concat(companyId);
					Integer connectionCount = (Integer) redisUtils.hGet(hashKey, userId);
					if(connectionCount == null){
						redisUtils.hPut(hashKey, userId, 1);
						//发送用户上线通知
						rabbitMQService.sendUserOnLineMessage(userId);
					}else {
						redisUtils.hPut(hashKey, userId, ++connectionCount);
					}
				}
			}
		}
	}

	private void doConnectionClosed(Map<String, Object> headers) {
		String pid = (String)headers.get("pid");
		if(pid != null){
			String userId = (String) redisUtils.hGet(USER_ONLINE_RELATION, pid);
			String companyId = WebUtils.getCurrCompanyId();
			if(userId != null && companyId!=null){
				User user = AppBeanInjector.userService.findById(userId);
				if(null != user){
					String hashKey = USER_ONLINE.concat(companyId);
					Integer connectionCount = (Integer) redisUtils.hGet(hashKey, userId);
					if(connectionCount != null){
						if(connectionCount <= 1){
							redisUtils.hDel(hashKey, userId);
							//发送用户下线通知
							rabbitMQService.sendUserOffLineMessage(userId);
						}else {
							redisUtils.hPut(hashKey, userId, --connectionCount);
						}
					}
				}
			}
			redisUtils.hDel(USER_ONLINE_RELATION, pid);
		}
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
				if (ack){
					logger.debug("rabbitmq message send success .....");
				}else {
					logger.debug("rabbitmq message send fail .....");
				}
			}
		);
		return rabbitTemplate;
	}

	/**
	 * spring的ramqpadmin在系统启动时会自动get出exchange,queue的bean，将他们在rabbitmq中注册。
	 * @return
	 */
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}


	/**
	 * 系统默认 exchange
	 * @return
	 */
	@Bean
	public TopicExchange defaultExchange() {
		return new TopicExchange(LWXF_DEFAULT_EXCHANGE_PREFIX + 0);
	}
}
