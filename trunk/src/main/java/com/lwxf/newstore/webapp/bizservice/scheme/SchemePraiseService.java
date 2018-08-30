package com.lwxf.newstore.webapp.bizservice.scheme;


import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.entity.scheme.SchemePraise;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 14:36:15
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SchemePraiseService extends BaseService <SchemePraise, String> {

	PaginatedList<SchemePraise> selectByFilter(PaginatedFilter paginatedFilter);

	int deleteBySchemeIdAndCreator(MapContext mapContext);

}