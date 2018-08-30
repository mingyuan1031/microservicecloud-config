package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsShow;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-22 15:30:32
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsShowService extends BaseService <GoodsShow, String> {

	PaginatedList<GoodsShow> selectByFilter(PaginatedFilter paginatedFilter);

	List<GoodsShow> findByGoodsId(String id);

	int removeDefaults(String goodsId);

	int setDefault(String id);

}