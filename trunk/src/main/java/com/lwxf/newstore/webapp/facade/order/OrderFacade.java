package com.lwxf.newstore.webapp.facade.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/26 11:03
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface OrderFacade extends BaseFacade {
	RequestResult createOrder(Map<String,Object> params );

	RequestResult deleteOrder(String oid);

	RequestResult updateOrder(String oid, Integer status);

	RequestResult findOrderById(String oid);

	RequestResult findOrderList(Integer pageNum, Integer pageSize, MapContext params);

	RequestResult findOrderListByMemberId(String mid);
	RequestResult selsectOrderGoodsDtoList(Integer pageNum,Integer pageSize,MapContext params);
	RequestResult findOrderDetailsByOrderId(String ogid);
	RequestResult getOrderDetails(String id);


	RequestResult addOrder( Map<String,Object> params);

	RequestResult getPaidrecords(String id);

	RequestResult updateOrderMapContext(MapContext context);
}
