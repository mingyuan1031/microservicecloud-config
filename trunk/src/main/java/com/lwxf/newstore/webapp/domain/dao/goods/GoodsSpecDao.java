package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
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
@IBatisSqlTarget
public interface GoodsSpecDao extends BaseDao<GoodsSpec, String> {

	PaginatedList<GoodsSpec> selectByFilter(PaginatedFilter paginatedFilter);
	List<GoodsSpec> findAll();
	List<GoodsSpec> findByTypeId(String id);
	boolean isExistByName(String goodTypeId,String name);
	int deleteByTypeId(String typeId);
}