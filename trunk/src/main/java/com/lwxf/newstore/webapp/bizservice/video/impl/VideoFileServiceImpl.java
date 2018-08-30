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
import com.lwxf.newstore.webapp.domain.dao.video.VideoFileDao;
import com.lwxf.newstore.webapp.bizservice.video.VideoFileService;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;


/**
 * 功能：
 * 
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 09:36:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("videoFileService")
public class VideoFileServiceImpl extends BaseServiceImpl<VideoFile, String, VideoFileDao> implements VideoFileService {


	@Resource

	@Override	public void setDao( VideoFileDao videoFileDao) {
		this.dao = videoFileDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<VideoFile> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public boolean isExistByTypeId(String id) {
		return this.dao.isExistByTypeId(id);
	}

	@Override
	public List<VideoFile> findAll(String disabled) {
		MapContext mapContext = MapContext.newOne();
		if(null==disabled){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,null);
		}else if(disabled.equals("false")){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,false);
		}else{
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,true);
		}
		return this.dao.findAll(mapContext);
	}

	@Override
	public boolean isExistByName(String name) {
		return this.dao.isExistByName(name);
	}

}