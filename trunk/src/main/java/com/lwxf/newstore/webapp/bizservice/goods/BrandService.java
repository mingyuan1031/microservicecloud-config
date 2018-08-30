package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BrandService extends BaseService <Brand, String> {

	PaginatedList<Brand> selectByFilter(PaginatedFilter paginatedFilter);
	List<Brand> findAll();
	Brand findByBrandName(String name);
	List<Brand> findListByParams(MapContext mapContext);
	List<Brand> findLikeName(String name);
}