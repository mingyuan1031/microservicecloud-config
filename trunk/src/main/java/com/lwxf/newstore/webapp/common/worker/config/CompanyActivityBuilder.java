package com.lwxf.newstore.webapp.common.worker.config;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/20 10:20
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class CompanyActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Company.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		StoreConfigResEntity companyEntity = new StoreConfigResEntity();
		Map<String, Object> attrMap;
		Company company;
		String id;
		switch (sqlType){
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				company = this.companyService.findById(id);
				systemActivity.setR1(id);
				companyEntity.setName(company.getName());
				systemActivity.setEvent(SystemActivityEvent.STORECFG_MD.getValue());
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
		activityInfoEntity.setRes(companyEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		return null;
	}
}
