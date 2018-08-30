package com.lwxf.newstore.webapp.facade.video;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 9:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface VideoTypeFacade extends BaseFacade {
	RequestResult findByFilter(String disabled);
	RequestResult add(VideoType videoType);
	RequestResult deleteById(String id);
	RequestResult updata(String id,MapContext mapContext);
}
