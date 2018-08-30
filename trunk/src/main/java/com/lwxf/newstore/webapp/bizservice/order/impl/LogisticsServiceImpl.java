package com.lwxf.newstore.webapp.bizservice.order.impl;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.List;
import java.util.Set;

import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.order.LogisticsDao;
import com.lwxf.newstore.webapp.bizservice.order.LogisticsService;
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
@Service("logisticsService")
public class LogisticsServiceImpl extends BaseServiceImpl<Logistics, String, LogisticsDao> implements LogisticsService {


	@Resource

	@Override	public void setDao( LogisticsDao logisticsDao) {
		this.dao = logisticsDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Logistics> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<LogisticsAddressDto> getLogisticsAddressDtoByOrderIds(Set<String> orderIds) {
		return this.dao.selectLogisticsAdDtosByOrderIds(orderIds);
	}

	@Override
	public LogisticsAddressDto selectLogisticsAdDtosByOrderGoodsId(String id) {
		return this.dao.selectLogisticsAdDtosByOrderGoodsId(id);
	}


	@Override
	public List<LogisticsDto> selectLogisticsDtoByOrderId(String orderId) {
		return this.dao.selectLogisticsDtoByOrderId(orderId);
	}

	@Override
	public Logistics selectByOrderGoodsId(String orderGoodsId) {
		return this.dao.selectByOrderGoodsId(orderGoodsId);
	}
}