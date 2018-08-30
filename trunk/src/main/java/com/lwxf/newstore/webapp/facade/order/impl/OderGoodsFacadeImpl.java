package com.lwxf.newstore.webapp.facade.order.impl;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lwxf.newstore.webapp.common.mobile.weixin.template.OrderEvaluateMsg;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.wall.violation.ErrorCode;


import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.order.LogisticsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.order.GoodsOrderStatus;
import com.lwxf.newstore.webapp.common.enums.order.OrderStatus;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dto.order.LogisticsAddressDto;
import com.lwxf.newstore.webapp.domain.dto.order.LogisticsDto;
import com.lwxf.newstore.webapp.domain.dto.order.OrderGoodsDto;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.order.OrderGoodsFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/29 19:26
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("orderGoodsFacade")
public class OderGoodsFacadeImpl extends BaseFacadeImpl implements OrderGoodsFacade {

	@Resource
	private OrderGoodsService orderGoodsService;
	@Resource
	private LogisticsService logisticsService;
	@Resource
	private OrderService orderService;

	/**
	 * 创建商品订单
	 * @param orderGoods
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult createOrderGoods(OrderGoods orderGoods) {

		this.orderGoodsService.add(orderGoods);
		return ResultFactory.generateRequestResult(orderGoods);
	}

	/**
	 * 删除商品订单
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult deleteOrderGoods(String id) {
		if (!this.orderGoodsService.isExist(id)) {
			return ResultFactory.generateResNotFoundResult();
		}
		this.orderGoodsService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	/**
	 * 修改订单状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateOrderGoodsStatus(String id, Integer status) {
		OrderGoods orderGoods = this.orderGoodsService.findById(id);
		Order order=null;
		if (orderGoods == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		MapContext context = MapContext.newOne();
		context.put(WebConstant.KEY_ENTITY_ID, id);
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setEvent(SystemActivityEvent.ORDERGOODS_STATUS_MD.getValue());
		tsManualData.setParams(status);
		tsManualData.setClazz(OrderGoods.class);
		tsManualData.setParams(context);
		switch (status) {

			case 2:
				context.put(WebConstant.KEY_ENTITY_STATUS, status);
				Logistics logistics = this.logisticsService.selectByOrderGoodsId(id);
				if (logistics == null) {
					return ResultFactory.generateResNotFoundResult();
				}
				MapContext updatelogistics = MapContext.newOne();
				updatelogistics.put(WebConstant.KEY_ENTITY_ID, logistics.getId());
				updatelogistics.put(WebConstant.KEY_ENTITY_UPDATED, DateUtil.getSystemDate());
				updatelogistics.put(WebConstant.KEY_ENTITY_RECEIPTTIME, DateUtil.getSystemDate());
				this.logisticsService.updateByMapContext(updatelogistics);
				boolean isReceipt = true;
				List<OrderGoods> orderGoodsList = this.orderGoodsService.selectByOrderId(orderGoods.getOrderId());
				for (OrderGoods orderGoodsOne : orderGoodsList) {
					if (orderGoodsOne.getId().equals(orderGoods.getId())) {
						continue;
					}
					if (orderGoodsOne.getStatus().intValue()==GoodsOrderStatus.SHIPPED.getValue())
					{
						isReceipt=false;
					}
				}
				if (isReceipt)
				{
					MapContext orderContext = MapContext.newOne();
					orderContext.put(WebConstant.KEY_ENTITY_ID,orderGoods.getOrderId());
					orderContext.put(WebConstant.KEY_ENTITY_STATUS, OrderStatus.RECEIVED.getValue());
					this.orderService.updateByMapContext(orderContext);
				}
				//发送微信消息 邀请用户评价

				order = this.orderService.selectByOrderGoodsId(id);
				if (order!=null)
				{
					OrderEvaluateMsg msg = new OrderEvaluateMsg();
					Map<String,String> map = new HashMap<>();
					map.put("order_number",order.getOrderNumber());
					map.put("order_time",DateUtil.getSystemDateTimeString());
					msg.setContentMsg(map);
					UserThirdInfo userThirdInfo = AppBeanInjector.userThirdInfoService.findByUserId(order.getMemberId());
					msg.setTouser(userThirdInfo.getWxOpenId());
					AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
				}
				break;
			case 3:
				context.put(WebConstant.KEY_ENTITY_STATUS, status);
				break;
			case 4:
				context.put(WebConstant.KEY_ENTITY_STATUS, status);
				break;
			default:
				MapContext mapContext = MapContext.newOne();
				mapContext.put(WebConstant.KEY_ENTITY_STATUS,ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,mapContext);
		}

		this.orderGoodsService.updateByMapContext(context);
		context.put("order",order);
		return ResultFactory.generateRequestResult(context);
	}

	/**
	 * 查询商品订单
	 * @param id
	 * @return
	 */
	@Override
	public RequestResult findOrderGoodsById(String id) {
		return ResultFactory.generateRequestResult(this.orderGoodsService.findById(id));
	}

	/**
	 * 商品发货（不用）
	 * @param id
	 * @param map
	 * @return
	 */
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public RequestResult sendOutGoods(String id, MapContext map) {
//		if(!this.orderGoodsService.isExist(id))
//		{
//			return ResultFactory.generateResNotFoundResult();
//		}
//		Order order = this.orderService.selectByOrderGoodsId(id);
//		MapContext context = MapContext.newOne();
//		context.put(WebConstant.KEY_ENTITY_ID, id);
//		context.put(WebConstant.KEY_ENTITY_STATUS, GoodsOrderStatus.SHIPPED.getValue());
//		this.orderGoodsService.updateByMapContext(context);
//		map.put("orderGoodsId",id);
//		map.put("orderId",order.getId());
//		map.put("receiptId",order.getReceiptId());
//		map.put("created",DateUtil.getSystemDate());
//		map.put("deliverTime",DateUtil.getSystemDate());
//		RequestResult result = Logistics.validateFields(map);
//		if (result!=null)
//		{
//			return  result;
//		}else
//		{
//
//		}
//		return ResultFactory.generateRequestResult(map);
//	}
}
