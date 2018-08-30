package com.lwxf.newstore.webapp.domain.dao.order.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.order.OrderDao;
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
@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Order> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Order> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<Order> selectOrderByMemberId(String mid) {
		String sqlId = this.getNamedSqlId("selectOrderByMemberId");

		return   this.getSqlSession().selectList(sqlId,mid);
	}

	@Override
	public List<GoodsDto> selectGoodsDtoListByOrderIds(Set<String> orderIds) {
		String sqlId = this.getNamedSqlId("selectGoodsDtoListByOrderIds");
		Map<String, Object> orderIdsParam = new HashMap<String, Object>();
		orderIdsParam.put("orderIds",orderIds);
		return this.getSqlSession().selectList(sqlId,orderIdsParam);
	}
	@Override
	public PaginatedList<OrderGoods> selectOrderGoodsDtoByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectOrderGoodsDtoByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<OrderGoods> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public GoodsDto selectGoodsDtoByOrderGoodsId(String id) {
		String sqlId = this.getNamedSqlId("selectGoodsDtoByOrderGoodsId");
		return this.getSqlSession().selectOne(sqlId,id);

	}

	@Override
	public Boolean isOrderExist(String id) {
		String sqlId = this.getNamedSqlId("isOrderExist");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public Order selectByOrderGoodsId(String id) {
		String sqlId = this.getNamedSqlId("selectByOrderGoodsId");
		return this.getSqlSession().selectOne(sqlId,id);
	}
}