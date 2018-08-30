package com.lwxf.newstore.webapp.bizservice.user.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.apache.commons.collections4.CollectionUtils;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.MQEventEnum;
import com.lwxf.newstore.webapp.common.enums.MainAccountType;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dao.user.UserThirdInfoDao;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;


@Service("userThirdInfoService")
public class UserThirdInfoServiceImpl implements UserThirdInfoService {
	private Logger logger = LoggerFactory.getLogger(UserThirdInfoServiceImpl.class);

	@Resource(name = "userThirdInfoDao")
	private UserThirdInfoDao userThirdInfoDao;
	@Resource
	private UserService userService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UserThirdInfo> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.userThirdInfoDao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public void bindingWeixin(String orgUserId, UserThirdInfo weixinInfo){
		bindingWeixinTmp(orgUserId, weixinInfo);
	}

	private void bindingWeixinTmp(String orgUserId, UserThirdInfo weixinInfo) {
		User user = userService.findById(weixinInfo.getUserId());
		if(user == null){
			WeixinUtils.sendErrorMsgToUser(MQEventEnum.USER_WX_BOUND_ERROR, ErrorCodes.BIZ_USER_NOT_FOUND_10002, "绑定用户不存在",weixinInfo.getUserId());
			return;
		}

		//查询微信绑定情况
		//注：此处判断换成unionid判断，因为网页注册用户的openid此时并未写入数据库，但此时不应该允许绑定其他账号
		if(LwxfStringUtils.isBlank(weixinInfo.getWxUnionId())){
			logger.error("此微信公众账号未绑定微信开放平台账号");
			return;
		}
		List<UserThirdInfo> userThirdInfoList = userThirdInfoDao.selectByWxUnionId(weixinInfo.getWxUnionId());
		if(!userThirdInfoList.isEmpty()){
			UserThirdInfo bindUserInfo = null;
			for (UserThirdInfo info : userThirdInfoList){
				if(info.getWxIsBind()){
					bindUserInfo = info;
					break;
				}
			}

			if(bindUserInfo != null){
				if(!bindUserInfo.getUserId().equals(weixinInfo.getUserId())){
					MQEventEnum eventEnum = MQEventEnum.USER_WX_BOUND_OTHER_ERROR;
					WeixinUtils.sendErrorMsgToUser(eventEnum, ErrorCodes.BIZ_MB_WEIXIN_BOUND_10026, "您的微信账号已与其他账号绑定", weixinInfo.getUserId());
					return;
				}
			}
		}

		UserThirdInfo userThirdInfo = userThirdInfoDao.selectByUserId(user.getId());

		//用户未绑定第三方账号
		if(userThirdInfo == null){
			//插入保存
			weixinInfo.setWxIsSubscribe(Boolean.TRUE);
			weixinInfo.setWxIsBind(Boolean.TRUE);
			weixinInfo.setEmailIsBind(Boolean.FALSE);
			weixinInfo.setMobileIsBind(Boolean.FALSE);
			this.userThirdInfoDao.insert(weixinInfo);
		}else {
			//更新微信绑定信息
			MapContext mapContext = MapContext.newOne();
			mapContext.put("userId", user.getId());
			mapContext.put("wxOpenId", weixinInfo.getWxOpenId());
			mapContext.put("wxUnionId", weixinInfo.getWxUnionId());
			mapContext.put("wxIsSubscribe", Boolean.TRUE);
			mapContext.put("wxIsBind", Boolean.TRUE);
			mapContext.put("wxNickname", weixinInfo.getWxNickname());

			userThirdInfoDao.updateByMapContext(mapContext);
		}
	}

	@Override
	public void unSubscribeByWxOpenId(String wxOpenId) {
		//关闭消息通知
		List<UserThirdInfo> userThirdInfoList = userThirdInfoDao.selectByWxOpenId(wxOpenId);
		if(CollectionUtils.isEmpty(userThirdInfoList)){
			return;
		}
		UserThirdInfo thirdInfo = userThirdInfoList.get(0);
		String userId = thirdInfo.getUserId();
		User user = userService.findById(userId);
		//判断是否是店主取消关注  如果是则只取消微信的绑定状态
		MapContext  mapContext = MapContext.newOne();
		mapContext.put("wxIsBind",Boolean.FALSE);
		//更新关注状态
		mapContext.put("wxOpenId", wxOpenId);
		mapContext.put("wxIsSubscribe", Boolean.FALSE);
		this.userThirdInfoDao.updateByWxOpenId(mapContext);
		int userRole = user.getRole().intValue();

		if(userRole==UserRole.SHOPKEEPER.getValue()){
			return;
		}


		mapContext = MapContext.newOne();
		// 更新用户的状态为禁用
		if(userRole != UserRole.MEMBER.getValue()){
			mapContext.put(WebConstant.KEY_ENTITY_ROLE,UserRole.MEMBER.getValue());
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,thirdInfo.getUserId());
		mapContext.put(WebConstant.KEY_ENTITY_STATE,UserState.DISABLED.getValue());

		// 整理发送微信消息的数据
		Map<String,Object> userLeaveData = new HashMap<>();
		userLeaveData.put("user",user);
		userLeaveData.put("tousers",this.userThirdInfoDao.findAllClerks());
		WebUtils.putDataToRequestMap(WebConstant.WX_TEMPLATE_MSG_USER_LEAVE_DATA,userLeaveData);

		this.userService.updateByMapContext(mapContext);
	}

	@Override
	public void unbindWeixinByUserId(String userId) {
		WebUtils.putDataToRequestMap("WX_UNBIND_BY_USERID", userId);
		//更新微信状态
		MapContext mapContext = MapContext.newOne();
		mapContext.put("userId", userId);
		mapContext.put("wxIsBind", Boolean.FALSE);
		mapContext.put("wxIsSubscribe", Boolean.FALSE);

		mapContext.put("wxOpenId", null);
		mapContext.put("wxUnionId", null);
		mapContext.put("wxNickname", null);

		this.userThirdInfoDao.updateByMapContext(mapContext);
	}

	@Override
	public List<UserThirdInfo> findByWxOpenId(String wxOpenId) {
		return this.userThirdInfoDao.selectByWxOpenId(wxOpenId);
	}

	@Override
	public List<UserThirdInfo> findByWxUnionId(String wxUnionId) {
		return this.userThirdInfoDao.selectByWxUnionId(wxUnionId);
	}

	@Override
	public UserThirdInfo findByUserId(String userId) {
		return this.userThirdInfoDao.selectById(userId);
	}

	@Override
	public List<UserThirdInfo> findAllWithNotBlankWxOpenId() {
		return userThirdInfoDao.findAllWithNotBlankWxOpenId();
	}

	@Override
	public List<UserThirdInfo> findByUserIds(List<String> userIdList) {
		return userThirdInfoDao.findByUserIds(userIdList);
	}

	@Override
	public void add(UserThirdInfo userThirdInfo) {
		userThirdInfoDao.insert(userThirdInfo);
	}

	@Override
	public int updateByMapContext(MapContext saveMap) {
		return userThirdInfoDao.updateByMapContext(saveMap);
	}

	@Override
	public void updateByWxOpenId(MapContext mapContext) {
		userThirdInfoDao.updateByWxOpenId(mapContext);
	}

	public  List<UserThirdInfo> findClerks(List<String> strList){
		return userThirdInfoDao.findByUserIds(strList);
	};

	public List<UserThirdInfo> findAllClerks(){
		return userThirdInfoDao.findAllClerks();
	}

	@Override
	public UserThirdInfo findByOpenId(String wxOpenId) {
		return this.userThirdInfoDao.selectByOpenId(wxOpenId);
	}

	@Override
	public void deleteByOppenId(String wxOppenId) {
		 userThirdInfoDao.deleteByOpenId(wxOppenId);
	}
}