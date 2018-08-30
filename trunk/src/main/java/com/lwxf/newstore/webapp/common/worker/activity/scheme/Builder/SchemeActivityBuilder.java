package com.lwxf.newstore.webapp.common.worker.activity.scheme.Builder;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/2 16:39
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SchemeActivityBuilder extends BaseActivityBuilder {
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Scheme.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		return super.build(tsManagerEntity, sqlType);
	}

	@Override
	public Object build(TSManualData tsManualData) {
		return super.build(tsManualData);
	}
}
