package com.lwxf.newstore.webapp.bizservice.scheme;


import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 09:54:52
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SchemeService extends BaseService <Scheme, String> {

	PaginatedList<Scheme> selectByFilter(PaginatedFilter paginatedFilter);

}