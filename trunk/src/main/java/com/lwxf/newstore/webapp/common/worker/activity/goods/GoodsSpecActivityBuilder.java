package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsSpecResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsTypeResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/20/020 19:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GoodsSpecActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name="goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "goodsSpecService")
	private GoodsSpecService goodsSpecService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(GoodsSpec.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Object params = tsManagerEntity.getMapperParams();
		GoodsSpecResEntity goodsSpecResEntity = new GoodsSpecResEntity();
		Map data = new HashMap<String,Object>();
		Map<String,Object> attrMap;
		GoodsSpec goodsSpec;
		GoodsType goodsType;

		String id;
		switch (sqlType){
			case INSERT:
				goodsSpec = (GoodsSpec) params;
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_CREATE.getValue());
				systemActivity.setR1(goodsSpec.getId());
				systemActivity.setR2(goodsSpec.getGoodsTypeId());
				goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
				data.put("name",goodsType.getName());
				data.put("id",goodsType.getId());
				activityInfoEntity.setData(data);
				goodsSpecResEntity.setName(goodsSpec.getName());
				break;
			case UPDATE:
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_MD.getValue());
				Map<String,Object> paramsMap = (Map<String, Object>) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				systemActivity.setR1(id);
				goodsSpec = this.goodsSpecService.findById(id);
				systemActivity.setR2(goodsSpec.getGoodsTypeId());
				goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
				data.put("name",goodsType.getName());
				data.put("id",goodsType.getId());
				activityInfoEntity.setData(data);
				goodsSpecResEntity.setName(goodsSpec.getName());
				attrMap = new HashMap<>();
				paramsMap.forEach((key,value) ->{
					if(key.equals(WebConstant.KEY_ENTITY_ID)){
							return;
						}
						attrMap.put(key,value);
					});
				activityInfoEntity.setAttr(attrMap);
				break;
			//删除可能涉及到规格选项表 所以进行埋点操作
		}
		activityInfoEntity.setRes(goodsSpecResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		String event= tsManualData.getEvent();
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Object params = tsManualData.getParams();
		GoodsSpecResEntity goodsSpecResEntity = new GoodsSpecResEntity();
		Map data = new HashMap<String,Object>();
		Map<String,Object> attrMap;
		GoodsSpec goodsSpec;
		GoodsType goodsType;
		String id;
		if (event.equals(SystemActivityEvent.GOODSTYPE_SPEC_DELETE.getValue())){//删除分类时进行埋点操作
			id = (String)params;
			systemActivity.setR1(id);
			systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_DELETE.getValue());
			goodsSpec= this.goodsSpecService.findById(id);
			if(goodsSpec==null){
				return null;
			}
			goodsType = this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
			systemActivity.setR2(goodsSpec.getGoodsTypeId());
			data.put("name",goodsType.getName());
			data.put("id",goodsType.getId());
			activityInfoEntity.setData(data);
			goodsSpecResEntity.setName(goodsSpec.getName());
		}
		activityInfoEntity.setRes(goodsSpecResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}
