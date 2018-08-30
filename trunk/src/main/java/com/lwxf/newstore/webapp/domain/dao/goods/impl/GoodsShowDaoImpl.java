package com.lwxf.newstore.webapp.domain.dao.goods.impl;


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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsShowDao;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsShow;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-22 15:30:32
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsShowDao")
public class GoodsShowDaoImpl extends BaseDaoImpl<GoodsShow, String> implements GoodsShowDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsShow> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsShow> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<GoodsShow> findByGoodsId(String id) {
		String sqlId = this.getNamedSqlId("findByGoodsId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public int removeDefaults(String goodsId) {
		String sqlId = this.getNamedSqlId("removeDefaults");
		//莫名其妙 修改必须有参数
		return this.getSqlSession().update(sqlId,goodsId);
	}

	@Override
	public int setDefault(String id) {
		String sqlId = this.getNamedSqlId("setDefault");
		return this.getSqlSession().update(sqlId,id);
	}

}