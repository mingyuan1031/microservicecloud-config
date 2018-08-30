package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;
import java.util.Set;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
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
@IBatisSqlTarget
public interface LogisticsDao extends BaseDao<Logistics, String> {

	PaginatedList<Logistics> selectByFilter(PaginatedFilter paginatedFilter);
	List<LogisticsAddressDto> selectLogisticsAdDtosByOrderIds(Set<String> orderIds);
	LogisticsAddressDto selectLogisticsAdDtosByOrderGoodsId(String id);
	List<LogisticsDto> selectLogisticsDtoByOrderId(String orderId);
	Logistics selectByOrderGoodsId(String orderGoodsId);

}