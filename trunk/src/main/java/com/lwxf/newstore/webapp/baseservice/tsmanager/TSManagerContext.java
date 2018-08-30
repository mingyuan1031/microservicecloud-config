package com.lwxf.newstore.webapp.baseservice.tsmanager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.newstore.webapp.baseservice.tsmanager.base.*;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：拦截器和WorkHandler的容器上下文
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:41:12
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class TSManagerContext {
	public static final TSManagerContext instance = new TSManagerContext();
	private static final String BeforeUpdate = "BeforeUpdate";
	private static final String AfterUpdate = "AfterUpdate";

	private TSManagerContext(){}

	private List<BeforeUpdateEventListener> beforeUpdateEventListeners = new ArrayList<>();
	private List<AfterUpdateEventListener> afterUpdateEventListeners = new ArrayList<>();
	private List<BeforeCommitEventListener> beforeCommitEventListeners = new ArrayList<>();
	private List<AfterCommitEventListener> afterCommitEventListeners = new ArrayList<>();


	public void beforeUpdate(TSManagerEntity tsManagerEntity,SQLType sqlType) {
		if(isExecuted(TSManagerContext.BeforeUpdate)){
			return;
		}
		for (BeforeUpdateEventListener beforeUpdateEventListener : beforeUpdateEventListeners) {
			beforeUpdateEventListener.beforeUpdate(tsManagerEntity, sqlType);
		}
		changeExecuted(TSManagerContext.BeforeUpdate);
	}


	public void afterUpdate(TSManagerEntity tsManagerEntity,SQLType sqlType) {
		if(isExecuted(TSManagerContext.AfterUpdate)){
			return;
		}
		for (AfterUpdateEventListener postUpdateEventListener : afterUpdateEventListeners) {
			postUpdateEventListener.afterUpdate(tsManagerEntity, sqlType);
		}
		changeExecuted(TSManagerContext.AfterUpdate);
	}


	public void beforeCommit(List<TSManagerEntity> tsManagerEntitys) {
		for (BeforeCommitEventListener beforeCommitEventListener : beforeCommitEventListeners) {
			beforeCommitEventListener.beforeCommit(tsManagerEntitys);
		}
	}

	public void afterCommit(List<TSManagerEntity> tsManagerEntitys){
		for (AfterCommitEventListener afterCommitEventListener : afterCommitEventListeners) {
			afterCommitEventListener.afterCommit(tsManagerEntitys);
		}
	}

	/**
	 * 判断是否执行过，线程中存在标志的话，只允许执行一次
	 * @param action
	 * @return
	 */
	private boolean isExecuted(String action){
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		if(tsManualData != null){
			if(TSManagerContext.BeforeUpdate.equals(action)){
				return tsManualData.getPreExecuted();
			}
			return tsManualData.getPostExecuted();
		}
		return false;
	}

	/**
	 * 更新执行状态
	 * @param action
	 */
	private void changeExecuted(String action){
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		if(tsManualData != null){
			if(TSManagerContext.BeforeUpdate.equals(action)){
				tsManualData.setPreExecuted(true);
			}else{
				tsManualData.setPostExecuted(true);
			}
		}
	}

	/**
	 * 注册
	 * @param eventListener
	 */
	public void register(EventListener eventListener){

		if(eventListener instanceof BeforeUpdateEventListener){
			beforeUpdateEventListeners.add((BeforeUpdateEventListener)eventListener);
		}

		if(eventListener instanceof AfterUpdateEventListener){
			afterUpdateEventListeners.add((AfterUpdateEventListener)eventListener);
		}

		if(eventListener instanceof BeforeCommitEventListener){
			beforeCommitEventListeners.add((BeforeCommitEventListener)eventListener);
		}

		if(eventListener instanceof AfterCommitEventListener){
			afterCommitEventListeners.add((AfterCommitEventListener)eventListener);
		}

	}

}
