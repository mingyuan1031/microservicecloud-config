package com.lwxf.newstore.webapp.facade.goods;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-22 11:20
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface TagFacade extends BaseFacade {
	RequestResult add(Tag tag);
	RequestResult updateByMapContext(String id,MapContext mapContext);
	RequestResult deleteById(String id);
	RequestResult findById(String id);
	RequestResult findAll();
	RequestResult findListByParams(MapContext params);
	RequestResult findListByParamsForPaging(Integer pageNum,Integer pageSize,MapContext params);
}
