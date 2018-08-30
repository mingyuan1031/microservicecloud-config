package com.lwxf.newstore.webapp.facade.goods;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:41
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsSpecFacade extends BaseFacade {
	RequestResult findByTypeId(String id);
	RequestResult findAll();
	RequestResult addGoodsSpec(GoodsSpec goodsSpec);
	RequestResult deleteById(String id,String goodsTypeId);
	RequestResult updata(String id, MapContext mapContext,String goodsTypeId);
}
