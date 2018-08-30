package com.lwxf.newstore.webapp.common.worker.mq.base;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.worker.RabbitMQWorker;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:21:25
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class AbstractMQBuilder implements MQBuilder {

	@Resource
	protected RabbitMQWorker rabbitMQWorker;

	@PostConstruct
	protected void init() {
		registerToWorker();
	}

	public abstract void registerToWorker();

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		return null;
	}


	@Override
	public Object build(TSManualData tsManualData) {
		return null;
	}

	@Override
	public void doBefore(TSManualData tsManualData) {

	}

	@Override
	public void doBefore(TSManagerEntity tsManagerEntity, SQLType sqlType) {

	}
}
