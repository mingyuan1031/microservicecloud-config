package com.lwxf.newstore.webapp.facade.goods;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/28/028 9:32
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SpecOptionFacade extends BaseFacade {
	RequestResult findAll();
	RequestResult deleteById(String id,String goodsTypeId,String specId);
	RequestResult addSpecOption(SpecOption specOption,String goodsTypeId);
	RequestResult findBySpecId(String id);
	RequestResult updata(String id, MapContext mapContext,String specId,String goodsTypeId);
	RequestResult findByOptions(String[] options);
}
