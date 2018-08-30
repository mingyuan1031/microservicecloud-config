package com.lwxf.newstore.webapp.domain.dao.config;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-08-15 16:19:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface StoreHomeNavDao extends BaseDao<StoreHomeNav, String> {

	PaginatedList<StoreHomeNav> selectByFilter(PaginatedFilter paginatedFilter);
	
	
	/**
	 * 根据图标排序降序排列
	 * @return
	 */
	List<StoreHomeNav> findHomeNavDatas();

}