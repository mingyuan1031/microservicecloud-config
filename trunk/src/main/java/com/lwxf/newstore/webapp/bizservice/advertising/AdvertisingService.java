package com.lwxf.newstore.webapp.bizservice.advertising;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-07-13 09:07:55
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface AdvertisingService extends BaseService <Advertising, String> {

	PaginatedList<Advertising> selectByFilter(PaginatedFilter paginatedFilter);

	List<Advertising> findAll();
}