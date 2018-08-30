package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.Map;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsComments;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface GoodsCommentDao extends BaseDao<GoodsComment, String> {

	PaginatedList<GoodsComment> selectByFilter(PaginatedFilter paginatedFilter);
	PaginatedList<GoodsComments> findByMapper(PaginatedFilter paginatedFilter);
	int deleteByParentId(String parentId);
	Map<String,Object> findCountById(String id);
}