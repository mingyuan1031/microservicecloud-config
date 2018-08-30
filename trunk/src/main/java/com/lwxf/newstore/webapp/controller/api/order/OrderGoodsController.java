package com.lwxf.newstore.webapp.controller.api.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.order.GoodsOrderStatus;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.order.OrderFacade;
import com.lwxf.newstore.webapp.facade.order.OrderGoodsFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/30 15:46
 * @version：2018 Version	：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/orderGoods", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class  OrderGoodsController {
	private static final Logger logger = LoggerFactory.getLogger(OrderGoodsController.class);
	@Resource(name = "orderGoodsFacade")
	private OrderGoodsFacade orderGoodsFacade;



	/**
	 * 删除商品订单
	 * @param id
	 * @return
	 */
	@DeleteMapping(value ="/{id}")
	public  RequestResult deleteOrderGoodsById(@PathVariable("id") String id)
	{
		return  this.orderGoodsFacade.deleteOrderGoods(id);
	}

	/**
	 * 修改商品订单状态
	 * @param id
	 * @param status
	 * @return
	 */
	@PutMapping(value ="/{id}/status/{status}")
	public RequestResult updateOrderGoods (@PathVariable("id") String id , @PathVariable("status") Integer status)
	{

		if (!GoodsOrderStatus.validValue(status)) {
			MapContext mapContext = MapContext.newOne();
			mapContext.put("status",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,mapContext);
		}

		return  this.orderGoodsFacade.updateOrderGoodsStatus(id,status);
	}

	/**
	 * 查询物流订单（暂时没用）
	 * @param id
	 * @return
	 */
	@GetMapping(value ="/{id}")
	public RequestResult findOrderGoodsById(@PathVariable("id") String id)
	{
		return this.orderGoodsFacade.findOrderGoodsById(id);
	}




}
