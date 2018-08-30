package com.lwxf.newstore.webapp.common.worker.config;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.config.SystemConfig;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/21 9:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class SystemConfigActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "systemConfigService")
	private SystemConfigService systemConfigService;
	@Resource(name = "companyService")
	private CompanyService companyService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(SystemConfig.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		SystemConfigResEntity systemConfigResEntity = new SystemConfigResEntity();
		Map<String, Object> attrMap;
		String id;
		switch (sqlType){
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if (null == id) {
					return null;
				}
				systemActivity.setR1(id);
				systemConfigResEntity.setName("系统配置");
				systemActivity.setEvent(SystemActivityEvent.SYSCFG_MD.getValue());
				attrMap = new HashMap<>();
				paramsMap.forEach((key,value) ->{
					if(key.equals(WebConstant.KEY_ENTITY_ID)){
						return;
					}
					attrMap.put(key,value);
				});
				activityInfoEntity.setAttr(attrMap);
				break;
			default :
				return null;
		}
		activityInfoEntity.setRes(systemConfigResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		SystemActivity systemActivity = newSystemActivityInstance();
		if (tsManualData.getEvent().equals(SystemActivityEvent.COMPANY_MD.getValue())){
			systemActivity.setEvent(SystemActivityEvent.COMPANY_MD.getValue());
			systemActivity.setR1((String) tsManualData.get(WebConstant.KEY_ENTITY_ID));
			ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
			Company company = companyService.findById((String) tsManualData.getParams());
			CompanyResEntity companyResEntity = new CompanyResEntity();
			companyResEntity.setName(company.getName());
			activityInfoEntity.setRes(companyResEntity);
			systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		}else {
			return null;
		}
		return systemActivity;
	}
}
