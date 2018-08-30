package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface TagDao extends BaseDao<Tag, String> {

	PaginatedList<Tag> selectByFilter(PaginatedFilter paginatedFilter);

	List<Tag> findListByName(String name);

	List<Tag> findAll();

	List<Tag> findListByParams(MapContext params);

	PaginatedList<Tag> findListByParamsForPaging(PaginatedFilter paginatedFilter);
}