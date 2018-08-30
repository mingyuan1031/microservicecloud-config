package com.lwxf.newstore.webapp.facade.advertising.impl;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.advertising.AdvertisingService;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.advertising.AdvertisingLinkStart;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.advertising.AdvertisingFacade;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/13 9:18
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component(value = "advertisingFacade")
public class AdvertisingFacadeImpl extends BaseFacadeImpl implements AdvertisingFacade {
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "advertisingService")
	private AdvertisingService advertisingService;
	@Resource
	private BaseFileUploadComponent baseFileUploadComponent;


	/**
	 * 上传广告位的临时图片
	 * @param multipartFile
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult uploadAdvTempImage(MultipartFile multipartFile,String advertisingId) {
		UploadInfo uploadInfo;
		UploadFiles  uploadFiles;
		if(null!=advertisingId){
			if(!this.advertisingService.isExist(advertisingId)){
				return ResultFactory.generateResNotFoundResult();
			}
			uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,multipartFile,UploadResourceType.ADVERTISING,"advertising");
			uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(advertisingId,advertisingId,UploadResourceType.ADVERTISING.getType());
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
			MapContext updataAdv = MapContext.newOne();
			updataAdv.put(WebConstant.KEY_ENTITY_ID,advertisingId);
			updataAdv.put("path",uploadInfo.getRelativePath());
			this.advertisingService.updateByMapContext(updataAdv);
		}else {
			uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,multipartFile,UploadResourceType.ADVERTISING,"advertising");
			//创建
			uploadFiles = UploadFiles.create(null,null,uploadInfo,UploadResourceType.ADVERTISING,UploadType.TEMP);
			//获取上传的ID并赋值
			uploadFiles.setCreator(WebUtils.getCurrUserId());
			//获取上传的时间并赋值
			uploadFiles.setCreated(DateUtil.getSystemDate());
			//获取相对路径
			uploadFiles.setPath(uploadInfo.getRelativePath());
			this.uploadFilesService.add(uploadFiles);
		}
		return ResultFactory.generateRequestResult(uploadFilesService.findById(uploadFiles.getId()));
	}

	/**
	 *  添加广告并修改图片状态。
	 * @param advertising
	 * @param imageId
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult addAdvertising(Advertising advertising, String[] imageId) {
		advertising.setLinkStart(AdvertisingLinkStart.ENABLED.getValue().intValue());
		this.advertisingService.add(advertising);
		String resourceId = advertising.getId();
		String belongId = advertising.getId();
		this.uploadFilesService.updateMicImageStatusAndresourceIdAndbelongId(imageId,resourceId,belongId);
		return ResultFactory.generateRequestResult(this.advertisingService.findById(advertising.getId())) ;
	}

	/**
	 * 查询所有的广告信息
	 * @return
	 */
	@Override
	public RequestResult selectAllAdvertising(Integer pageSize,Integer pageNum) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		Pagination pagination =Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		return ResultFactory.generateRequestResult(this.advertisingService.selectByFilter(filter)) ;
	}

	/**
	 * 根据广告id删除广告的信息以及upload_files的图片
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult deleteAdvertising(String id) {
		Advertising advertising = this.advertisingService.findById(id);
		if (null!=advertising){
			baseFileUploadComponent.deleteFile(advertising.getPath(), UploadResourceType.ADVERTISING,0);
			this.uploadFilesService.deleteByBelongId(id);
			this.advertisingService.deleteById(id);
		}
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updateAdvertisingById(MapContext mapContext,String id) {
		Advertising advertising = this.advertisingService.findById(id);
		if (advertising == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		this.advertisingService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(this.advertisingService.findById(id));
	}
}
