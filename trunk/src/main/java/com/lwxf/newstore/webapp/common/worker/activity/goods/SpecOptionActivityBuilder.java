package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsSpecResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsTypeResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.SpecOptionResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/20/020 20:18
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class SpecOptionActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name="goodsSpecService")
	private GoodsSpecService goodsSpecService;
	@Resource(name = "goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(SpecOption.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Object params = tsManagerEntity.getMapperParams();
		SpecOptionResEntity specOptionResEntity = new SpecOptionResEntity();
		Map data = new HashMap<String,Object>();
		GoodsSpecResEntity goodsSpecResEntity = new GoodsSpecResEntity();
		Map<String,Object> attrMap;
		GoodsSpec goodsSpec;
		GoodsType goodsType;
		SpecOption specOption;
		String id;
		List att = new ArrayList();
		switch (sqlType){
			case INSERT:
				specOption = (SpecOption) params;
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_OPTIONS_CREATE.getValue());
				systemActivity.setR1(specOption.getGoodsSpecId());
				goodsSpec=this.goodsSpecService.findById(specOption.getGoodsSpecId());
				systemActivity.setR2(goodsSpec.getGoodsTypeId());
				goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
				data.put("name",goodsType.getName());
				data.put("id",goodsType.getId());
				activityInfoEntity.setData(data);
				goodsSpecResEntity.setName(goodsSpec.getName());
				attrMap = new HashMap<String,Object>();
				att.add(specOption);
				attrMap.put("options",att);
				activityInfoEntity.setAttr(attrMap);
				break;
			case UPDATE:
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_OPTIONS_MD.getValue());
				Map<String,Object> paramsMap = (Map<String, Object>) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				specOption = this.specOptionService.findById(id);
				goodsSpec = this.goodsSpecService.findById(specOption.getGoodsSpecId());
				goodsSpecResEntity.setName(goodsSpec.getName());
				systemActivity.setR1(goodsSpec.getId());
				goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
				systemActivity.setR2(goodsType.getId());
				data.put("name",goodsType.getName());
				data.put("id",goodsType.getId());
				activityInfoEntity.setData(data);
				Map parmMap = new HashMap<>();
				paramsMap.forEach((key,value) ->{
					if(key.equals(WebConstant.KEY_ENTITY_ID)){
						return;
					}
					parmMap.put(key,value);
				});
				att.add(parmMap);
				attrMap = new HashMap<String, Object>();
				attrMap.put("options",att);
				activityInfoEntity.setAttr(attrMap);
				break;
			case DELETE:
				id = (String)params;
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_OPTIONS_DELETE.getValue());
				specOption = this.specOptionService.findById(id);
				goodsSpec = this.goodsSpecService.findById(specOption.getGoodsSpecId());
				systemActivity.setR1(goodsSpec.getId());
				goodsSpecResEntity.setName(goodsSpec.getName());
				goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
				systemActivity.setR2(goodsType.getId());
				data.put("name",goodsType.getName());
				data.put("id",goodsType.getId());
				activityInfoEntity.setData(data);
				List list = new ArrayList();
				list.add(specOption);
				attrMap = new HashMap<String, Object>();
				attrMap.put("options",list);
				activityInfoEntity.setAttr(attrMap);
				break;
		}
		activityInfoEntity.setRes(goodsSpecResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		return null;
	}
}
