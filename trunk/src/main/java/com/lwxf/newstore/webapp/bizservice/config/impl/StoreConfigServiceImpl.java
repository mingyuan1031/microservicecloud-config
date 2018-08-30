package com.lwxf.newstore.webapp.bizservice.config.impl;


import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.config.StoreConfigDao;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:12
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("storeConfigService")
public class StoreConfigServiceImpl extends BaseServiceImpl<StoreConfig, String, StoreConfigDao> implements StoreConfigService {


	@Resource(name = "storeConfigDao")
	@Override
	public void setDao( StoreConfigDao storeConfigDao) {
		this.dao = storeConfigDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<StoreConfig> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int insert(StoreConfig storeConfig) {
		return this.dao.insert(storeConfig);
	}

	@Override
	public StoreConfig selectById(String id) {
		return this.dao.selectById(id);
	}

	@Override
	public StoreConfig findOneByLimit() {
		return this.dao.findOneByLimit();
	}

	@Override
	public StoreConfig findByCompanyId(String companyId) {
		return this.dao.findByCompanyId(companyId);
	}
}