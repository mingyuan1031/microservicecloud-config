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
import com.lwxf.newstore.webapp.domain.dao.goods.SpecOptionDao;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("specOptionDao")
public class SpecOptionDaoImpl extends BaseDaoImpl<SpecOption, String> implements SpecOptionDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<SpecOption> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<SpecOption> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<SpecOption> findAll() {
		String sqlId = this.getNamedSqlId("selectByFilter");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public List<SpecOption> findBySpecId(String id) {
		String sqlId = this.getNamedSqlId("findBySpecId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public boolean isExistByName(String goodsSpecId,String name) {
		String sqlId = this.getNamedSqlId("isExistByName");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goodsSpecId",goodsSpecId);
		mapContext.put("name",name);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}

	@Override
	public int deleteBySpecId(String id) {
		String sqlId = this.getNamedSqlId("deleteBySpecId");
		return this.getSqlSession().delete(sqlId,id);
	}

	@Override
	public int findByIds(String[] ids) {
		String sqlId = this.getNamedSqlId("findByIds");
		return this.getSqlSession().selectOne(sqlId,ids);
	}

	@Override
	public int findSpecByOptionids(String[] ids) {
		String sqlId = this.getNamedSqlId("findSpecByOptionids");
		return this.getSqlSession().selectOne(sqlId,ids);
	}

	@Override
	public int findTypeByOptions(String[] ids) {
		String sqlId = this.getNamedSqlId("findTypeByOptions");
		return this.getSqlSession().selectOne(sqlId,ids);
	}
}