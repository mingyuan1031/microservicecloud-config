package com.lwxf.newstore.webapp.controller.api.rabbitmq;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.config.RabbitMQConfig;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能： rabbitmq权限验证
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-26 15:39:39
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
public class AuthBackendHttpController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthBackendHttpController.class);
	Pattern pattern = Pattern.compile("^(O|D|F|U|OU)\\.([^/\\s]+)$");//匹配routingkey

	@Resource
	private RedisUtils redisUtils;

	/**
	 * @param userId
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/auth/user")
	public String user(@RequestParam("username") String userId,//userId
					   @RequestParam("password") String sessionId) {//X-PHPSESSID
		if(!AppBeanInjector.configuration.isOnProd()){//非生产环境
			return "allow";
		}
		LOGGER.info("Trying to authenticate user {}", userId);
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(sessionId)){
			return "deny";
		}
		String token = (String)redisUtils.get(WebConstant.MQ_TOKEN_PREFIX.concat(sessionId));
		if(token == null){
			return "deny";
		}
		String[] temp = userId.split("_");
		if(temp.length == 2 && token.equals(temp[0])){
			return "allow";
		}

		return "deny";
	}

	/*@RequestMapping("/auth/vhost")
	public String vhost(VirtualHostCheck check) {
		LOGGER.info("Checking vhost access with {}", check);
		if(AppBeanInjector.rabbitMQProperties.getVirtualHost().equals(check.getVhost())){
			return "allow";
		}
		return "deny";
	}*/

	@RequestMapping("/auth/resource")
	public String resource(ResourceCheck check, HttpServletRequest request) {
		LOGGER.info("Checking resource access with {}", check);
		if(!AppBeanInjector.configuration.isOnProd()){//非生产环境
			return "allow";
		}

		String resource = check.getResource();
		//topic的routingkey绑定验证
		if("routing_key".equals(resource)){
			String routingKey = check.getName();
			if(checkRoutingKey(check.getUsername(), routingKey)){
				return "allow";
			}
			return "deny";
		}else if("exchange".equals(resource)){//exchange验证
			if(check.getName() != null && check.getName().startsWith(RabbitMQConfig.LWXF_DEFAULT_EXCHANGE_PREFIX)){
				return "allow";
			}else{
				return "deny";
			}
		}
		return "allow";
	}


	/**
	 * mq 绑定校验
	 * @param userId
	 * @param routingKey
	 * @return
	 */
	private boolean checkRoutingKey(String userId, String routingKey) {
		String[] temp = userId.split("_");
		userId = temp[0];
		Matcher matcher = pattern.matcher(routingKey);
		boolean flag = Boolean.FALSE.booleanValue();
		if(matcher.matches()){
			String route = matcher.group(1);
			String key = matcher.group(2);
			switch (route){
				case "O" :
					 if(key.equals(AppBeanInjector.configuration.getCompanyId())){
					 	return Boolean.TRUE.booleanValue();
					 }
					break;
				case "U" :
					if(userId.equals(key)){//用户只能监听自己的
						return Boolean.TRUE.booleanValue();
					}
					break;
				default:
					break;
			}
		}
		return flag;
	}
}
