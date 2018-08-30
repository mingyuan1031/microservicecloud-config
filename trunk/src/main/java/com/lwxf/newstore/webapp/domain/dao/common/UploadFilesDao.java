package com.lwxf.newstore.webapp.domain.dao.common;


import java.util.List;
import java.util.Set;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 11:44:26
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface UploadFilesDao extends BaseDao<UploadFiles, String> {

	PaginatedList<UploadFiles> selectByFilter(PaginatedFilter paginatedFilter);
	UploadFiles findByBelongIdAndResourceIdAndResourceType(String belongId, String ResourceId, int ResourceType);
	int deleteByResourceId(String id);

	List<UploadFiles> findByBelongId(String belongId);
	int  updateMicImageStatusAndresourceIdAndbelongId (String[] ids, String resourceId,String belongId);

	int updateMicImageStatus (String[] ids);

	List<UploadFiles> isNullByIds (String[] ids);

	int deleteMicImage (String[] ids);

	List<String> selectMicImageIdByBlogId (String blogId);

	List<UploadFiles> selectByCreatorAndTempAndResourceType(String creator);

	int deleteByUserIdAndStatusAndResType(String userId);

	List<UploadFiles> findByResourceId(String resourceId);

	List<UploadFiles> findByBlogIds(Set<String> blogIds);
	int deleteByBelongId(String id);
	List<UploadFiles> findByResourceIdAndStatusAndTypeId(String ResourcedId,boolean status,int typeId);
}