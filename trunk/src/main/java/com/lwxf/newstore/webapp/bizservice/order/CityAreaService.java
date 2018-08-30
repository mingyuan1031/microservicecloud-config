package com.lwxf.newstore.webapp.bizservice.order;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.order.CityArea;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface CityAreaService extends BaseService <CityArea, String> {

	PaginatedList<CityArea> selectByFilter(PaginatedFilter paginatedFilter);


	/**
	 * 根据行政级别查询行政区域
	 * @param levelType
	 * @return
	 */
	List<CityArea> selectCityAreaListByLevel(int levelType);

	/**
	 * 根据父ID查询行政区域
	 * @param parentId
	 * @return
	 */
	List<CityArea> selectCityAreaListByParentId(String parentId);
}