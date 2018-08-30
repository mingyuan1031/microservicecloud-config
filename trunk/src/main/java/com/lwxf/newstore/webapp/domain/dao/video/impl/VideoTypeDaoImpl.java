package com.lwxf.newstore.webapp.domain.dao.video.impl;


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
import com.lwxf.newstore.webapp.domain.dao.video.VideoTypeDao;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;


/**
 * 功能：
 * 
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 09:36:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("videoTypeDao")
public class VideoTypeDaoImpl extends BaseDaoImpl<VideoType, String> implements VideoTypeDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<VideoType> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<VideoType> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<VideoType> findByFilter(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("findByFilter");
		return this.getSqlSession().selectList(sqlId,mapContext);
	}

	@Override
	public boolean isExistByName(String name) {
		String sqlId = this.getNamedSqlId("isExistByName");
		return this.getSqlSession().selectOne(sqlId,name);
	}

}