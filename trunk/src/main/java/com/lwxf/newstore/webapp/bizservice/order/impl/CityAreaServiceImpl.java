package com.lwxf.newstore.webapp.bizservice.order.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.order.CityAreaDao;
import com.lwxf.newstore.webapp.bizservice.order.CityAreaService;
import com.lwxf.newstore.webapp.domain.entity.order.CityArea;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("cityAreaService")
public class CityAreaServiceImpl extends BaseServiceImpl<CityArea, String, CityAreaDao> implements CityAreaService {


	@Resource

	@Override	public void setDao( CityAreaDao cityAreaDao) {
		this.dao = cityAreaDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<CityArea> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}
	@Override
	public List<CityArea> selectCityAreaListByLevel(int levelType) {

		return this.dao.selectCityAreaListByLevel(levelType);
	}

	@Override
	public List<CityArea> selectCityAreaListByParentId(String parentId) {
		return this.dao.selectCityAreaListByParentId(parentId);
	}
}