package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
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
@IBatisSqlTarget
public interface OrderGoodsDao extends BaseDao<OrderGoods, String> {

	PaginatedList<OrderGoods> selectByFilter(PaginatedFilter paginatedFilter);

	List<OrderGoods> selectByOrderId(String orderId);
	int insertByBatch(List<OrderGoods> list);
	List<OrderGoods> selectByIds(String [] ids);
	List<GoodsDetailsDto> selectGoodsDetailsByOrderId(String orderId);

}