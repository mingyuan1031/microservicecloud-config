package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
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
public interface GoodsService extends BaseService <Goods, String> {

	PaginatedList<Goods> selectByFilter(PaginatedFilter paginatedFilter);
	PaginatedList<GoodsDefault> findGoodsDefaultByParamsForPaging(PaginatedFilter paginatedFilter);
	Boolean isExistByBrandId(String brandId);
	Boolean isExistByGoodsTypeId(String goodsTypeId);
	List<GoodsDefault> findForHomePage();
	GoodsDefault findGoodsInfo(String id);
	int updateUpdator(String goodsId);
	//订单付款成功时 该种规格的库存减1 销量加1 库存为0的话 返回 0 代表没有库存 不可购买 1 代表成功
	int updataStock(String extendsId);
}