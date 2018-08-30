package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.order.CityArea;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface CityAreaDao extends BaseDao<CityArea, String> {

	/**
	 * 分页查询
	 * @param paginatedFilter
	 * @return
	 */
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
	List<CityArea> selectCityAreaListByParentId(String parentId );
}