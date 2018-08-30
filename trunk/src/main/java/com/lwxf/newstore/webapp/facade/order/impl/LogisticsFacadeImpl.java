package com.lwxf.newstore.webapp.facade.order.impl;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.OrderDeliveryMsg;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.order.LogisticsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.order.GoodsOrderStatus;
import com.lwxf.newstore.webapp.common.enums.order.OrderStatus;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.order.LogisticsFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/30 9:36
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("logisticsFacade")
public class LogisticsFacadeImpl extends BaseFacadeImpl implements LogisticsFacade {

	@Resource
	private LogisticsService logisticsService;
	@Resource
	private OrderGoodsService orderGoodsService;
	@Resource
	private OrderService orderService;
	@Resource
	private AddressService addressService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult createLogistics(Logistics logistics, String[] ids, String orderId) {
		List<OrderGoods> ogoodsList = this.orderGoodsService.selectByIds(ids);

		if (ogoodsList.size() != ids.length) {
			return ResultFactory.generateResNotFoundResult();
		}
		//创建发货单
		String idsStr = StringUtils.join(ids, ",");
		Order order = this.orderService.findById(orderId);
		logistics.setOrderGoodsId(idsStr);
		logistics.setOrderId(orderId);
		logistics.setReceiptId(order.getReceiptId());
		logistics.setCreated(DateUtil.getSystemDate());
		logistics.setDeliverTime(DateUtil.getSystemDate());
		RequestResult result = logistics.validateFields();
		if (result!=null)
		{
			return result;
		}
		//埋点开始
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Logistics.class);
		tsManualData.setEvent(SystemActivityEvent.LOGISTICS_CREATE.getValue());
		tsManualData.setParams(logistics);
		//埋点结束
		this.logisticsService.add(logistics);
		//把商品状态改为已发货状态
		List<OrderGoods> orderGoodsList = this.orderGoodsService.selectByIds(ids);
		for (OrderGoods orderGoods : orderGoodsList) {
			MapContext context = MapContext.newOne();
			context.put(WebConstant.KEY_ENTITY_STATUS, GoodsOrderStatus.SHIPPED.getValue());
			context.put(WebConstant.KEY_ENTITY_ID, orderGoods.getId());
			this.orderGoodsService.updateByMapContext(context);

		}

		if (order.equals(null)) {
			return ResultFactory.generateResNotFoundResult();
		}
		List<OrderGoods> list = this.orderGoodsService.selectByOrderId(orderId);
		boolean isAllSend = true;
		for (OrderGoods orderGoods : list) {

			if (ArrayUtils.contains(ids, orderGoods.getId())) { // TODO:是数组
				continue;
			}
			if (orderGoods.getStatus().intValue() == GoodsOrderStatus.NOTSHIPPED.getValue()) {
				isAllSend = false;
			}
		}
		MapContext orderContext = MapContext.newOne();
		if (!isAllSend) {
			orderContext.put(WebConstant.KEY_ENTITY_ID, orderId);
			orderContext.put(WebConstant.KEY_ENTITY_STATUS, OrderStatus.PARTIALDELIVER.getValue());
		} else {
			orderContext.put(WebConstant.KEY_ENTITY_ID, orderId);
			orderContext.put(WebConstant.KEY_ENTITY_STATUS, OrderStatus.SHIPPED.getValue());
		}
		this.orderService.updateByMapContext(orderContext);
		//发送微信消息给用户
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("logistics",logistics);
		map.put("orderGoodsStatus",GoodsOrderStatus.SHIPPED.getValue());
		map.put("orderStatus",orderContext.get(WebConstant.KEY_ENTITY_STATUS));
		
		OrderDeliveryMsg msg = new OrderDeliveryMsg();
		Map<String,String> mapInfo = new HashMap<>();
		mapInfo.put("order_number",order.getOrderNumber());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHZ:mm:ss");
		mapInfo.put("order_time",format.format(new Date().getTime()) );
		mapInfo.put("logistics_company",logistics.getLogisticsName());
		mapInfo.put("courier_number",logistics.getLogisticsNumber());
		Address address = this.addressService.findById(logistics.getReceiptId());
		mapInfo.put("pickup_information","收货人："+address.getReceiver()+";联系方式："+address.getReceiverPhone()+";收货地址："+address.getReceiverAddress());
		msg.setContentMsg(mapInfo);
		UserThirdInfo userThirdInfo = AppBeanInjector.userThirdInfoService.findByUserId(order.getMemberId());
		msg.setTouser(userThirdInfo.getWxOpenId());
		AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);

		return ResultFactory.generateRequestResult(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult deleteLogistics(String id) {
		if (!this.logisticsService.isExist(id)) {
			return ResultFactory.generateResNotFoundResult();
		}
		this.logisticsService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateLogistics(String id, MapContext context) {
		Logistics logistics = this.logisticsService.findById(id);
		if (logistics == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		context.put(WebConstant.KEY_ENTITY_ID, id);
		this.logisticsService.updateByMapContext(context);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findLogisticsById(String id) {
		return ResultFactory.generateRequestResult(this.logisticsService.findById(id));
	}
}
