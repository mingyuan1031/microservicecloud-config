package com.lwxf.newstore.webapp.bizservice.order.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.order.OrderGoodsDao;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
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
@Service("orderGoodsService")
public class OrderGoodsServiceImpl extends BaseServiceImpl<OrderGoods, String, OrderGoodsDao> implements OrderGoodsService {


	@Resource

	@Override	public void setDao( OrderGoodsDao orderGoodsDao) {
		this.dao = orderGoodsDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<OrderGoods> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<OrderGoods> selectByOrderId(String orderId) {
		return this.dao.selectByOrderId(orderId);
	}

	@Override
	public int insertByBatch(List<OrderGoods> list) {

		return this.dao.insertByBatch(list);
	}

	@Override
	public List<OrderGoods> selectByIds(String[] ids) {
		return this.dao.selectByIds(ids);
	}

	@Override
	public List<GoodsDetailsDto> selectGoodsDetailsByOrderId(String orderId) {
		return this.dao.selectGoodsDetailsByOrderId(orderId);
	}
}