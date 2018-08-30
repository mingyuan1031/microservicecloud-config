package com.lwxf.newstore.webapp.bizservice.common.impl;


import javax.annotation.Resource;

import java.util.List;
import java.util.Set;

import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;

import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.common.UploadFilesDao;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 11:44:26
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("uploadFilesService")
public class UploadFilesServiceImpl extends BaseServiceImpl<UploadFiles, String, UploadFilesDao> implements UploadFilesService {


	@Resource
	@Override	public void setDao( UploadFilesDao uploadFilesDao) {
		this.dao = uploadFilesDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UploadFiles> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public UploadFiles findByBelongIdAndResourceIdAndResourceType(String belongId, String ResourceId, int ResourceType) {
		return this.dao.findByBelongIdAndResourceIdAndResourceType(belongId,ResourceId,ResourceType);
	}

	@Override
	public int deleteByResourceId(String id) {
		return this.dao.deleteByResourceId(id);
	}

	@Override
	public List<UploadFiles> findByBelongId(String belongId) {
		return this.dao.findByBelongId(belongId);
	}
	@Override
	public int updateMicImageStatusAndresourceIdAndbelongId(String[] ids, String resourceId,String belongId) {
		return this.dao.updateMicImageStatusAndresourceIdAndbelongId(ids,resourceId,belongId);
	}

	@Override
	public int updateMicImageStatus(String[] ids) {
		return this.dao.updateMicImageStatus(ids);
	}

	@Override
	public List<UploadFiles> isNullByIds(String[] ids) {
		return this.dao.isNullByIds(ids);
	}

	@Override
	public int deleteMicImage(String[] ids) {
		return this.dao.deleteMicImage(ids);
	}

	@Override
	public List<String> selectMicImageIdByBlogId(String blogId) {
		return this.dao.selectMicImageIdByBlogId(blogId);
	}

	@Override
	public List<UploadFiles> selectByCreatorAndTempAndResourceType(String creator) {
		return this.dao.selectByCreatorAndTempAndResourceType(creator);
	}

	@Override
	public int deleteByUserIdAndStatusAndResType(String userId) {
		return this.dao.deleteByUserIdAndStatusAndResType(userId);
	}

	@Override
	public List<UploadFiles> findByResourceId(String resoureceId) {
		return this.dao.findByResourceId(resoureceId);
	}

	@Override
	public List<UploadFiles> findByResourceIdAndStatusAndTypeId(String ResourcedId,boolean status,int typeId) {
		return this.dao.findByResourceIdAndStatusAndTypeId(ResourcedId,status,typeId);
	}

	@Override
	public List<UploadFiles> findByBlogIds(Set<String> blogIds) {
		return this.dao.findByBlogIds(blogIds);
	}

	@Override
	public int deleteByBelongId(String id) {
		return this.dao.deleteByBelongId(id);
	}
}