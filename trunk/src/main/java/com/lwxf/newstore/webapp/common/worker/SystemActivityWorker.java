package com.lwxf.newstore.webapp.common.worker;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterUpdateEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeCommitEventListener;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.BeforeUpdateEventListener;
import com.lwxf.newstore.webapp.bizservice.sys.SystemActivityService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityScope;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.base.IActivityBuilder;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-02 19:36
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("systemActivityWorker")
public class SystemActivityWorker extends AbstractAutowireWorker implements BeforeUpdateEventListener,AfterUpdateEventListener, BeforeCommitEventListener {
	private static final String SYSTEM_ACTIVITY = "SystemActivity";
	private static Logger logger = LoggerFactory.getLogger(SystemActivityWorker.class);
	private Map<Class, IActivityBuilder> registerLogs = new HashMap<>();

	@Resource(name = "systemActivityService")
	private SystemActivityService systemActivityService;

	/**
	 * 注册
	 * @param clazz
	 * @param systemActivityBuilder
	 */
	public void register(Class clazz, IActivityBuilder systemActivityBuilder){
		if(registerLogs.containsKey(clazz)){
			Assert.isTrue(false, "class: "+ clazz.getName() +" 重复注册，请检查代码");
		}
		this.registerLogs.put(clazz, systemActivityBuilder);
	}


	@Override
	public void beforeUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		List<SystemActivity> list = new ArrayList();

		//线程放置数据
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		if(tsManualData != null && tsManualData.getBeforeCommitExecuted() == false){
			IActivityBuilder builder = registerLogs.get(tsManualData.getClazz());
			if(builder != null){
				Object object = builder.build(tsManualData);
				addSystemActivity(list, object);
			}
		}

		//默认处理
		if(tsManagerEntity != null){
			IActivityBuilder builder = registerLogs.get(tsManagerEntity.getClazz());
			if(builder != null){//有监听
				Object object = builder.build(tsManagerEntity, sqlType);
				addSystemActivity(list, object);
			}
		}

		//放入线程
		if(list.size() > 0){
			List<SystemActivity> activitys = (List<SystemActivity>)WebUtils.getDataFromRequestMap(SYSTEM_ACTIVITY);
			if(activitys == null){
				activitys = new ArrayList();
				WebUtils.putDataToRequestMap(SYSTEM_ACTIVITY, activitys);
			}

			for (SystemActivity systemActivity : list){
				systemActivity.setId("uninitialized");
				activitys.add(systemActivity);
			}
		}
	}

	@Override
	public void afterUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		//数据库影响行数为0且产生日志对象时,需要删除增加的日志
		List<SystemActivity> list = (List<SystemActivity>)WebUtils.getDataFromRequestMap(SYSTEM_ACTIVITY);
		if(list != null && list.size() > 0) {
			Iterator<SystemActivity> iterator = list.iterator();
			while (iterator.hasNext()) {
				SystemActivity systemActivity = iterator.next();
				if ("uninitialized".equals(systemActivity.getId())) {
					if (tsManagerEntity != null) {//非埋点
						if (tsManagerEntity.getAffectedRows() > 0) {//操作成功
							systemActivity.setId(null);
						} else {//操作失败
							iterator.remove();
						}
					} else {
						systemActivity.setId(null);
					}
				}
			}
		}
	}

	@Override
	public void beforeCommit(List<TSManagerEntity> tsManagerEntitys) {
		List<SystemActivity> list = (List<SystemActivity>) WebUtils.getDataFromRequestMap(SYSTEM_ACTIVITY);

		//处理特殊事件
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		if(tsManualData != null && tsManualData.getBeforeCommitExecuted()){
			IActivityBuilder builder = registerLogs.get(tsManualData.getClazz());
			if(builder != null){
				Object object = builder.build(tsManualData);
				if(list == null){
					list = new ArrayList<>();
				}
				addSystemActivity(list, object);
			}
		}

		if(list != null && list.size() > 0){
			Date now = new Date();
			for (SystemActivity systemActivity : list) {
				// 活动日志新增加作用范围，为了节省时间，在这里统一处理
				if(systemActivity.getScope()==null) {
					if (systemActivity.getEvent().startsWith("company:")) {
						systemActivity.setScope(SystemActivityScope.COMPANY.getValue());
					} else if (systemActivity.getEvent().startsWith("syscfg:")) {
						systemActivity.setScope(SystemActivityScope.CONFIG_SYS.getValue());
					} else if (systemActivity.getEvent().startsWith("storecfg:")) {
						systemActivity.setScope(SystemActivityScope.CONFIG_STORE.getValue());
					} else if (systemActivity.getEvent().startsWith("reservation:")) {
						systemActivity.setScope(SystemActivityScope.RESERVATION.getValue());
					}else if (systemActivity.getEvent().startsWith("member:")) {
						systemActivity.setScope(SystemActivityScope.MEMBER.getValue());
					}else if (systemActivity.getEvent().startsWith("user:")) {
						systemActivity.setScope(SystemActivityScope.USER.getValue());
					}else if(systemActivity.getEvent().startsWith("cart:")){
					    systemActivity.setScope(SystemActivityScope.CART.getValue());
					}else if(systemActivity.getEvent().startsWith("nav:")){
					    systemActivity.setScope(SystemActivityScope.STORE_NAV.getValue());
					}else if(systemActivity.getEvent().startsWith("brand:")){
						systemActivity.setScope(SystemActivityScope.BRAND.getValue());
					}else if(systemActivity.getEvent().startsWith("goods:")){
						systemActivity.setScope(SystemActivityScope.GOODS.getValue());
					}else if(systemActivity.getEvent().startsWith("tag:")){
						systemActivity.setScope(SystemActivityScope.TAG.getValue());
					}else if(systemActivity.getEvent().startsWith("goodstype:")){
						systemActivity.setScope(SystemActivityScope.GOODSTYPE.getValue());
					}else if(systemActivity.getEvent().startsWith("goodscomment:")){
						systemActivity.setScope(SystemActivityScope.GOODS_COMMENT.getValue());
					}else if(systemActivity.getEvent().startsWith("quickshare:")){
						systemActivity.setScope(SystemActivityScope.QUICKSHARE.getValue());
					}else if(systemActivity.getEvent().startsWith("quickshare_comment:")){
						systemActivity.setScope(SystemActivityScope.QUICKSHARE_COMMENT.getValue());
					}else if(systemActivity.getEvent().startsWith("quickshare_praise:")){
						systemActivity.setScope(SystemActivityScope.QUICKSHARE_PRAISE.getValue());
					}else if(systemActivity.getEvent().startsWith("advertising:")){
						systemActivity.setScope(SystemActivityScope.ADVERTISING.getValue());
					}else if (systemActivity.getEvent().startsWith("clerk:")) {
						systemActivity.setScope(SystemActivityScope.CLERK.getValue());
					}else if (systemActivity.getEvent().startsWith("address:")){
						systemActivity.setScope(SystemActivityScope.ADDRESS.getValue());
					}else if(systemActivity.getEvent().startsWith("order:")){
						systemActivity.setScope(SystemActivityScope.ORDER.getValue());
					}else if(systemActivity.getEvent().startsWith("ordergoods:")){
						systemActivity.setScope(SystemActivityScope.ORDERGOODS.getValue());
					}else if(systemActivity.getEvent().startsWith("logistics:")){
					systemActivity.setScope(SystemActivityScope.LOGISTICS.getValue());
					}
				}
				systemActivity.setCreated(now);
				systemActivityService.add(systemActivity);
			}
		}
	}

	/**
	 * 封装数据
	 * @param list
	 * @param object
	 */
	private void addSystemActivity(List<SystemActivity> list, Object object){
		if(object != null){
			if(object instanceof SystemActivity){
				SystemActivity orgActivity = (SystemActivity) object;
				list.add(orgActivity);
			}else if(object instanceof List){
				list.addAll((List)object);
			}else {
				throw new RuntimeException("返回类型不支持");
			}
		}
	}
}
