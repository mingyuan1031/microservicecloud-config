package com.lwxf.newstore.webapp.facade.goods;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/4/004 14:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsShowFacade extends BaseFacade {
	RequestResult uploadGoodsShowFile(String id, MultipartFile file);
	RequestResult findAll(String goodsId);
	RequestResult setDefault(String id,String goodsId);
	RequestResult deleteById(String id,String goodsId);
}
