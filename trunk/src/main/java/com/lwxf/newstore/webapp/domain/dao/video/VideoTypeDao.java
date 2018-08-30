package com.lwxf.newstore.webapp.domain.dao.video;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;


/**
 * 功能：
 * 
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 09:36:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface VideoTypeDao extends BaseDao<VideoType, String> {

	PaginatedList<VideoType> selectByFilter(PaginatedFilter paginatedFilter);
	List<VideoType> findByFilter(MapContext mapContext);
	boolean isExistByName(String name);
}