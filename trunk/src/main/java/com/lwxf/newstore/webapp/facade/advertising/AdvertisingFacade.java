package com.lwxf.newstore.webapp.facade.advertising;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/13 9:18
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface AdvertisingFacade extends BaseFacade {

	RequestResult uploadAdvTempImage(MultipartFile multipartFile,String advertisingId);

	RequestResult addAdvertising(Advertising advertising,String[] imageId);

	RequestResult selectAllAdvertising(Integer pageSize,Integer pageNum);


	RequestResult deleteAdvertising(String id);

	RequestResult updateAdvertisingById(MapContext mapContext, String id);
}
