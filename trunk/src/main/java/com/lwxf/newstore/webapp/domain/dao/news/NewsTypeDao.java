package com.lwxf.newstore.webapp.domain.dao.news;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:35:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface NewsTypeDao extends BaseDao<NewsType, String> {

	PaginatedList<NewsType> selectByFilter(PaginatedFilter paginatedFilter);
	Boolean isExistName(String name);
	List<NewsType> findByParentId(String id);
	Boolean findByParentIds(List types);
	List<NewsType> findAll();
}