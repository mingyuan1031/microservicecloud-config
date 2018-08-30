package com.lwxf.newstore.webapp.common.worker.activity.quickshare.microblog;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.quickshare.MicroblogStatus;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/17 14:54
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class MicroblogActivityBuilder extends BaseActivityBuilder {
	//jsonmapper是非空
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "microblogService")
	private MicroblogService microblogService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Microblog.class,this);
	}
	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		//实例化一个日志对象
		SystemActivity systemActivity = newSystemActivityInstance();
		//得到mapperParams（对象或者map）
		Object params = tsManagerEntity.getMapperParams();
		//活动日志r3实体类
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		//博客帖子
		MicroblogResEntity microblogResEntity = new MicroblogResEntity();
		Map<String, Object> attrMap;
		Microblog microblog;
		String id;
		switch (sqlType){
			case INSERT:
				microblog=(Microblog) params;
				systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_CREATE.getValue());
				systemActivity.setR1(microblog.getId());
				microblogResEntity.setContent(microblog.getContent());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				systemActivity.setR1(id);
				microblog = microblogService.findById(id);
				microblogResEntity.setContent(microblog.getContent());
				activityInfoEntity.setRes(microblogResEntity);
				Object status = paramsMap.get("status");
				if (status != null) {
					systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_MD.getValue());
					Byte statsValue = Byte.valueOf(status.toString());
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,MicroblogStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_MD.getValue());
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
			case DELETE:
				id = (String) tsManagerEntity.getMapperParams();
				microblog = this.microblogService.findById(id);
				systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_DELETE.getValue());
				systemActivity.setR1(id);
				//microblogResEntity.setName(microblog.getCreator());
				microblogResEntity.setContent(microblog.getContent());
				break;
		}
		activityInfoEntity.setRes(microblogResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		systemActivity.setCompanyId(WebUtils.getCurrCompanyId());
		return systemActivity;
	}


	@Override
	public Object build(TSManualData tsManualData) {
		SystemActivity systemActivity = newSystemActivityInstance();
		if (tsManualData.getEvent().equals(SystemActivityEvent.QUICKSHARE_DELETE.getValue())){
			systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_DELETE.getValue());
			systemActivity.setR1((String) tsManualData.get(WebConstant.KEY_ENTITY_ID));
			ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
			Microblog microblog = microblogService.findById((String) tsManualData.getParams());
			MicroblogResEntity microblogResEntity = new MicroblogResEntity();
			microblogResEntity.setContent(microblog.getContent());
			activityInfoEntity.setRes(microblogResEntity);
			systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
			systemActivity.setCompanyId(WebUtils.getCurrCompanyId());
		}else {
			return null;
		}
		return systemActivity;
	}
}
