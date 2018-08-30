package com.lwxf.newstore.webapp.facade.goods;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/16/016 15:02
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsTypeFacade extends BaseFacade {
	RequestResult findAll();
	RequestResult findById(String id);
	RequestResult updataGoodsType(String id, MapContext mapContext);
	RequestResult deleteById(String id);
	RequestResult addGoodsType(GoodsType goodsType);
	RequestResult findTypeAll();
}
