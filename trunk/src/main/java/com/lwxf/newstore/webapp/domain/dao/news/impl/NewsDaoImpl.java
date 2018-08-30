package com.lwxf.newstore.webapp.domain.dao.news.impl;


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
import com.lwxf.newstore.webapp.domain.dao.news.NewsDao;
import com.lwxf.newstore.webapp.domain.dto.news.NewsDto;
import com.lwxf.newstore.webapp.domain.entity.news.News;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:35:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoImpl<News, String> implements NewsDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<News> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<News> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Boolean findByNesTypeId(String id) {
		String sqlId = this.getNamedSqlId("findByNesTypeId");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public PaginatedList<NewsDto> findByMapper(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("findByMapper");
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<NewsDto> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public PaginatedList<NewsDto> findAll(PaginatedFilter filter) {
		String sqlId = this.getNamedSqlId("findAll");
		// 过滤查询参数
		PageBounds pageBounds = this.toPageBounds(filter.getPagination(),filter.getSorts());
		PageList<NewsDto> pageList = (PageList) this.getSqlSession().selectList(sqlId,filter.getFilters(),pageBounds);
		return this.toPaginatedList(pageList);
	}


}