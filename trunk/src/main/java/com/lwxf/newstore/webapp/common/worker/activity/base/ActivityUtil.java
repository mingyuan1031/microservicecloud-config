package com.lwxf.newstore.webapp.common.worker.activity.base;

import java.util.HashMap;
import java.util.Map;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;

/**
 * 功能：日志操作便捷方法
 *
 * @author：dangdong(dangdong@ihydt.com)
 * @create：2017-05-27 16:39:52
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
public class ActivityUtil {
	/**
	 * 传入name，处理r3里面仅name的情况
	 *
	 * @param name
	 * @return
	 */
	public static String createR3(String name) {
		if (name == null) {
			name = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\"res\":{\"name\":\"")
				.append(name)
				.append("\"}}");
		return sb.toString();
	}

	/**
	 * 传入对象的json串,处理r3里面是对象的情况
	 *
	 * @param json
	 * @return
	 */
	@Deprecated
	public static String createR3ByObject(String json) {
		if (json == null) {
			json = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\"res\":{")
				.append(json)
				.append("\"}}");
		return sb.toString();
	}

	/**
	 * 根据传入name生成ResEntity
	 *
	 * @param name
	 * @return
	 */
	public static ResEntity createResEntityByName(String name) {
		ResEntity resEntity = new ResEntity();
		resEntity.setName(name);
		return resEntity;
	}

	/**
	 * 根据传入属性名， 值生成仅attr活动日志R3事件格式封装
	 *
	 * @param name
	 * @param mapContext
	 * @return
	 */
	public static ActivityInfoEntity createActivityInfoEntityByAttr(String name, MapContext mapContext) {
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Map map = new HashMap();
		map.put(name, mapContext.get(name));
		activityInfoEntity.setAttr(map);
		return activityInfoEntity;
	}

	/**
	 * Attr,name
	 *
	 * @param key
	 * @param mapContext
	 * @return
	 */
	public static ActivityInfoEntity createActivityInfoEntityByAttrContainName(String key, MapContext mapContext, String name) {
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Map map = new HashMap();
		map.put(key, mapContext.get(key));
		activityInfoEntity.setAttr(map);
		ResEntity resEntity = new ResEntity();
		resEntity.setName(name);
		activityInfoEntity.setRes(resEntity);
		return activityInfoEntity;
	}

	public static SystemActivityEvent getEventByParamFromEventsMap(Map<String, Object> params, Map<String, SystemActivityEvent> events, SystemActivityEvent defaultEvent) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			if ("id".equals(key)) {
					continue;
			}
			if (events.containsKey(entry.getKey())) {
				return events.get(key);
			}
		}
		return defaultEvent;
	}

}
