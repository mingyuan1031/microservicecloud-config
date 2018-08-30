package com.lwxf.newstore.webapp.facade.order;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;


/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/29 19:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface OrderGoodsFacade extends BaseFacade {
	RequestResult createOrderGoods(OrderGoods orderGoods);
	RequestResult deleteOrderGoods(String id);
	RequestResult updateOrderGoodsStatus(String id,Integer status);
	RequestResult findOrderGoodsById(String id);
//	public  RequestResult sendOutGoods(String id, MapContext map);


}
