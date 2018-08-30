package com.lwxf.newstore.webapp.facade.order;

import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.CityArea;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/21 10:34
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface CityAreaFacade extends BaseFacade {
	CityArea findCityAreaById(String id);
	CityArea AddCityArea(CityArea cityArea);
	int updateCityArea(CityArea cityArea);
	RequestResult selectCityAreaList(Integer pageNum,Integer pageSize,MapContext params);
	RequestResult selectCityAreaListByLevel(int levelType);
	RequestResult selectCityAreaListByParentId(String parentId);

}
