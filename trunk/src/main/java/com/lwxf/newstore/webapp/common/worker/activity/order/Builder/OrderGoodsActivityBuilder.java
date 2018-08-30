package com.lwxf.newstore.webapp.common.worker.activity.order.Builder;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.order.GoodsOrderStatus;
import com.lwxf.newstore.webapp.common.enums.order.OrderGoodsStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.order.OrderGoodsResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.order.OrderResEntity;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/7/26/026 16:29
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class OrderGoodsActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "orderGoodsService")
	private OrderGoodsService orderGoodsService;
	@Resource(name = "orderService")
	private OrderService orderService;
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(OrderGoods.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		return null;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		SystemActivity systemActivity =newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		OrderGoodsResEntity orderGoodsResEntity = new OrderGoodsResEntity();
		String event = tsManualData.getEvent();
		Object params= tsManualData.getParams();
		Map<String,Object> data = new HashMap<String, Object>();
		OrderGoods orderGoods;
		Order order;
		String id;
		Map<String ,Object> attrMap;
		if(event.equals(SystemActivityEvent.ORDERGOODS_STATUS_MD.getValue())) {
			Map<String, Object> paramsMap = (Map) params;
			id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null==id){
					return null;
				}
				orderGoods = this.orderGoodsService.findById(id);
				order=this.orderService.findById(orderGoods.getOrderId());
				systemActivity.setR1(orderGoods.getId());
				systemActivity.setR2(order.getId());
				orderGoodsResEntity.setName(this.goodsService.findById(this.goodsExtendService.findById(orderGoods.getGoodsExtendId()).getGoodsId()).getName());
				orderGoodsResEntity.setDescr(orderGoods.getDescr());
				orderGoodsResEntity.setGoodsAmount(orderGoods.getGoodsAmount());
				orderGoodsResEntity.setPaidPrice(orderGoods.getPaidPrice());
				data.put("paidPrice",order.getPaidPrice());
				data.put("status",order.getStatus());
				data.put("orderId",order.getId());
				data.put("orderNumber",order.getOrderNumber());
				activityInfoEntity.setData(data);
				Object status=  paramsMap.get(WebConstant.KEY_ENTITY_STATUS);
				if(status==null){
					return null;
				}
				Byte statsValue = Byte.valueOf(status.toString());
				systemActivity.setEvent(SystemActivityEvent.ORDERGOODS_STATUS_MD.getValue());
				attrMap = new HashMap<>();
				attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
				attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,OrderGoodsStatus.getByValue(statsValue).getName());
				activityInfoEntity.setAttr(attrMap);

		}else {
			return null;
		}
		activityInfoEntity.setRes(orderGoodsResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}
