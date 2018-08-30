package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsTypeService extends BaseService <GoodsType, String> {

	PaginatedList<GoodsType> selectByFilter(PaginatedFilter paginatedFilter);
	List<GoodsType> findall();
	List<GoodsType> findByParentId(String id);
	GoodsType findByNameAndParentId(String name,String goodsTypeId);
	GoodsType findByName(String name);
}