package com.lwxf.newstore.webapp.common.worker.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterCommitEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeUpdateEventListener;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 19:05:28
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("updateCurrUserSessionWorker")
public class UpdateCurrUserSessionWorker extends AbstractAutowireWorker implements BeforeUpdateEventListener, AfterCommitEventListener {
	private static final String UPDATE_CURR_USER_INFO_TO_SESSION = "update_curr_user_info_to_session";

	@Override
	public void beforeUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);

		Class clazz;
		Object params;
		if (null == tsManualData) {
			clazz = tsManagerEntity.getClazz();
			params = tsManagerEntity.getMapperParams();
		} else {
			clazz = tsManualData.getClazz();
			params = tsManualData.getParams();
		}
		if(clazz != User.class){
			return;
		}

		if(null != tsManualData){
			sqlType = SystemActivityEvent.parseEventToSQLType(tsManualData.getEvent());
		}

		if (sqlType == SQLType.UPDATE) {
			Object idObj = ((Map)params).get(WebConstant.KEY_ENTITY_ID);
			if (null == idObj) {
				return ;
			}
			if(idObj.toString().equals(WebUtils.getCurrUserId())){
				WebUtils.putDataToRequestMap(UPDATE_CURR_USER_INFO_TO_SESSION,Boolean.TRUE);
			}
		}
	}

	@Override
	public void afterCommit(List<TSManagerEntity> tsManagerEntitys) {
		Boolean isChange = (Boolean) WebUtils.getDataFromRequestMap(UPDATE_CURR_USER_INFO_TO_SESSION);
		if(Boolean.TRUE.equals(isChange)){
			HttpSession session = WebUtils.getHttpSession();
			if(null != session){
				session.removeAttribute(WebConstant.SESSION_KEY_CURR_USER);
			}
		}
	}
}