package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface GoodsTypeDao extends BaseDao<GoodsType, String> {

	PaginatedList<GoodsType> selectByFilter(PaginatedFilter paginatedFilter);
	List<GoodsType> findAll();
	List<GoodsType> selectByParentId(String id);
	List<GoodsType> findByParentId(String id);
	GoodsType findByNameAndParentId(String name,String parentId);
	GoodsType findByName(String name);
}