package com.lwxf.newstore.webapp.domain.dao.user.impl;


import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.user.UserExtraDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Component("userExtraDao")
public class UserExtraDaoImpl extends BaseDaoImpl<UserExtra, String> implements UserExtraDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UserExtra> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
//		MapContext filterItems = paginatedFilter.getFilters();
		//
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<UserExtra> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public int updateUserExtra(UserExtra userExtra) {
		String sqlId = this.getNamedSqlId("updateUserExtra");
		return this.getSqlSession().update(sqlId, userExtra);
	}

}