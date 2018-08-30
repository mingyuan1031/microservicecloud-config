package com.lwxf.newstore.webapp.domain.dao.goods.impl;


import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsCommentDao;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsComments;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsCommentDao")
public class GoodsCommentDaoImpl extends BaseDaoImpl<GoodsComment, String> implements GoodsCommentDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsComment> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsComment> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}


	@Override
	public PaginatedList<GoodsComments> findByMapper(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("findByMapper");
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsComments> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public int deleteByParentId(String parentId) {
		String sqlId = this.getNamedSqlId("deleteByParentId");
		return this.getSqlSession().delete(sqlId,parentId);
	}

	@Override
	public Map<String, Object> findCountById(String id) {
		String sqlId = this.getNamedSqlId("findCountById");
		return this.getSqlSession().selectOne(sqlId,id);
	}

}