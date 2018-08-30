package com.lwxf.newstore.webapp.facade.news;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 9:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface NewsTypeFacade extends BaseFacade {
	RequestResult addType(NewsType newsType);
	RequestResult deleteType(String id);
	RequestResult findAll();
	RequestResult updateDisabled(String id);
	RequestResult updateName(MapContext mapContext,String id);
}
