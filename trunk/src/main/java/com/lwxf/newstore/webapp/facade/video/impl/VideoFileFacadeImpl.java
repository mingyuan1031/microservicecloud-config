package com.lwxf.newstore.webapp.facade.video.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.video.VideoFileService;
import com.lwxf.newstore.webapp.bizservice.video.VideoTypeService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.video.VideoFileFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 9:48
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("videoFileFacade")
public class VideoFileFacadeImpl extends BaseFacadeImpl implements VideoFileFacade {

	@Resource(name = "videoFileService")
	private VideoFileService videoFileService;
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "videoTypeService")
	private VideoTypeService videoTypeService;
	@Override
	public RequestResult findAll(String disabled) {
		return ResultFactory.generateRequestResult(videoFileService.findAll(disabled));
	}

	@Override
	@Transactional
	public RequestResult uploadVideoImage(String id,MultipartFile file) {
		UploadFiles uploadFiles=null;
		//赋值给uploadinfo,数据进行处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,file,UploadResourceType.COVER,"cover");
		if(id!=null){
			uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(id,id,UploadResourceType.COVER.getType());
			if(uploadFiles == null){
				uploadFiles = UploadFiles.create(id,id ,uploadInfo,UploadResourceType.COVER,UploadType.FORMAL);
				this.uploadFilesService.add(uploadFiles);
			}else{
				AppBeanInjector.baseFileUploadComponent.deleteFileByDir(uploadFiles.getPath());
				MapContext update = MapContext.newOne();
				update.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
				update.put("path",uploadInfo.getRelativePath());
				update.put("name",uploadInfo.getFileName());
				update.put("originalMime",uploadInfo.getFileMimeType().getOriginalType());
				update.put("mime",uploadInfo.getFileMimeType().getRealType());
				update.put("creator",WebUtils.getCurrUserId());
				update.put("created",DateUtil.getSystemDate());
				this.uploadFilesService.updateByMapContext(update);
			}
			MapContext mapContext = new MapContext();
			mapContext.put("cover",uploadInfo.getRelativePath());
			mapContext.put(WebConstant.KEY_ENTITY_ID,id);
			this.videoFileService.updateByMapContext(mapContext);
		}else{
			 uploadFiles = UploadFiles.create(null,null ,uploadInfo,UploadResourceType.COVER,UploadType.FORMAL);
			this.uploadFilesService.add(uploadFiles);
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
		mapContext.put("path",uploadFiles.getPath());
		return ResultFactory.generateRequestResult(mapContext);
	}

	@Override
	@Transactional
	public RequestResult updata(String id, MapContext mapContext) {
		if(null!=mapContext.get("videoTypeId")){
			VideoType videoType=this.videoTypeService.findById((String) mapContext.get("videoTypeId"));
			if(videoType==null){
				return ResultFactory.generateResNotFoundResult();
			}else if(videoType.getDisabled()){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
			}
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.videoFileService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult uploadVideoFile(String id, MultipartFile file) {
		UploadFiles uploadFiles=null;
		//赋值给uploadinfo,数据进行处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,file,UploadResourceType.VIDEOFILE,"videofile");
		if(id!=null){
			uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(id,id,UploadResourceType.VIDEOFILE.getType());
			if(uploadFiles == null){
				uploadFiles = UploadFiles.create(id,id ,uploadInfo,UploadResourceType.VIDEOFILE,UploadType.FORMAL);
				this.uploadFilesService.add(uploadFiles);
			}else{
				AppBeanInjector.baseFileUploadComponent.deleteFileByDir(uploadFiles.getPath());
				MapContext update = MapContext.newOne();
				update.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
				update.put("path",uploadInfo.getRelativePath());
				update.put("name",uploadInfo.getFileName());
				update.put("originalMime",uploadInfo.getFileMimeType().getOriginalType());
				update.put("mime",uploadInfo.getFileMimeType().getRealType());
				update.put("creator",WebUtils.getCurrUserId());
				update.put("created",DateUtil.getSystemDate());
				this.uploadFilesService.updateByMapContext(update);
			}
			MapContext mapContext = new MapContext();
			mapContext.put("path",uploadInfo.getRelativePath());
			mapContext.put(WebConstant.KEY_ENTITY_ID,id);
			this.videoFileService.updateByMapContext(mapContext);
		}else{
			uploadFiles = UploadFiles.create(null,null ,uploadInfo,UploadResourceType.COVER,UploadType.FORMAL);
			this.uploadFilesService.add(uploadFiles);
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
		mapContext.put("path",uploadFiles.getPath());
		return ResultFactory.generateRequestResult(mapContext);
	}

	@Override
	@Transactional
	public RequestResult addVideoFile(VideoFile videoFile,String coverId,String pathId) {
		VideoType videoType=this.videoTypeService.findById(videoFile.getVideoTypeId());
		if(videoType==null){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("videoTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else if(videoType.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
		}
		if(this.videoFileService.isExistByName(videoFile.getName())){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		videoFileService.add(videoFile);
		MapContext mapContext = MapContext.newOne();
		if(coverId!=null){
			mapContext.put(WebConstant.KEY_ENTITY_ID,coverId);
			mapContext.put("status",true);
			mapContext.put("resourceId",videoFile.getId());
			mapContext.put("belongId",videoFile.getId());
			this.uploadFilesService.updateByMapContext(mapContext);
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,pathId);
		mapContext.put("status",true);
		mapContext.put("resourceId",videoFile.getId());
		mapContext.put("belongId",videoFile.getId());
		this.uploadFilesService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		VideoFile videoFile= this.videoFileService.findById(id);
		if(videoFile!=null){
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(videoFile.getPath());
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(videoFile.getCover());
			this.uploadFilesService.deleteByResourceId(id);
		}
		this.videoFileService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}
}
