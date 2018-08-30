package com.lwxf.newstore.webapp.domain.dao.common.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
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
@Repository("uploadFilesDao")
public class UploadFilesDaoImpl extends BaseDaoImpl<UploadFiles, String> implements UploadFilesDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UploadFiles> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<UploadFiles> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public UploadFiles findByBelongIdAndResourceIdAndResourceType(String belongId, String ResourceId, int ResourceType) {
		String sqlId = this.getNamedSqlId("findByBelongIdAndResourceIdAndResourceType");
		Map map = new HashMap<String,Object>();
		map.put("belongId",belongId);
		map.put("ResourceId",ResourceId);
		map.put("ResourceType",ResourceType);
		return this.getSqlSession().selectOne(sqlId,map);
	}

	@Override
	public int deleteByResourceId(String id) {
		String sqlId = this.getNamedSqlId("deleteByResourceId");
		return this.getSqlSession().delete(sqlId,id);
	}

	@Override
	public List<UploadFiles> findByBelongId(String belongId) {
		String sqlId = this.getNamedSqlId("findByBelongId");
		return this.getSqlSession().selectList(sqlId, belongId);
	}
	@Override
	public int updateMicImageStatusAndresourceIdAndbelongId(String[] ids,  String resourceId,String belongId) {
		String sqlId = this.getNamedSqlId("updateMicImageStatusAndresourceIdAndbelongId");
		Map<String,Object> data = new HashMap<>();
		data.put("ids",ids);
		data.put("resourceId",resourceId);
		data.put("belongId",belongId);
		return this.getSqlSession().update(sqlId,data);
	}

	@Override
	public int updateMicImageStatus(String[] ids) {
		String sqlId = this.getNamedSqlId("updateMicImageStatus");
		return this.getSqlSession().update(sqlId,ids);
	}

	@Override
	public List<UploadFiles> isNullByIds(String[] ids) {
		String sqlId = this.getNamedSqlId("isNullByIds");
		return this.getSqlSession().selectList(sqlId,ids);
	}

	@Override
	public int deleteMicImage(String[] ids) {
		String sqlId = this.getNamedSqlId("deleteMicImage");
		return this.getSqlSession().delete(sqlId,ids);
	}

	@Override
	public List<String> selectMicImageIdByBlogId(String blogId) {
		String sqlId = this.getNamedSqlId("selectMicImageIdByBlogId");
		return this.getSqlSession().selectList(sqlId,blogId);
	}

	@Override
	public List<UploadFiles> selectByCreatorAndTempAndResourceType(String creator) {
		String sqlId = this.getNamedSqlId("selectByCreatorAndTempAndResourceType");
		return this.getSqlSession().selectList(sqlId,creator);
	}

	@Override
	public int deleteByUserIdAndStatusAndResType(String userId) {
		String sqlId = this.getNamedSqlId("deleteByUserIdAndStatusAndResType");
		return this.getSqlSession().delete(sqlId,userId);
	}

	@Override
	public List<UploadFiles> findByResourceId(String resourceId) {
		String sqlId = this.getNamedSqlId("findByResourceId");
		return this.getSqlSession().selectList(sqlId,resourceId);
	}
	@Override
	public List<UploadFiles> findByBlogIds(Set<String> blogIds) {
		String sqlId = this.getNamedSqlId("findByBlogIds");
		return this.getSqlSession().selectList(sqlId, blogIds);
	}


	@Override
	public int deleteByBelongId(String id) {
		String sqlId = this.getNamedSqlId("deleteByBelongId");
		return this.getSqlSession().delete(sqlId,id);
	}

	@Override
	public List<UploadFiles> findByResourceIdAndStatusAndTypeId(String ResourcedId, boolean status,int typeId) {
		String sqlId = this.getNamedSqlId("findByResourceIdAndStatusAndTypeId");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("ResourceId",ResourcedId);
		mapContext.put("status",status);
		mapContext.put("typeId",typeId);
		return this.getSqlSession().selectList(sqlId,mapContext);
	}
}