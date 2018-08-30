package com.lwxf.newstore.webapp.facade.goods;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/15/015 14:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BrandFacade extends BaseFacade {
	RequestResult findAll();
	RequestResult deleteById(String id);
	RequestResult addBrand(Brand brand);
	RequestResult findById(String id);
	RequestResult updataBrand(String id,MapContext brand);
	RequestResult uploadBrandImage(String brandId, MultipartFile file);
	RequestResult findListByParams(MapContext mapContext);
}
