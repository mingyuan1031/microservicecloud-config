package com.lwxf.newstore.webapp.domain.dao.quickshare.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogDao;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:24
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("microblogDao")
public class MicroblogDaoImpl extends BaseDaoImpl<Microblog, String> implements MicroblogDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Microblog> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Microblog> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })



	@Override
	public PaginatedList<Microblog> findByType(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("findByType");
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Microblog> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Microblog findById(String id) {
		String sqlId = this.getNamedSqlId("findById");
		return this.getSqlSession().selectOne(sqlId, id);
	}

	@Override
	public int deleteByIdAndUserId(Map<String, String> map) {
		String sqlId = this.getNamedSqlId("deleteByIdAndUserId");
		return this.getSqlSession().delete(sqlId,map);
	}

	@Override
	public Integer findCountByStatus() {
		String sqlId = this.getNamedSqlId("findCountByStatus");
		return this.getSqlSession().selectOne(sqlId);
	}

	@Override
	public PaginatedList<Microblog> findMicroblogByMemberId(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("findMicroblogByMemberId");
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Microblog> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}


}