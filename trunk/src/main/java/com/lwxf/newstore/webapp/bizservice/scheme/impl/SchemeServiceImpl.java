package com.lwxf.newstore.webapp.bizservice.scheme.impl;


import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemeService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.scheme.SchemeDao;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 09:54:52
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("schemeService")
public class SchemeServiceImpl extends BaseServiceImpl<Scheme, String, SchemeDao> implements SchemeService {


	@Resource

	@Override	public void setDao( SchemeDao schemeDao) {
		this.dao = schemeDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Scheme> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

}