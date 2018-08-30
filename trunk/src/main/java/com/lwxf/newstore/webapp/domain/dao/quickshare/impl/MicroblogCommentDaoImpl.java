package com.lwxf.newstore.webapp.domain.dao.quickshare.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogCommentDao;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;

import javax.print.DocFlavor;

import org.apache.catalina.mapper.Mapper;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:25
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("microblogCommentDao")
public class MicroblogCommentDaoImpl extends BaseDaoImpl<MicroblogComment, String> implements MicroblogCommentDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<MicroblogComment> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<MicroblogComment> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Integer findCountByMicroblogId(String microblogId) {
		String sqlId = this.getNamedSqlId("findCountByMicroblogId");
		return this.getSqlSession().selectOne(sqlId, microblogId);
	}
	@Override
	public List<MicroblogComment> selectMicroblogCommentByBlogId(String blogId) {
		String sqlId = this.getNamedSqlId("selectMicroblogCommentByBlogId");
		return this.getSqlSession().selectList(sqlId,blogId);
	}

	public List<MicroblogComment> selectMicroblogCommentByBlogIds(Set<String> blogIds) {
		String sqlId = this.getNamedSqlId("selectMicroblogCommentByBlogIds");
		return this.getSqlSession().selectList(sqlId,blogIds);
	}

	@Override
	public int deleteMicroblogCommentByBlogId(String blogId) {
		String sqlId = this.getNamedSqlId("deleteMicroblogCommentByBlogId");
		return this.getSqlSession().delete(sqlId,blogId);
	}

	@Override
	public List<MicroblogComment> findByParentIds(List<String> parentIds) {
		String sqlId = this.getNamedSqlId("findByParentIds");
		return this.getSqlSession().selectList(sqlId,parentIds);
	}

	@Override
	public List<MicroblogComment> findByParentId(String parentId) {
		String sqlId = this.getNamedSqlId("findByParentId");
		return this.getSqlSession().selectList(sqlId,parentId);
	}

	@Override
	public int updateByParentId(String parentId) {
		String sqlId = this.getNamedSqlId("updateByParentId");
		return this.getSqlSession().update(sqlId, parentId);
	}
}