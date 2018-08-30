package com.lwxf.newstore.webapp.bizservice.scheme.impl;


import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemePraiseService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.scheme.SchemePraiseDao;
import com.lwxf.newstore.webapp.domain.entity.scheme.SchemePraise;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 14:36:15
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("schemePraiseService")
public class SchemePraiseServiceImpl extends BaseServiceImpl<SchemePraise, String, SchemePraiseDao> implements SchemePraiseService {


	@Resource

	@Override	public void setDao( SchemePraiseDao schemePraiseDao) {
		this.dao = schemePraiseDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<SchemePraise> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int deleteBySchemeIdAndCreator(MapContext mapContext) {

		return  this.dao.deleteBySchemeIdAndCreator(mapContext);
	}
}