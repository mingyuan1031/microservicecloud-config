package com.lwxf.newstore.webapp.bizservice.config.impl;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.config.StoreHomeNavDao;
import com.lwxf.newstore.webapp.bizservice.config.StoreHomeNavService;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:21
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("storeHomeNavService")
public class StoreHomeNavServiceImpl extends BaseServiceImpl<StoreHomeNav, String, StoreHomeNavDao> implements StoreHomeNavService {


	@Resource(name = "storeHomeNavDao")
	@Override	public void setDao( StoreHomeNavDao storeHomeNavDao) {
		this.dao = storeHomeNavDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<StoreHomeNav> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int insert(StoreHomeNav storeHomeNav) {
		return this.dao.insert(storeHomeNav);
	}

	@Override
	public StoreHomeNav selectById(String id) {
		return this.dao.selectById(id);
	}

	@Override
	public List<StoreHomeNav> findHomeNavDatas() {
		return this.dao.findHomeNavDatas();
	}

}