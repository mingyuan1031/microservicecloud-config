package com.lwxf.newstore.webapp.bizservice.order;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.dto.order.GoodsDetailsDto;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-29 10:49:51
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface OrderGoodsService extends BaseService <OrderGoods, String> {

	PaginatedList<OrderGoods> selectByFilter(PaginatedFilter paginatedFilter);
	List<OrderGoods> selectByOrderId(String orderId);
	int insertByBatch(List<OrderGoods> list);
	List<OrderGoods> selectByIds(String [] ids);
	List<GoodsDetailsDto> selectGoodsDetailsByOrderId(String orderId);


}