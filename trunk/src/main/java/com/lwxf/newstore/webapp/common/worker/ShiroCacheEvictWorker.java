package com.lwxf.newstore.webapp.common.worker;

import javax.annotation.Resource;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.apache.commons.collections4.CollectionUtils;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterCommitEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeUpdateEventListener;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.shiro.LwxfShiroRealm;
import com.lwxf.newstore.webapp.common.utils.LwxfAssert;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

import static com.lwxf.newstore.webapp.common.utils.WebUtils.getDataFromRequestMap;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 19:05:28
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("shiroCacheEvictWorker")
public class ShiroCacheEvictWorker extends AbstractAutowireWorker implements BeforeUpdateEventListener, AfterCommitEventListener {
	private static final String BE_EVICT_SHIRO_CACHE_USERS = "be_evict_shiro_cache_users";
	private static Logger logger = LoggerFactory.getLogger(ShiroCacheEvictWorker.class);
	private static Map<Class, IEvictCacheHandler> handlerMap = new HashMap<Class, IEvictCacheHandler>() {{
		/*put(Org.class, new OrgEvictCacheHandler());
		put(OrgUser.class, new OrgUserEvictCacheHandler());
		put(Project.class, new ProjectEvictCacheHandler());
		put(ProjectUser.class, new ProjectUserEvictCacheHandler());
		put(ApiRap.class, new ApiRapEvictCacheHandler());
		put(ApiMember.class, new ApiMemberEvictCacheHandler());
		put(OrgIpWhitelist.class, new OrgIpWhitelistEvictCacheHandler());
		put(OrgLicence.class, new OrgLicenceEvictCacheHandler());
		put(OrgMemberWhitelist.class, new OrgMemberWhitelistEvictCacheHandler());*/
	}};
	@Resource(name = "shiroRealm")
	private LwxfShiroRealm shiroRealm;

	@Override
	public void beforeUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		TSManualData tsManualData = (TSManualData) getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		IEvictCacheHandler handler = handlerMap.get(null == tsManualData ? tsManagerEntity.getClazz() : tsManualData.getClazz());
		if (null == handler) {
			return;
		}

		Object params;
		if (null == tsManualData) {
			params = tsManagerEntity.getMapperParams();
		} else {
			params = tsManualData.getParams();
			sqlType = SystemActivityEvent.parseEventToSQLType(tsManualData.getEvent());
		}

		if (sqlType == SQLType.INSERT) {
			//新增组织时候不组装该消息
			if (WebUtils.requestMapContainsKey(WebConstant.MQ_ORG_ADD_IGNORE)) {
				return;
			}
			handler.doAdd(this.getParamsForInsertOrDelete(params));
		} else if (sqlType == SQLType.UPDATE) {
			handler.doUpdate(params);
		} else if (sqlType == SQLType.DELETE) {
			handler.doDelete(this.getParamsForInsertOrDelete(params));
		} else {
			LwxfAssert.isTrue(false, "这是数据的读操作，请检查代码");
		}
	}

	@Override
	public void afterCommit(List<TSManagerEntity> tsManagerEntitys) {
		Set<String> users = (Set<String>) getDataFromRequestMap(BE_EVICT_SHIRO_CACHE_USERS);
		if (CollectionUtils.isNotEmpty(users)) {
			users.stream().forEach((userId) -> {
				shiroRealm.clearCachedAuthorizationInfo(userId);
			});
		}
		// 处理由于禁用组织用户而产生的新的项目负责人
		/*Set<ProjectUser> newProjectAssignees = (Set<ProjectUser>) getDataFromRequestMap(WebConstant.KEY_SHIRO_ORG_DISABLE_USER_PROJECTNEWASSIGNS);
		if(CollectionUtils.isNotEmpty(newProjectAssignees)){
			newProjectAssignees.stream().forEach(pu->{
				shiroRealm.clearCachedAuthorizationInfo(pu.getUserId());
			});
		}*/
	}

	private Object getParamsForInsertOrDelete(Object params) {
		if (params instanceof Map) {
			Map map = (Map)params;
			if(map.containsKey(WebConstant.KEY_COMMON_LIST)){
				return ((Map) params).get(WebConstant.KEY_COMMON_LIST);
			}
		}
		return params;
	}

	interface IEvictCacheHandler {
		void doAdd(Object params);

		void doUpdate(Object params);

		void doDelete(Object params);

		default Set<String> getBeEvictUsers() {
			Set<String> users = (Set<String>) getDataFromRequestMap(BE_EVICT_SHIRO_CACHE_USERS);
			if (null == users) {
				users = new HashSet<>();
				WebUtils.putDataToRequestMap(BE_EVICT_SHIRO_CACHE_USERS, users);
			}
			return users;
		}

		default String getUpdateId(Map update, String key) {
			if (null == key) {
				key = "id";
			}
			Object idObj = update.get(key);
			if (null == idObj) {
				return null;
			}
			return idObj.toString();
		}

		/*default void doOrgUserList(List<OrgUser> ous) {
			if (CollectionUtils.isNotEmpty(ous)) {
				ous.stream().forEach((ou) -> {
					if (ou.getState().equals(OrgUserState.ACTIVE.getValue())) {
						this.getBeEvictUsers().add(ou.getUserId());
					}
				});
			}
		}*/

		/*default void doProjectUserList(List<ProjectUser> pus) {
			if (CollectionUtils.isNotEmpty(pus)) {
				pus.stream().forEach((pu) -> {
					if (pu.getState().equals(ProjectUserState.ACTIVE.getValue())) {
						this.getBeEvictUsers().add(pu.getUserId());
					}
				});
			}
		}*/
	}
}