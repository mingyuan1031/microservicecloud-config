package com.lwxf.newstore.webapp.domain.dao.order.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.order.LogisticsDao;
import com.lwxf.newstore.webapp.domain.dto.order.LogisticsAddressDto;
import com.lwxf.newstore.webapp.domain.dto.order.LogisticsDto;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("logisticsDao")
public class LogisticsDaoImpl extends BaseDaoImpl<Logistics, String> implements LogisticsDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Logistics> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Logistics> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<LogisticsAddressDto> selectLogisticsAdDtosByOrderIds(Set<String> orderIds) {
		String sqlId = this.getNamedSqlId("selectLogisticsAdDtosByOrderIds");
		Map<String, Object> orderIdsParam = new HashMap<String, Object>();
		orderIdsParam.put("orderIds",orderIds);
		return this.getSqlSession().selectList(sqlId,orderIdsParam);
	}

	@Override
	public LogisticsAddressDto selectLogisticsAdDtosByOrderGoodsId(String id) {
		String sqlId = this.getNamedSqlId("selectLogisticsAdDtosByOrderGoodsId");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public List<LogisticsDto> selectLogisticsDtoByOrderId(String orderId) {
		String sqlId = this.getNamedSqlId("selectLogisticsDtoByOrderId");
		return this.getSqlSession().selectList(sqlId,orderId);
	}

	@Override
	public Logistics selectByOrderGoodsId(String orderGoodsId) {
		String sqlId = this.getNamedSqlId("selectByOrderGoodsId");
		return this.getSqlSession().selectOne(sqlId,orderGoodsId);
	}
}