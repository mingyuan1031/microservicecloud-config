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
import com.lwxf.newstore.webapp.domain.dao.news.NewsTypeDao;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:35:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("newsTypeDao")
public class NewsTypeDaoImpl extends BaseDaoImpl<NewsType, String> implements NewsTypeDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<NewsType> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<NewsType> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Boolean isExistName(String name) {
		String sqlId = this.getNamedSqlId("isExistName");
		return this.getSqlSession().selectOne(sqlId,name);
	}

	@Override
	public List<NewsType> findByParentId(String id) {
		String sqlId = this.getNamedSqlId("findByParentId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public Boolean findByParentIds(List types) {
		String sqlId =this.getNamedSqlId("findByParentIds");
		return this.getSqlSession().selectOne(sqlId,types);
	}

	@Override
	public List<NewsType> findAll() {
		String sqlId = this.getNamedSqlId("findAll");
		return this.getSqlSession().selectList(sqlId);
	}

}