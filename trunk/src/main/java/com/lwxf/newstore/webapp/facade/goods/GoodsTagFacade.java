package com.lwxf.newstore.webapp.facade.goods;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/13/013 16:12
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsTagFacade extends BaseFacade {
	RequestResult addGoodsTag(String goodsId,String[] tags);
	RequestResult deleteGoodsTag(String goodsId,String tagId);
	RequestResult findByGoodsId(String goodsId);
}
