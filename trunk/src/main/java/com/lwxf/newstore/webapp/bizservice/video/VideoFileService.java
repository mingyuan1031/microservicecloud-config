package com.lwxf.newstore.webapp.bizservice.video;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;


/**
 * 功能：
 * 
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 09:36:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface VideoFileService extends BaseService <VideoFile, String> {

	PaginatedList<VideoFile> selectByFilter(PaginatedFilter paginatedFilter);
	boolean isExistByTypeId(String id);
	List<VideoFile> findAll(String disabled);
	boolean isExistByName(String name);
}