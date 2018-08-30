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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsDao;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsDefault;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsDao")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, String> implements GoodsDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Goods> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Goods> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public PaginatedList<GoodsDefault> findGoodsDefaultByParamsForPaging(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("findGoodsDefaultByParamsForPaging");
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsDefault> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public boolean isExistByBrandId(String brandId) {
		String sqlId = this.getNamedSqlId("isExistByBrandId");
		return this.getSqlSession().selectOne(sqlId,brandId);
	}

	@Override
	public boolean isExistByGoodsTypeId(String id) {
		String sqlId = this.getNamedSqlId("isExistByGoodsTypeId");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public List<GoodsDefault> findForHomePage() {
		String sqlId = this.getNamedSqlId("findForHomePage");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public GoodsDefault findGoodsInfo(String id) {
		String sqlId = this.getNamedSqlId("findGoodsInfo");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public int updateUpdator(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("updateUpdator");
		return this.getSqlSession().update(sqlId,mapContext);
	}

}