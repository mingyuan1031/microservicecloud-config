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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsSpecDao;
import com.lwxf.newstore.webapp.domain.dto.goods.SpecOptionList;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsSpecDao")
public class GoodsSpecDaoImpl extends BaseDaoImpl<GoodsSpec, String> implements GoodsSpecDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsSpec> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsSpec> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<GoodsSpec> findAll() {
		String sqlId = this.getNamedSqlId("findAll");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public List<GoodsSpec> findByTypeId(String id) {
		String sqlId = this.getNamedSqlId("findByTypeId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public boolean isExistByName(String goodsTypeId,String name) {
		String sqlId = this.getNamedSqlId("isExistByName");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goodsTypeId",goodsTypeId);
		mapContext.put("name",name);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}

	@Override
	public int deleteByTypeId(String typeId) {
		String sqlId = this.getNamedSqlId("deleteByTypeId");
		return this.getSqlSession().delete(sqlId,typeId);
	}
}