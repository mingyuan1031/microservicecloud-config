package com.lwxf.newstore.webapp.common.worker.activity.order.Builder;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.order.OrderStatusValue;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.order.OrderResEntity;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/26 17:34
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class OrderActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper =JsonMapper.nonEmptyMapper();
	@Resource(name = "orderService")
	private OrderService orderService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Order.class,this);
	}

	/*@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {//没用
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		OrderResEntity orderResEntity = new OrderResEntity();
		Map<String, Object> attrMap;
		Order order;
		String id;
		switch (sqlType){
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get("id");
				if(null == id){
					return null;
				}
				systemActivity.setR1(id);
				order = orderService.findById(id);
				orderResEntity.setName(order.getOrderNumber());
				Object status = paramsMap.get("status");
				if (status != null) {
					systemActivity.setEvent(SystemActivityEvent.ORDER_STATUS_MD.getValue());
					Byte statsValue = Byte.valueOf(status.toString());
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,OrderStatusValue.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.ORDER_MD.getValue());
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
		activityInfoEntity.setRes(orderResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}*/

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		return super.build(tsManagerEntity, sqlType);
	}

	@Override
	public Object build(TSManualData tsManualData) {//埋点处理添加订单
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		OrderResEntity orderResEntity = new OrderResEntity();
		Object params = tsManualData.getParams();
		Map<String, Object> attrMap;
		Order order;
		String id;
		//创建订单
		if (tsManualData.getEvent().equals(SystemActivityEvent.ORDER_CREATE.getValue())) {
			order = (Order) params;
			systemActivity.setEvent(SystemActivityEvent.ORDER_CREATE.getValue());
			systemActivity.setR1(order.getId());
			orderResEntity.setName(WebUtils.getCurrUser().getName());
			orderResEntity.setFreight(order.getFreight());
			orderResEntity.setPaidPrice(order.getPaidPrice());
			orderResEntity.setDescr(order.getDescr());
			activityInfoEntity.setRes(orderResEntity);
			systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		} //修改订单状态
		else if  (tsManualData.getEvent().equals(SystemActivityEvent.ORDER_STATUS_MD.getValue())){
			Object paramsObject = tsManualData.getParams();
			Map<String,Object> paramsMap =(Map<String,Object>) paramsObject;
			id = (String)paramsMap.get("id");
			if(null == id){
				return null;
			}
			systemActivity.setR1(id);
			order = orderService.findById(id);
			orderResEntity.setName(order.getOrderNumber());
			Object status = paramsMap.get("status");
			if (status != null) {
				systemActivity.setEvent(SystemActivityEvent.ORDER_STATUS_MD.getValue());
				Byte statsValue = Byte.valueOf(status.toString());
				attrMap = new HashMap<>();
				attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
				attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,OrderStatusValue.getByValue(statsValue).getName());
				activityInfoEntity.setAttr(attrMap);
				activityInfoEntity.setRes(orderResEntity);
				systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
			} else {
				systemActivity.setEvent(SystemActivityEvent.ORDER_MD.getValue());
				attrMap = new HashMap<>();
				paramsMap.forEach((key,value) ->{
					if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_STATUS)){
						return;
					}
					attrMap.put(key,value);
				});
				activityInfoEntity.setRes(orderResEntity);
				activityInfoEntity.setAttr(attrMap);
				systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
			}
		}else{
			return null;
		}
		return systemActivity;
	}
}
