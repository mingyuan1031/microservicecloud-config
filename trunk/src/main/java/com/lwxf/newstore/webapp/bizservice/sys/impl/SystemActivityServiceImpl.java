package com.lwxf.newstore.webapp.bizservice.sys.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.sys.SystemActivityService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.sys.SystemActivityDao;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-30 08:55:29
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("systemActivityService")
public class SystemActivityServiceImpl extends BaseServiceImpl<SystemActivity, String, SystemActivityDao> implements SystemActivityService {


	@Resource

	@Override	public void setDao( SystemActivityDao systemActivityDao) {
		this.dao = systemActivityDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<SystemActivity> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int deleteByIds(String[] ids) {
		return this.dao.deleteByIds(ids);
	}
}