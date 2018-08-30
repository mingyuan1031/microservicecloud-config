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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsExtendDao;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsExtendDao")
public class GoodsExtendDaoImpl extends BaseDaoImpl<GoodsExtend, String> implements GoodsExtendDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsExtend> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsExtend> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public boolean isExistByLikeOptions(String id) {
		String sqlId = this.getNamedSqlId("isExistByLikeOptions");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public List<GoodsExtend> findByGoodsId(String goodsId) {
		String sqlId = this.getNamedSqlId("findByGoodsId");
		return this.getSqlSession().selectList(sqlId,goodsId);
	}

	@Override
	public int removeDefaults(String goodsId) {
		String sqlId = this.getNamedSqlId("removeDefaults");
		return this.getSqlSession().update(sqlId,goodsId);
	}

	@Override
	public boolean isExistByDefaults(String goodsId) {
		String sqlId = this.getNamedSqlId("isExistByDefaults");
		return this.getSqlSession().selectOne(sqlId,goodsId);
	}

	@Override
	public boolean isExistByGoodsIdAndExtendId(String goodsId, String extendId) {
		String sqlId = this.getNamedSqlId("isExistByGoodsIdAndExtendId");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goodsId",goodsId);
		mapContext.put("extendId",extendId);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}


}