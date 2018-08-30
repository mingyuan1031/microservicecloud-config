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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsTypeDao;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("goodsTypeDao")
public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsType, String> implements GoodsTypeDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsType> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<GoodsType> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<GoodsType> findAll() {
		String sqlId = this.getNamedSqlId("findAll");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public List<GoodsType> selectByParentId(String id) {
		String sqlId = this.getNamedSqlId("selectByParentId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public List<GoodsType> findByParentId(String id) {
		String sqlId = this.getNamedSqlId("findByParentId");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public GoodsType findByNameAndParentId(String name,String parentId) {
		String sqlId = this.getNamedSqlId("findByNameAndParentId");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("name",name);
		mapContext.put("parentId",parentId);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}

	@Override
	public GoodsType findByName(String name) {
		String sqlId = this.getNamedSqlId("findByName");
		return this.getSqlSession().selectOne(sqlId,name);
	}


}