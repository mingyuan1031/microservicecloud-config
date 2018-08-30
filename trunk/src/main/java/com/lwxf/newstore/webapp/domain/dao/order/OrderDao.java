package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
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
@IBatisSqlTarget
public interface OrderDao extends BaseDao<Order, String> {

	PaginatedList<Order> selectByFilter(PaginatedFilter paginatedFilter);
	List<Order> selectOrderByMemberId(String mid);
	List<GoodsDto> selectGoodsDtoListByOrderIds(@Param("orderIds") Set<String> orderIds);
	PaginatedList<OrderGoods> selectOrderGoodsDtoByFilter(PaginatedFilter paginatedFilter);
	GoodsDto selectGoodsDtoByOrderGoodsId(String id);
	Boolean isOrderExist(String id);
	Order selectByOrderGoodsId(String id);

}