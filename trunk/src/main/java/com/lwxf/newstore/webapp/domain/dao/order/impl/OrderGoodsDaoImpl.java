package com.lwxf.newstore.webapp.domain.dao.order.impl;


import java.util.List;
import java.util.Map;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.order.OrderGoodsDao;
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
@Repository("orderGoodsDao")
public class OrderGoodsDaoImpl extends BaseDaoImpl<OrderGoods, String> implements OrderGoodsDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<OrderGoods> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<OrderGoods> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<OrderGoods> selectByOrderId(String orderId) {
		String sqlId = this.getNamedSqlId("selectByOrderId");
		return this.getSqlSession().selectList(sqlId,orderId);
	}

	@Override
	public int insertByBatch(List<OrderGoods> list) {
		String sqlId = this.getNamedSqlId("insertByBatch");
		MapContext mapContext=MapContext.newOne();
		mapContext.put("list",list);
		return this.getSqlSession().insert(sqlId,mapContext);
	}

	@Override
	public List<OrderGoods> selectByIds(String[] ids) {
		String sqlId = this.getNamedSqlId("selectByIds");
		return this.getSqlSession().selectList(sqlId,ids);
	}

	@Override
	public List<GoodsDetailsDto> selectGoodsDetailsByOrderId(String orderId) {
		String sqlId = this.getNamedSqlId("selectGoodsDetailsByOrderId");
		return this.getSqlSession().selectList(sqlId,orderId);
	}
}