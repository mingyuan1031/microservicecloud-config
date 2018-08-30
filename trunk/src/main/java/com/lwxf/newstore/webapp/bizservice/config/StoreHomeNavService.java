package com.lwxf.newstore.webapp.bizservice.config;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:21
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface StoreHomeNavService extends BaseService <StoreHomeNav, String> {

	PaginatedList<StoreHomeNav> selectByFilter(PaginatedFilter paginatedFilter);

	int insert(StoreHomeNav storeHomeNav);

	StoreHomeNav selectById(String id);

	List<StoreHomeNav> findHomeNavDatas();


}