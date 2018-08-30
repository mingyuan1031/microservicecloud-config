package com.lwxf.newstore.webapp.domain.dao.scheme;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 09:54:52
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface SchemeDao extends BaseDao<Scheme, String> {

	PaginatedList<Scheme> selectByFilter(PaginatedFilter paginatedFilter);

}