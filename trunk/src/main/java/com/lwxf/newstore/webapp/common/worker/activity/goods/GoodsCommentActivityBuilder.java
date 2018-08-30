package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sun.org.apache.bcel.internal.generic.NEW;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsCommentService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.goods.GoodsCommentStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsCommentResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/21/021 15:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GoodsCommentActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Resource(name = "goodsCommentService")
	private GoodsCommentService goodsCommentService;
	@Resource(name = "orderGoodsService")
	private OrderGoodsService orderGoodsService;
	@Resource(name = "orderService")
	private OrderService orderService;
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(GoodsComment.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		GoodsResEntity goodsResEntity = new GoodsResEntity();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		GoodsCommentResEntity goodsCommentResEntity = new GoodsCommentResEntity();
		Object parms = tsManagerEntity.getMapperParams();
		String id;
		Goods goods;
		GoodsComment goodsComment;
		switch (sqlType){
			case UPDATE:
				Map<String, Object> paramsMap = (Map) parms;
				Object disabled=paramsMap.get(WebConstant.KEY_ENTITY_DISABLED);
				String falg = "1";
				if((Boolean) disabled==true){
					systemActivity.setEvent(SystemActivityEvent.GOODSCOMMENT_DISABLED.getValue());
				}else{
					falg="0";
					systemActivity.setEvent(SystemActivityEvent.GOODSCOMMENT_ENABLED.getValue());
				}
				Byte statsValue = Byte.valueOf(falg);
				Map attrMap = new HashMap<>();
				attrMap.put(WebConstant.KEY_ENTITY_DISABLED,statsValue);
				attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,GoodsCommentStatus.getByValue(statsValue).getName());
				activityInfoEntity.setAttr(attrMap);
				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				systemActivity.setR1(id);
				goodsComment= this.goodsCommentService.findById(id);
				goodsCommentResEntity.setContent(goodsComment.getContent());
				goodsCommentResEntity.setCreated(goodsComment.getCreated());
				goods=this.goodsService.findById(goodsComment.getGoodsId());
				systemActivity.setR2(goods.getId());
				goodsResEntity.setName(goods.getName());
				goodsResEntity.setId(goods.getId());
				activityInfoEntity.setData(goodsResEntity);
		}
		activityInfoEntity.setRes(goodsCommentResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		SystemActivity systemActivity = newSystemActivityInstance();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		String event=tsManualData.getEvent();
		Object params= tsManualData.getParams();
		//定义data的map
		Map<String,Object> data = new HashMap<String, Object>();
		String orderGoodsId = (String) tsManualData.get("orderGoodsId");
		GoodsCommentResEntity goodsCommentResEntity = new GoodsCommentResEntity();
		Goods goods;
		GoodsComment goodsComment;
		OrderGoods orderGoods;
		Order order;
		String id;
		if(event.equals(SystemActivityEvent.GOODSCOMMENT_ADMIN_CREATE.getValue())){
			systemActivity.setEvent(SystemActivityEvent.GOODSCOMMENT_ADMIN_CREATE.getValue());
			goodsComment = (GoodsComment) params;
			data.put("parentId",goodsComment.getParentId());
			data.put("user",false);
			GoodsExtend goodsExtend = this.goodsExtendService.findById(this.goodsCommentService.findById(goodsComment.getParentId()).getGoodsExtendId());
			data.put("originalOptions",goodsExtend.getOptions());
			String[] options= goodsExtend.getOptions().split(",");
			String option="";
			for (int i = 0; i <options.length ; i++) {
				SpecOption specOption= this.specOptionService.findById(options[i]);
				option+=specOption.getName()+" ";
			}
			data.put("options",option);
			goodsCommentResEntity.setContent(goodsComment.getContent());
			goodsCommentResEntity.setCreated(goodsComment.getCreated());
			activityInfoEntity.setRes(goodsCommentResEntity);
			systemActivity.setR1(goodsComment.getId());
			systemActivity.setR2(goodsComment.getGoodsId());
			activityInfoEntity.setData(data);
		}else if(event.equals(SystemActivityEvent.GOODSCOMMENT_USER_CREATE.getValue())){
			systemActivity.setEvent(SystemActivityEvent.GOODSCOMMENT_USER_CREATE.getValue());
			goodsComment = (GoodsComment) params;
			goods =this.goodsService.findById(goodsComment.getGoodsId());
			data.put("goodsName",goods.getName());
			data.put("goodsId",goods.getId());
			data.put("user",true);
			orderGoods = this.orderGoodsService.findById(orderGoodsId);
			order = this.orderService.findById(orderGoods.getOrderId());
			data.put("goodsExtendId",orderGoods.getGoodsExtendId());
			GoodsExtend goodsExtend = this.goodsExtendService.findById(orderGoods.getGoodsExtendId());
			data.put("originalOptions",goodsExtend.getOptions());
			String[] options= goodsExtend.getOptions().split(",");
			String option="";
			for (int i = 0; i <options.length ; i++) {
				SpecOption specOption= this.specOptionService.findById(options[i]);
				option+=specOption.getName()+" ";
			}
			data.put("options",option);
			data.put("orderId",order.getId());
			data.put("orderNumber",order.getOrderNumber());
			data.put("orderStatus",order.getStatus());
			data.put("paidPrice",order.getPaidPrice());
			goodsCommentResEntity.setContent(goodsComment.getContent());
			goodsCommentResEntity.setCreated(goodsComment.getCreated());
			activityInfoEntity.setRes(goodsCommentResEntity);
			systemActivity.setR1(goodsComment.getId());
			systemActivity.setR2(goodsComment.getGoodsId());
			activityInfoEntity.setData(data);
		}else if(event.equals(SystemActivityEvent.GOODSCOMMENT_DELETE.getValue())){
			systemActivity.setEvent(SystemActivityEvent.GOODSCOMMENT_DELETE.getValue());
			id = (String)params;
			goodsComment = this.goodsCommentService.findById(id);
			goods = this.goodsService.findById(goodsComment.getGoodsId());
			data.put("goodsId",goods.getId());
			data.put("goodsName",goods.getName());
			goodsCommentResEntity.setCreated(goodsComment.getCreated());
			goodsCommentResEntity.setContent(goodsComment.getContent());
			activityInfoEntity.setRes(goodsCommentResEntity);
			systemActivity.setR1(id);
			systemActivity.setR2(goods.getId());
			activityInfoEntity.setData(data);
		}else if(event.equals("null")){
			return null;
		}
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}

