package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.goods.GoodsTypeStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsTypeResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/20/020 11:22
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GoodsTypeActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(GoodsType.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		Object params = tsManagerEntity.getMapperParams();
		GoodsTypeResEntity goodsTypeResEntity = new GoodsTypeResEntity();
		Map data = new HashMap();
		Map<String,Object> attrMap;
		GoodsType goodsType;
		String id;
		switch (sqlType){
			case INSERT:
				goodsType = (GoodsType) params;
				systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_CREATE.getValue());
				systemActivity.setR1(goodsType.getId());
				if(goodsType.getParentId()!=null){
					GoodsType parentGoodsType = this.goodsTypeService.findById(goodsType.getParentId());
					data.put("name",parentGoodsType.getName());
					data.put("id",parentGoodsType.getId());
					activityInfoEntity.setData(data);
				}
				goodsTypeResEntity.setName(goodsType.getName());
				goodsTypeResEntity.setId(goodsType.getId());
				break;
			case UPDATE:
				Map<String,Object> paramsMap = (Map<String, Object>) params;
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				systemActivity.setR1(id);
				goodsType = this.goodsTypeService.findById(id);
				if(goodsType.getParentId()!=null){//如果存在父级 就赋值给data
					GoodsType parentGoodsType = this.goodsTypeService.findById(goodsType.getParentId());
					data.put("name",parentGoodsType.getName());
					data.put("disabled",parentGoodsType.getDisabled());
					activityInfoEntity.setData(data);
				}
				goodsTypeResEntity.setName(goodsType.getName());
				goodsTypeResEntity.setId(goodsType.getId());
				Object disabled= paramsMap.get("disabled");
				if (disabled != null) {
					String falg = "0";
					if((Boolean)disabled==true){
						falg="1";
						systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_DISABLED.getValue());
					}else{
						systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_ENABLED.getValue());
					}
					Byte statsValue = Byte.valueOf(falg);
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_DISABLED,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,GoodsTypeStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_MD.getValue());
					attrMap = new HashMap<>();
					paramsMap.forEach((key,value) ->{
						if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_DISABLED)){
							return;
						}
						attrMap.put(key,value);
					});
					activityInfoEntity.setAttr(attrMap);
				}
				break;
				//删除可能涉及到规格表 所以进行埋点操作
		}
		activityInfoEntity.setRes(goodsTypeResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		String event= tsManualData.getEvent();
		Object params = tsManualData.getParams();
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		GoodsTypeResEntity goodsTypeResEntity = new GoodsTypeResEntity();
		Map data = new HashMap();
		Map<String,Object> attrMap;
		GoodsType goodsType;
		String id;
		if (event.equals(SystemActivityEvent.GOODSTYPE_DELETE.getValue())){//删除分类时进行埋点操作
			id = (String)params;
			systemActivity.setR1(id);
			systemActivity.setEvent(SystemActivityEvent.GOODSTYPE_DELETE.getValue());
			goodsType = this.goodsTypeService.findById(id);
			if(goodsType.getParentId()!=null){//如果存在父级 就赋值给data
				GoodsType parentGoodsType = this.goodsTypeService.findById(goodsType.getParentId());
				data.put("name",parentGoodsType.getName());
				data.put("id",parentGoodsType.getId());
				activityInfoEntity.setData(data);
			}
			goodsTypeResEntity.setName(goodsType.getName());
			goodsTypeResEntity.setId(goodsType.getId());
		}
		activityInfoEntity.setRes(goodsTypeResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}
