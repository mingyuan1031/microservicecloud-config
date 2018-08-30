package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.common.enums.MQEventEnum;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.config.RabbitMQConfig;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:53:27
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class RabbitMQService {

	/**
	 * 	发送上线通知
	 */
	public void sendUserOnLineMessage(String userId) {
		String companyId = WebUtils.getCurrCompanyId();
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.getAdd().add(userId);
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setEvent(MQEventEnum.USER_ONLINE.getValue());
		messageEntity.setScope(MessageScope.COMPANY);
		messageEntity.setScopeId(companyId);
		messageEntity.setCompanyId(companyId);
		messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
		messageEntity.setData(messageInfo);
		/*AppBeanInjector.rabbitMQSender.sendMessage(Boolean.FALSE, messageEntity);*/
	}

	/**
	 * 	发送下线通知
	 */
	public void sendUserOffLineMessage(String userId) {
		String companyId = WebUtils.getCurrCompanyId();
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.getDelete().add(userId);

		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setEvent(MQEventEnum.USER_ONLINE.getValue());
		messageEntity.setScope(MessageScope.COMPANY);
		messageEntity.setScopeId(companyId);
		messageEntity.setCompanyId(companyId);
		messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
		messageEntity.setData(messageInfo);
		messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
		/*AppBeanInjector.rabbitMQSender.sendMessage(Boolean.FALSE, messageEntity);*/
	}


	/**
	 * 获取组织所有在线用户:orgUserId
	 */
	public Set<String> getOnLineUsersByCompanyId(String companyId){
		Set<String> orgUserIdList = AppBeanInjector.redisUtils.hKeys(RabbitMQConfig.USER_ONLINE.concat(companyId));
		if(orgUserIdList == null || orgUserIdList.isEmpty()){
			return new HashSet<>(0);
		}
		return orgUserIdList;
	}
}
