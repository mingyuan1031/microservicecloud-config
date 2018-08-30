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
import com.lwxf.newstore.webapp.domain.dao.base.BaseNoIdDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogPraiseDao;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.domain.entity.user.User;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:25
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("microblogPraiseDao")
public class MicroblogPraiseDaoImpl extends BaseNoIdDaoImpl<MicroblogPraise> implements MicroblogPraiseDao {

	@Override
	public MicroblogPraise selectByMemberIdAndMicroblogId(String memberId, String microblogId) {
		String sqlId = this.getNamedSqlId("selectByMemberIdAndMicroblogId");
		//
		Map<String, Object> params = this.newParamMap();
		params.put("memberId", memberId);
		params.put("microblogId", microblogId);
		//
		return this.getSqlSession().selectOne(sqlId, params);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<MicroblogPraise> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination());
		PageList<MicroblogPraise> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Integer findCountByMicroblogId(String microblogId) {
		String sqlId = this.getNamedSqlId("findCountByMicroblogId");
		return this.getSqlSession().selectOne(sqlId, microblogId);
	}

	@Override
	public List<MicroblogPraise> findMemberId(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("findMemberId");
		return this.getSqlSession().selectList(sqlId,mapContext);
	}
	@Override
	public int deleteByBlogId(String blogId) {
		String sqlId = this.getNamedSqlId("deleteByBlogId");
		return this.getSqlSession().delete(sqlId,blogId);
	}

	@Override
	public List<MicroblogPraise> findByBlogIds(Set<String> blogIds) {
		String sqlId = this.getNamedSqlId("findByBlogIds");
		return this.getSqlSession().selectList(sqlId, blogIds);
	}

	@Override
	public List<MicroblogPraise> findByMemberId(String memberId) {
		String sqlId = this.getNamedSqlId("findByMemberId");
		return this.getSqlSession().selectList(sqlId, memberId);
	}
}