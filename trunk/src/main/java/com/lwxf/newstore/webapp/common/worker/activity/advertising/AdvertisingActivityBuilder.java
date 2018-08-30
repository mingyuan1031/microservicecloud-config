package com.lwxf.newstore.webapp.common.worker.activity.advertising;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.advertising.AdvertisingService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.advertising.AdvertisingLinkStart;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/20 16:11
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class AdvertisingActivityBuilder extends BaseActivityBuilder {
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "advertisingService")
	private AdvertisingService advertisingService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Advertising.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		AdvertisingResEntity advertisingResEntity = new AdvertisingResEntity();
		Map<String, Object> attrMap;
		Advertising advertising;
		String id;
		switch (sqlType) {
			case INSERT:
				advertising = (Advertising) params;
				systemActivity.setEvent(SystemActivityEvent.ADVERTISING_CREATE.getValue());
				systemActivity.setR1(advertising.getId());
				advertisingResEntity.setName(advertising.getAdvName());
				advertisingResEntity.setPath(advertising.getPath());
				advertisingResEntity.setLink(advertising.getLink());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if (null == id) {
					return null;
				}

				advertising = this.advertisingService.findById(id);
				systemActivity.setEvent(SystemActivityEvent.ADVERTISING_DELETE.getValue());
				systemActivity.setR1(id);
				advertisingResEntity = new AdvertisingResEntity();
					advertisingResEntity.setName(advertising.getAdvName());
				if (paramsMap.get("path") != null) {
					advertisingResEntity.setPath(advertising.getPath());
				}
				if (paramsMap.get("link") != null) {
					advertisingResEntity.setLink(advertising.getLink());
				}
				if (paramsMap.get("place") != null) {
					advertisingResEntity.setPlace(advertising.getPlace());
				}
				if (paramsMap.get("linkType") != null) {
					advertisingResEntity.setLinkType(advertising.getLinkType());
				}
				Object status = paramsMap.get("linkStart");
				if (status != null) {
					systemActivity.setEvent(SystemActivityEvent.ADVERTISING_LINKSTART_MD.getValue());
					int statsValue = Integer.valueOf(status.toString());
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS, statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME, AdvertisingLinkStart.getByValue((byte)statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
				systemActivity.setEvent(SystemActivityEvent.ADVERTISING_LINKSTART_MD.getValue());
				attrMap = new HashMap<>();
				paramsMap.forEach((key, value) -> {
					if (key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_STATUS)) {
						return;
					}
					attrMap.put(key, value);
				});
				activityInfoEntity.setAttr(paramsMap);
			}
				break;
			case DELETE:
				id = (String) tsManagerEntity.getMapperParams();
				advertising = this.advertisingService.findById(id);
				systemActivity.setEvent(SystemActivityEvent.ADVERTISING_DELETE.getValue());
				systemActivity.setR1(id);
				advertisingResEntity = new AdvertisingResEntity();
				advertisingResEntity.setName(advertising.getAdvName());
				advertisingResEntity.setPath(advertising.getPath());
				advertisingResEntity.setLink(advertising.getLink());
				break;
		}
			activityInfoEntity.setRes(advertisingResEntity);
			systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
			return systemActivity;

	}
		@Override
		public Object build (TSManualData tsManualData){
			return super.build(tsManualData);
		}
}
