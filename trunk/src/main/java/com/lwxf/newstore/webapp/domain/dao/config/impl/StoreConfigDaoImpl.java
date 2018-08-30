package com.lwxf.newstore.webapp.domain.dao.config.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.config.StoreConfigDao;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:12
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("storeConfigDao")
public class StoreConfigDaoImpl extends BaseDaoImpl<StoreConfig, String> implements StoreConfigDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<StoreConfig> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<StoreConfig> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public StoreConfig findOneByLimit() {
		String sqlId = this.getNamedSqlId("findOneByLimit");
		return this.getSqlSession().selectOne(sqlId);
	}

	@Override
	public StoreConfig findByCompanyId(String companyId) {
		String sqlId = this.getNamedSqlId("findByCompanyId");
		return this.getSqlSession().selectOne(sqlId, companyId);
	}
}