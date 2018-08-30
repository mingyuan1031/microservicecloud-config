package com.lwxf.newstore.webapp.bizservice.config.impl;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.config.SystemConfigDao;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.domain.entity.config.SystemConfig;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:21
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, String, SystemConfigDao> implements SystemConfigService {


	@Resource(name = "systemConfigDao")
	@Override
	public void setDao( SystemConfigDao systemConfigDao) {
		this.dao = systemConfigDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<SystemConfig> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int insert(SystemConfig systemConfig) {
		return this.dao.insert(systemConfig);
	}

	@Override
	public SystemConfig selectById(String id) {
		return this.dao.selectById(id);
	}

	@Override
	public SystemConfig findOneByLimit() {
		return this.dao.findOneByLimit();
	}

}