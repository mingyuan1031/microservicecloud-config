package com.lwxf.newstore.webapp.bizservice.order;


import java.util.List;
import java.util.Set;


import org.apache.ibatis.annotations.Param;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.dto.order.GoodsDto;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface OrderService extends BaseService <Order, String> {

	PaginatedList<Order> selectByFilter(PaginatedFilter paginatedFilter);
	List<Order> findOrderList(String mid);
	List<GoodsDto> findGoodsDtoByOrderIds(Set<String> orderIds);
	PaginatedList<OrderGoods> selectOrderGoodsDtoByFilter(PaginatedFilter paginatedFilter);
	GoodsDto selectGoodsDtoByOrderGoodsId(String id);
	Boolean isOrderExist(String id);

	/**
	 * 不用
	 * @param id
	 * @return
	 */
	Order selectByOrderGoodsId(String id);

}