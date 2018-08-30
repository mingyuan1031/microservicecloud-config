package com.lwxf.newstore.webapp.domain.dao.config;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-15 11:16:12
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface StoreConfigDao extends BaseDao<StoreConfig, String> {

	PaginatedList<StoreConfig> selectByFilter(PaginatedFilter paginatedFilter);

	StoreConfig findOneByLimit();

	StoreConfig findByCompanyId(String companyId);

}