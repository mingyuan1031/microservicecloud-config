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
import com.lwxf.newstore.webapp.domain.dao.goods.BrandDao;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("brandDao")
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Brand> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Brand> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}
	@Override
	public List<Brand> findAll() {
		String sqlId = this.getNamedSqlId("findAll");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public Brand findByBrandName(String name) {
		String sqlId = this.getNamedSqlId("findByBrandName");
		return this.getSqlSession().selectOne(sqlId,name);
	}

	@Override
	public List<Brand> findListByParams(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("findListByParams");
		return this.getSqlSession().selectList(sqlId,mapContext);
	}

	@Override
	public List<Brand> findLikeName(String name) {
		String sqlId = this.getNamedSqlId("findLikeName");
		return this.getSqlSession().selectList(sqlId,name);
	}


}