package com.lwxf.newstore.webapp.facade.scheme;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 10:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SchemeFacade extends BaseFacade  {

	RequestResult addScheme(Scheme scheme,String... ids);

	RequestResult findAll(Integer pageSize,Integer pageNum,MapContext mapContext);

	RequestResult findById (String id);

	RequestResult deleteById (String id);

	RequestResult updateById(MapContext mapContext,String id);

	RequestResult uploadSchemeImage(MultipartFile multipartFile,String type,String schemeId);


}
