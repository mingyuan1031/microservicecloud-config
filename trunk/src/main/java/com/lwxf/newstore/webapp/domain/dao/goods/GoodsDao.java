package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsDefault;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface GoodsDao extends BaseDao<Goods, String> {

	PaginatedList<Goods> selectByFilter(PaginatedFilter paginatedFilter);
	PaginatedList<GoodsDefault> findGoodsDefaultByParamsForPaging(PaginatedFilter paginatedFilter);
	boolean isExistByBrandId(String brandId);
	boolean isExistByGoodsTypeId(String id);
	List<GoodsDefault> findForHomePage();
	GoodsDefault findGoodsInfo(String id);
	int updateUpdator(MapContext mapContext);
}