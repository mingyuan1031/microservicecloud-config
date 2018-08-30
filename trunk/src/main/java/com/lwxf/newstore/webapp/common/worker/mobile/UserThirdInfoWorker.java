package com.lwxf.newstore.webapp.common.worker.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lwxf.newstore.webapp.common.mobile.weixin.template.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageEntity;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageInfo;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageScope;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterCommitEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeUpdateEventListener;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.MQEventEnum;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.mobile.weixin.service.IMsgService;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

import static com.lwxf.newstore.webapp.common.utils.WebUtils.getDataFromRequestMap;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:35:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Component("userThirdInfoWorker")
public class UserThirdInfoWorker extends AbstractAutowireWorker implements AfterCommitEventListener, BeforeUpdateEventListener {
	private static final String DATA_CACHE_KEY_WX_USER_THIRD_INFO = "wx_user_third_info";
	@Resource(name = "weixinTemplateMsgService")
	private IMsgService templateMsgService;
	@Resource
	private UserThirdInfoService userThirdInfoService;
	@Resource(name = "companyService")
	private CompanyService companyService;

	@Override
	public void beforeUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		if (null == tsManagerEntity) {
			return;
		}

		if (tsManagerEntity.getClazz() != UserThirdInfo.class) {
			return;
		}

		if (getDataFromRequestMap("WX_UNBIND_BY_USERID") != null) {
			UserThirdInfo userThirdInfo = userThirdInfoService.findByUserId(getDataFromRequestMap("WX_UNBIND_BY_USERID").toString());
			WebUtils.putDataToRequestMap(DATA_CACHE_KEY_WX_USER_THIRD_INFO, userThirdInfo);
		}
	}

	@Override
	public void afterCommit(List<TSManagerEntity> tsManagerEntitys) { if (CollectionUtils.isEmpty(tsManagerEntitys)) {
			return;
		}

		for (TSManagerEntity tSManagerEntity : tsManagerEntitys) {
			if (tSManagerEntity.getClazz() != UserThirdInfo.class) {
				continue;
			}

			SQLType sqlType = tSManagerEntity.getSqlType();
			if (sqlType == SQLType.INSERT) {
				UserThirdInfo thirdInfo = (UserThirdInfo) tSManagerEntity.getMapperParams();
				User user = AppBeanInjector.userService.findById(thirdInfo.getUserId());
				MessageEntity<MessageInfo> messageEntity = MessageEntity.newOne(MessageScope.USER, thirdInfo.getUserId(), MQEventEnum.USER_THIRDINFO);
				messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.getAdd().add(thirdInfo);
				messageEntity.setData(messageInfo);
				// AppBeanInjector.rabbitMQSender.sendMessage(true, messageEntity); TODO：

				WeixinBaseTemplateMsg weixinMsg;
				if(user.getRole().intValue() == UserRole.MEMBER.getValue()){ // 普通用户注册
					weixinMsg= new RegisterSuccessMsg();
					weixinMsg.setUserInfo(user);
					// 通知所有店员
					List<UserThirdInfo> toUsers = this.userThirdInfoService.findAllClerks(); // TODO：需要查询出所有店员的（用户状态是正常的）
					String openId;
					for (UserThirdInfo third : toUsers){
						openId = third.getWxOpenId();
						if(LwxfStringUtils.isNotBlank(openId)){
							weixinMsg.setTouser(openId);
							this.templateMsgService.pushMsg(weixinMsg);
						}
					}
				}else{ // 申请为店员的通知
					Company company = this.companyService.findById(WebUtils.getCurrCompanyId());
					weixinMsg = new ApplyClerkSuccessMsg(company.getName());
					weixinMsg.setUserInfo(user);
					weixinMsg.setTouser(thirdInfo.getWxOpenId());
					this.templateMsgService.pushMsg(weixinMsg);
				}
			} else if (sqlType == SQLType.UPDATE) {
				Map paramMap = (Map) tSManagerEntity.getMapperParams();

				//绑定
				if (paramMap.containsKey("wxIsBind") && (Boolean) paramMap.get("wxIsBind")) {
					//同步消息
					UserThirdInfo thirdInfo = userThirdInfoService.findByUserId(paramMap.get("userId").toString());
					MessageEntity<MessageInfo> messageEntity = MessageEntity.newOne(MessageScope.USER, thirdInfo.getUserId(), MQEventEnum.USER_THIRDINFO);
					messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.getAdd().add(thirdInfo);
					messageEntity.setData(messageInfo);
					// AppBeanInjector.rabbitMQSender.sendMessage(true, messageEntity); TODO:

					//微信消息
					User user = AppBeanInjector.userService.findById(thirdInfo.getUserId());
					WeixinBaseTemplateMsg weixinMsg = new BindShopkeeperMsg();
					if(user.getRole().intValue() == UserRole.SHOPKEEPER.getValue()){
						weixinMsg.setTouser(thirdInfo.getWxOpenId());
						weixinMsg.setUserInfo(user);
						this.templateMsgService.pushMsg(weixinMsg);
					}else{
						List<UserThirdInfo> toUsers = this.userThirdInfoService.findAllClerks();
						weixinMsg = new RegisterSuccessMsg();
						weixinMsg.setUserInfo(user);
						for (UserThirdInfo toUser:toUsers){
							weixinMsg.setTouser(toUser.getWxOpenId());
							this.templateMsgService.pushMsg(weixinMsg);
						}
					}
				}

				//个人中心解绑
				if (paramMap.containsKey("wxIsBind") && (Boolean) paramMap.get("wxIsBind") == false) {
					//解绑微信通知
					/*UserThirdInfo userThirdInfo = (UserThirdInfo) WebUtils.getDataFromRequestMap(DATA_CACHE_KEY_WX_USER_THIRD_INFO);
					if(!LwxfStringUtils.isBlank(userThirdInfo.getWxOpenId()) && userThirdInfo.getWxIsBind()){
						UserThirdInfo thirdInfo = userThirdInfoService.findByUserId(paramMap.get("userId").toString());
						UserLeaveMsg weixinMsg = new UserLeaveMsg();
						List<UserThirdInfo> toUsers = this.userThirdInfoService.findAllClerks();
						User user = AppBeanInjector.userService.findById(thirdInfo.getUserId());
						weixinMsg.setUserInfo(user);
						for (UserThirdInfo toUser:toUsers){
							weixinMsg.setTouser(toUser.getWxOpenId());
							this.templateMsgService.pushMsg(weixinMsg);
						}
					}*/

					//同步消息
					String userId = (String) paramMap.get("userId");
					MessageEntity<MessageInfo> messageEntity = MessageEntity.newOne(MessageScope.USER, userId, MQEventEnum.USER_THIRDINFO);
					messageEntity.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.getDelete().add(userId);
					messageEntity.setData(messageInfo);
					// AppBeanInjector.rabbitMQSender.sendMessage(true, messageEntity); TODO:
				}
			}
		}
	}
}
