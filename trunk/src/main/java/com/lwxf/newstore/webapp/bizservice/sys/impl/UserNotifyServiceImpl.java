package com.lwxf.newstore.webapp.bizservice.sys.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.sys.UserNotifyService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.sys.UserNotifyDao;
import com.lwxf.newstore.webapp.domain.entity.sys.UserNotify;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-30 08:55:29
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("userNotifyService")
public class UserNotifyServiceImpl extends BaseServiceImpl<UserNotify, String, UserNotifyDao> implements UserNotifyService {


	@Resource

	@Override	public void setDao( UserNotifyDao userNotifyDao) {
		this.dao = userNotifyDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UserNotify> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

}