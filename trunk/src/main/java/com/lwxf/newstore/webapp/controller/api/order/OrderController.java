package com.lwxf.newstore.webapp.controller.api.order;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;


import com.lwxf.newstore.webapp.common.enums.order.OrderStatus;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.cart.CartFacade;
import com.lwxf.newstore.webapp.facade.order.OrderFacade;
import com.lwxf.newstore.webapp.facade.order.WeiXinPayFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/26 15:36
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/orders", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "weiXinPayFacade")
	private WeiXinPayFacade weiXinPayFacade;

	/**
	 * 无用(废弃)
	 * @param context
	 * @return
	 */
	@PutMapping
	public RequestResult updateOrder(@RequestBody(required = true) MapContext context)
	{

		if (context.size()==0||context.get("id").equals(null))
		{
			return ResultFactory.generateResNotFoundResult();
		}


		return  this.orderFacade.updateOrderMapContext(context);

	}
	/**
	 * 创建订单
	 * @param params
	 * @return
	 */
	@PostMapping
	public RequestResult createOrder(@RequestBody(required = true) Map<String,Object> params)
	{

		if (params.size()==0||params.get("ids").equals(null)||StringUtils.isBlank(params.get("freight").toString()))
		{
			return ResultFactory.generateResNotFoundResult();
		}


		return  this.orderFacade.createOrder(params);

	}
	@PostMapping("/purchase")
	public  RequestResult addOrder(@RequestBody(required = true) Map<String,Object> params)
	{

		return  this.orderFacade.addOrder(params);
	}


	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @return
	 */
	@PutMapping(value ="/{oid}/status/{status}")
	public  RequestResult updateOrderById(@PathVariable("oid") String oid,@PathVariable("status") Integer status )
	{
		boolean validValue = OrderStatus.validValue(status);
		if (!validValue)
		{
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT,AppBeanInjector.i18nUtil.getMessage("VALIDATE_ILLEGAL_ARGUMENT"));
		}

		return this.orderFacade.updateOrder(oid,status);
	}

	/**
	 * 获取订单详情（前端）
	 * @param oid
	 * @return
	 */
	@GetMapping(value = "/{oid}")
	public RequestResult getOrderById(@PathVariable("oid") String oid)
	{
		return  	this.orderFacade.findOrderById(oid);
	}

	/**
	 * 获取订单列表
	 * @param pageNum
	 * @param pageSize
	 * @param status
	 * @param orderNumber
	 * @return
	 */
	@GetMapping
	public RequestResult geOrderList(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
										@RequestParam(required = false,defaultValue = "50") Integer pageSize,
										@RequestParam(required = false)Integer[] status, @RequestParam(required = false) String orderNumber) {
		return  this.orderFacade.selsectOrderGoodsDtoList(pageNum,pageSize,this.createParamsForFindList(status,orderNumber));

	}
	private MapContext createParamsForFindList(Integer[] status,String orderNumber){
		MapContext params = MapContext.newOne();

		if(status != null){
			params.put(WebConstant.KEY_ENTITY_STATUS,status);
		}
		if(LwxfStringUtils.isNotBlank(orderNumber)){
			params.put("orderNumber",orderNumber);
		}

		return params;
	}

	/**获取商品订单详情
	 * id为orderGoodsID
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}/details")
	public  RequestResult getOrderDetails(@PathVariable("id") String id)
	{
		return  this.orderFacade.getOrderDetails(id);
	}

	/**
	 * 微信统一下单
	 * id为orderGoodsID
	 * @param orderId
	 * @return
	 */
	@PostMapping(value = "/{orderId}/mppay")
	public  RequestResult postMpPay(@PathVariable String orderId){
		return weiXinPayFacade.mpPay(orderId);
	}

	/**
	 * 关闭订单
	 * id为orderGoodsID
	 * @param orderId
	 * @return
	 */
	@PutMapping(value = "/{orderId}/close")
	public  RequestResult putClose(@PathVariable String orderId){
		return weiXinPayFacade.closeOrder(orderId);
	}

	/**
	 * 创建订单的支付记录
	 * id为orderGoodsID
	 * @param orderId
	 * @return
	 */
	@PostMapping(value = "/{orderId}/paidrecords")
	public  RequestResult postPaidRecords(@PathVariable String orderId){
		return weiXinPayFacade.createPaidRecord(orderId);
	}
}
