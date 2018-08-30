package com.lwxf.newstore.webapp.common.worker.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.worker.activity.base.AbstractActivityBuilder;

/**
 * 功能：
 *		默认日志处理
 * @author：zhaozhenyi(zhenyi.zhao@ihydt.com)
 * @create：2017-05-18 17:40:26
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
//@Component
public abstract class BaseActivityBuilder extends AbstractActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(BaseActivityBuilder.class);

	/**
	 * 生成事件名
	 * @param tsManagerEntity
	 * @return
	 */
	@Override
	protected String getEvent(TSManagerEntity tsManagerEntity){
		StringBuilder sb = new StringBuilder();
		sb.append(tsManagerEntity.getClazz().getSimpleName().toLowerCase());
		sb.append(":");

		SQLType sqlType = tsManagerEntity.getSqlType();
		if(sqlType == SQLType.INSERT){
			sb.append("create");
		}else if(sqlType == SQLType.UPDATE){
			sb.append("modify");
		}else {
			sb.append(sqlType.toString().toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 获取操作主体标识
	 * @param tsManagerEntity
	 * @return
	 */
	@Override
	protected String getR1(TSManagerEntity tsManagerEntity){
		Object object = tsManagerEntity.getMapperParams();
		return getId(object);
	}


	/**
	 * 获取额外参数
	 * @param tsManagerEntity
	 * @return
	 */
	@Override
	protected String getR2(TSManagerEntity tsManagerEntity){
		return null;
	}

	/**
	 * 获取补充数据
	 * @param tsManagerEntity
	 * @return
	 */
	@Override
	protected String getR3(TSManagerEntity tsManagerEntity){
		return JsonUtil.toJson(tsManagerEntity.getMapperParams());
	}

}
