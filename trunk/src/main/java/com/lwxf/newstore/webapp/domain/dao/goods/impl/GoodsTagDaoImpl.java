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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsTagDao;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsTagsInfo;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsTag;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsTagDao")
public class GoodsTagDaoImpl extends BaseDaoImpl<GoodsTag, String> implements GoodsTagDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsTag> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsTag> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<GoodsTag> findAll() {
		String sqlId = this.getNamedSqlId("findAll");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public boolean isExistByTagId(String tagId) {
		String sqlId = this.getNamedSqlId("isExistByTagId");
		return this.getSqlSession().selectOne(sqlId,tagId);
	}

	@Override
	public int deleteByGoodsIdAndTagId(String goodsId, String tagId) {
		String sqlId = this.getNamedSqlId("deleteByGoodsIdAndTagId");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goodsId",goodsId);
		mapContext.put("tagId",tagId);
		return this.getSqlSession().delete(sqlId,mapContext);
	}

	@Override
	public List<GoodsTagsInfo> findByGoodsId(String goodsId) {
		String sqlId = this.getNamedSqlId("findByGoodsId");
		return this.getSqlSession().selectList(sqlId,goodsId);
	}

	@Override
	public boolean isExistByGoodsIdAndTagId(String goodsId, String tagId) {
		String sqlId = this.getNamedSqlId("isExistByGoodsIdAndTagId");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goodsId",goodsId);
		mapContext.put("tagId",tagId);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}

	@Override
	public int deleteByGoodsId(String goodsId) {
		String sqlId = this.getNamedSqlId("deleteByGoodsId");
		return this.getSqlSession().delete(sqlId,goodsId);
	}
}