package com.lwxf.newstore.webapp.bizservice.order;


import java.util.List;
import java.util.Set;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
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
public interface LogisticsService extends BaseService <Logistics, String> {

	PaginatedList<Logistics> selectByFilter(PaginatedFilter paginatedFilter);
	List<LogisticsAddressDto> getLogisticsAddressDtoByOrderIds(Set<String> orderIds);
	LogisticsAddressDto selectLogisticsAdDtosByOrderGoodsId(String id);
	List<LogisticsDto> selectLogisticsDtoByOrderId(String orderId);
	Logistics selectByOrderGoodsId(String orderGoodsId);

}