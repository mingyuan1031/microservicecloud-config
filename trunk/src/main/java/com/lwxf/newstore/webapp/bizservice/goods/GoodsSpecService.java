package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.dto.goods.SpecOptionList;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsSpecService extends BaseService <GoodsSpec, String> {

	PaginatedList<GoodsSpec> selectByFilter(PaginatedFilter paginatedFilter);

	List<GoodsSpec> findAll();

	List<GoodsSpec> findByTypeId(String id);

	boolean isExistByName(String goodsTypeId, String name);

	int deleteByTypeId(String typeId);
}