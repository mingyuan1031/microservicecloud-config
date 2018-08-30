package com.lwxf.newstore.webapp.bizservice.config;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:12
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface StoreConfigService extends BaseService <StoreConfig, String> {

	PaginatedList<StoreConfig> selectByFilter(PaginatedFilter paginatedFilter);

	int insert(StoreConfig storeConfig);

	StoreConfig selectById(String id);

	StoreConfig findOneByLimit();

	StoreConfig findByCompanyId(String companyId);

}