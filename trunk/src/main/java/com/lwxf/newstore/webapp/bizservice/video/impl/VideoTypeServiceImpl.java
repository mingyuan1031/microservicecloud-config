package com.lwxf.newstore.webapp.bizservice.video.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.video.VideoTypeDao;
import com.lwxf.newstore.webapp.bizservice.video.VideoTypeService;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;


/**
 * 功能：
 * 
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 09:36:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("videoTypeService")
public class VideoTypeServiceImpl extends BaseServiceImpl<VideoType, String, VideoTypeDao> implements VideoTypeService {


	@Resource

	@Override	public void setDao( VideoTypeDao videoTypeDao) {
		this.dao = videoTypeDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<VideoType> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<VideoType> findByFilter(String disabled) {
		MapContext mapContext = MapContext.newOne();
		if(null==disabled){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,null);
		}else if(disabled.equals("false")){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,false);
		}else{
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,true);
		}
		return this.dao.findByFilter(mapContext);
	}

	@Override
	public boolean isExistByName(String name) {
		return this.dao.isExistByName(name);
	}

}