package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface BrandDao extends BaseDao<Brand, String> {

	PaginatedList<Brand> selectByFilter(PaginatedFilter paginatedFilter);
	List<Brand> findAll();
	Brand findByBrandName(String name);
	List<Brand> findListByParams(MapContext mapContext);
	List<Brand> findLikeName(String name);
}