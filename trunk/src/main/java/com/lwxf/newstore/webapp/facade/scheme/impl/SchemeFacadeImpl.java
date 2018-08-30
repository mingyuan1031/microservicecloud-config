package com.lwxf.newstore.webapp.facade.scheme.impl;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemePraiseService;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemeService;
import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.scheme.SchemeStatus;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.scheme.SchemeFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 10:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("schemeFacade")
public class SchemeFacadeImpl extends BaseFacadeImpl implements SchemeFacade {
	@Resource(name = "schemeService")
	private SchemeService schemeService;
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "schemePraiseService")
	private SchemePraiseService schemePraiseService;
	@Resource(name = "baseFileUploadComponent")
	private BaseFileUploadComponent baseFileUploadComponent;

	/**
	 *  添加案例
	 * @param scheme
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult addScheme(Scheme scheme,String...ids) {
		scheme.setCreator(WebUtils.getCurrUserId());
		scheme.setCreated(DateUtil.getSystemDate());
		//创建的设计案例浏览量和点赞数为0
		scheme.setPageView(0);
		scheme.setPraise(0);
		//为设计案例的状态赋值0为禁用，1为启用
		scheme.setStatus(SchemeStatus.ENABLED.getValue().intValue());
		RequestResult validateFields = scheme.validateFields();
		if (null!=validateFields){
			return validateFields;
		}
		this.schemeService.add(scheme);
		String resourceId = scheme.getId();
		String belongId = scheme.getId();
		this.uploadFilesService.updateMicImageStatusAndresourceIdAndbelongId(ids,resourceId,belongId);
		return ResultFactory.generateRequestResult(scheme);
	}

	/**
	 *  分页查询所有设计案例
	 * @param pageSize
	 * @param pageNum
	 * @param mapContext
	 * @return
	 */
	@Override
	public RequestResult findAll(Integer pageSize,Integer pageNum,MapContext mapContext) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		if (null!=mapContext){
			filter.setFilters(mapContext);
		}
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		return ResultFactory.generateRequestResult(this.schemeService.selectByFilter(filter));
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult findById(String id) {
		Scheme scheme = this.schemeService.findById(id);
		Integer pageView = scheme.getPageView();
		pageView = pageView+1;
		scheme.setPageView(pageView);
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		mapContext.put(WebConstant.KEY_SCHEME_PAGEVIEW,pageView);
		this.schemeService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(scheme);
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		Scheme scheme = this.schemeService.findById(id);
		if(null!=scheme){
			Map<String,UploadResourceType> files = new HashMap<>();
			files.put(scheme.getCover(),UploadResourceType.SCHEME_COVER);
			files.put(scheme.getPanorama(),UploadResourceType.SCHEME_PANORAMA);
			baseFileUploadComponent.deleteFiles(files,0);
			this.schemePraiseService.deleteById(id);
			this.uploadFilesService.deleteByBelongId(id);
			this.schemeService.deleteById(id);
		}
		return ResultFactory.generateSuccessResult();
	}

	/**
	 * 修改信息和状态共用此接口
	 * @param mapContext
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult updateById(MapContext mapContext, String id) {
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		return ResultFactory.generateRequestResult(this.schemeService.updateByMapContext(mapContext));
	}

	/**
	 * 根据类型上传设计案例的临时图片
	 * @param multipartFile
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult uploadSchemeImage(MultipartFile multipartFile,String type,String schemeId) {
		UploadInfo uploadInfo = null;
		UploadFiles uploadFiles=null;
		if(null!=schemeId){//如果传过来的schemeID不为空代表是修改图片
			if(null!=type&&type.equals("panorama")){//type是panorama是修改全景图
				uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,multipartFile,UploadResourceType.SCHEME_PANORAMA,type);
				uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(schemeId,schemeId,UploadResourceType.SCHEME_PANORAMA.getType());
			}else if (null!=type&&type.equals("cover")){//type是cover是修改封面
				uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,multipartFile,UploadResourceType.SCHEME_COVER,type);
				uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(schemeId,schemeId,UploadResourceType.SCHEME_COVER.getType());
			}
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(AppBeanInjector.configuration.getUploadFileRootDir().concat(uploadFiles.getPath()));
			MapContext update = MapContext.newOne();
			update.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
			update.put("path",uploadInfo.getRelativePath());
			update.put("name",uploadInfo.getFileName());
			update.put("originalMime",uploadInfo.getFileMimeType().getOriginalType());
			update.put("mime",uploadInfo.getFileMimeType().getRealType());
			update.put("creator",WebUtils.getCurrUserId());
			update.put("created",DateUtil.getSystemDate());
			this.uploadFilesService.updateByMapContext(update);
			return ResultFactory.generateRequestResult(this.uploadFilesService.findById(uploadFiles.getId()));
		}else{//如果传过来的schemeID为空代表是上传图片
			if(null!=type&&type.equals("cover")&&null==schemeId){//type是cover是上传封面
				uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,multipartFile,UploadResourceType.SCHEME_COVER,"cover");
				uploadFiles = UploadFiles.create(null,null,uploadInfo,UploadResourceType.SCHEME_COVER,UploadType.TEMP);
			}else if (null!=type&&type.equals("panorama")&&null==schemeId) {//type是panorama是上传封面
				uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP, multipartFile, UploadResourceType.SCHEME_PANORAMA, "panorama");
				uploadFiles = UploadFiles.create(null,null,uploadInfo,UploadResourceType.SCHEME_PANORAMA,UploadType.TEMP);
			}else {
				return ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001,"图片上传错误");
			}
			//获取上传的ID并赋值
			uploadFiles.setCreator(WebUtils.getCurrUserId());
			//获取上传的时间并赋值
			uploadFiles.setCreated(DateUtil.getSystemDate());
			//获取相对路径
			uploadFiles.setPath(uploadInfo.getRelativePath());
			this.uploadFilesService.add(uploadFiles);
			return ResultFactory.generateRequestResult(uploadFiles);
		}
	}

}
