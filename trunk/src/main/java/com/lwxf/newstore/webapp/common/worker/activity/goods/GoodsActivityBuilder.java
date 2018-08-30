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
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.goods.GoodsStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsExtendResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/17/017 17:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GoodsActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Goods.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		GoodsResEntity goodsResEntity = new GoodsResEntity();
		Map<String, Object> attrMap;
		Goods goods;
		String id;
		switch (sqlType) {
			case INSERT:
				goods = (Goods) params;
				systemActivity.setEvent(SystemActivityEvent.GOODS_CREATE.getValue());
				systemActivity.setR1(goods.getId());
				goodsResEntity.setName(goods.getName());
				goodsResEntity.setId(goods.getId());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				goods = this.goodsService.findById(id);
				systemActivity.setR1(id);
				goodsResEntity.setName(goods.getName());
				goodsResEntity.setId(goods.getId());
				Object disabled = paramsMap.get("disabled");
				if (disabled != null) {
					String falg = "0";
					if((Boolean)disabled==true){
						falg="1";
						systemActivity.setEvent(SystemActivityEvent.GOODS_DISABLED.getValue());
					}else{
						systemActivity.setEvent(SystemActivityEvent.GOODS_ENABLED.getValue());
					}
					Byte statsValue = Byte.valueOf(falg);
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_DISABLED,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,GoodsStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.GOODS_MD.getValue());
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
			default:
				return null;
		}
		activityInfoEntity.setRes(goodsResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		String event = tsManualData.getEvent();
		Goods goods = (Goods) tsManualData.get(WebConstant.KEY_ENTITY_GOODS);
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		GoodsResEntity goodsResEntity = new GoodsResEntity();
		if(event.equals("null")){
			return null;
		}else if(event.equals(SystemActivityEvent.GOODS_PRICES_CREATE.getValue())){ // 添加报价
			List<GoodsExtend> params = (List<GoodsExtend>)tsManualData.getParams();
			//TODO
			systemActivity.setEvent(SystemActivityEvent.GOODS_CREATE.getValue());
			systemActivity.setR1(goods.getId());
			goodsResEntity.setName(goods.getName());
			goodsResEntity.setId(goods.getId());
			//赋值事件
			systemActivity.setEvent(event);
			//赋值R1 当前资源id
			systemActivity.setR1(goods.getId());
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("price",params);
			//赋值R3的模型的attr属性 因为不存在父级 所以没有data
			activityInfoEntity.setAttr(attrMap);
			//赋值R3的模型的res属性 因为不存在父级资源 所以没有R2
			activityInfoEntity.setRes(goodsResEntity);
		}else if(event.equals(SystemActivityEvent.GOODS_PRICES_MD.getValue())){//修改报价
			Map<String,Object> paramsMap = (Map<String, Object>) tsManualData.getParams();
			GoodsExtend goodsExtend = (GoodsExtend) tsManualData.get("goodsExtend");
			GoodsExtendResEntity goodsExtendResEntity = new GoodsExtendResEntity();
			String id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
			if(null == id){
				return null;
			}
			//赋值事件
			systemActivity.setEvent(SystemActivityEvent.GOODS_PRICES_MD.getValue());
			goodsExtendResEntity.setOptions(goodsExtend.getOptions());
			goodsExtendResEntity.setOriginalprice(goodsExtend.getOriginalprice());
			goodsExtendResEntity.setPrice(goodsExtend.getPrice());
			goodsResEntity.setName(goods.getName());
			goodsResEntity.setId(goods.getId());
			//赋值R1
			systemActivity.setR1(goods.getId());
			//赋值R3的模型的res属性
			activityInfoEntity.setRes(goodsResEntity);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			paramsMap.forEach((key,value) ->{
				if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_DISABLED)){
					return;
				}
				attrMap.put(key,value);
			});
			Map<String,Object> attr = new HashMap<String,Object>();
			List att = new ArrayList();
			att.add(attrMap);
			attr.put("price",att);
			//赋值R3的,模型的attr属性
			activityInfoEntity.setAttr(attr);
		}else if(event.equals(SystemActivityEvent.GOODS_MD.getValue())){//商品修改  只有当修改商品详情的时候 才会调用此方法
			systemActivity.setR1(goods.getId());
			systemActivity.setEvent(SystemActivityEvent.GOODS_MD.getValue());
			goodsResEntity.setId(goods.getId());
			goodsResEntity.setName(goods.getName());
			Map<String,Object> attrMap = new HashMap<String, Object>();
			//因为该方法只 存在于修改详情时使用 所以修改的参数 肯定只有一个 所以不必
			attrMap.put("content",((Map<String ,Object>)tsManualData.getParams()).get("content"));
			activityInfoEntity.setAttr(attrMap);
			activityInfoEntity.setRes(goodsResEntity);
		}
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}
