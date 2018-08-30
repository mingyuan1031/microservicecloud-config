package com.lwxf.newstore.webapp.facade.goods;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:21
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsExtendFacade extends BaseFacade {
	RequestResult findByGoodsId(String GoodsId);

	RequestResult add(String goodsId,List<GoodsExtend> goodsExtendList);

	RequestResult updata(String id, MapContext mapContext,String goodsId);

	RequestResult uploadExtendImage(String id, MultipartFile file,String goodsId);
}
