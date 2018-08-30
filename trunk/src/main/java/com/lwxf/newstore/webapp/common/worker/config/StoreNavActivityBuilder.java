package com.lwxf.newstore.webapp.common.worker.config;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.config.StoreHomeNavService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.cfgs.NavStatus;
import com.lwxf.newstore.webapp.common.enums.reservation.ReservationStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/23 14:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class StoreNavActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "storeHomeNavService")
	private StoreHomeNavService storeHomeNavService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(StoreHomeNav.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		StoreNavResEntity navResEntity = new StoreNavResEntity();
		Map<String, Object> attrMap;
		StoreHomeNav storeHomeNav;
		String id;
		switch (sqlType){
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
				    return null;
				}
				storeHomeNav = this.storeHomeNavService.findById(id);
				systemActivity.setR1(id);
				navResEntity.setName(storeHomeNav.getName());
				navResEntity.setUrl(storeHomeNav.getUrl());
				Object status = paramsMap.get("status");
				if(status != null){
					systemActivity.setEvent(SystemActivityEvent.NAV_STATUS_MD.getValue());
					Byte statsValue = Byte.valueOf(status.toString());

					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,NavStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				}else {
					systemActivity.setEvent(SystemActivityEvent.NAV_MD.getValue());
					attrMap = new HashMap<>();
					paramsMap.forEach((key,value) ->{
						if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_STATUS)){
							return;
						}
						attrMap.put(key,value);
					});
					activityInfoEntity.setAttr(attrMap);
				}
				break;
		}
		activityInfoEntity.setRes(navResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		return null;
	}
}
