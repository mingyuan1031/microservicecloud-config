package com.lwxf.newstore.webapp.bizservice.sys;


import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-30 08:55:29
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SystemActivityService extends BaseService <SystemActivity, String> {

	PaginatedList<SystemActivity> selectByFilter(PaginatedFilter paginatedFilter);

	int deleteByIds (String[] ids);

}