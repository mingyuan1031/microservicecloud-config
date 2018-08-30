package com.lwxf.newstore.webapp.bizservice.order.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.domain.dao.order.OrderDao;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.domain.dto.order.GoodsDto;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order, String, OrderDao> implements OrderService {


	@Resource

	@Override	public void setDao( OrderDao orderDao) {
		this.dao = orderDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Order> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<Order> findOrderList(String mid) {
		return this.dao.selectOrderByMemberId(mid);
	}

	@Override
	public int add(Order entity) {
		// 设置订单编号
		entity.setOrderNumber(AppBeanInjector.uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.ORDER_NO));
		return this.dao.insert(entity);
	}

	@Override
	public List<GoodsDto> findGoodsDtoByOrderIds(Set<String> orderIds) {
		return this.dao.selectGoodsDtoListByOrderIds(orderIds);
	}
	@Override
	public PaginatedList<OrderGoods> selectOrderGoodsDtoByFilter(PaginatedFilter paginatedFilter) {
		return this.dao.selectOrderGoodsDtoByFilter(paginatedFilter);
	}

	@Override
	public GoodsDto selectGoodsDtoByOrderGoodsId(String id) {
		return this.dao.selectGoodsDtoByOrderGoodsId(id);
	}

	@Override
	public Boolean isOrderExist(String id) {
		return this.dao.isOrderExist(id);
	}

	@Override
	public Order selectByOrderGoodsId(String id) {
		return this.dao.selectByOrderGoodsId(id);
	}
}