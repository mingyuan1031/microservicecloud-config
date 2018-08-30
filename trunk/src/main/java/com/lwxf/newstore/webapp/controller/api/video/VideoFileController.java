package com.lwxf.newstore.webapp.controller.api.video;

import sun.tools.asm.Cover;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.video.VideoFileFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 9:52
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/videofiles", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class VideoFileController {
	@Resource(name = "videoFileFacade")
	private VideoFileFacade videoFileFacade;

	@GetMapping
	private RequestResult findAll(@RequestParam(required = false) String disabled){
		return videoFileFacade.findAll(disabled);
	}

	@PostMapping("files")
	private RequestResult uploadFile(@RequestParam(required = false) String id,@RequestParam MultipartFile file){
		Map<String, Object> errInfo = new HashMap<>();
		if (file == null) {
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalImageType(file)) {
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadAvatarMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031").concat(":").concat(AppBeanInjector.configuration.getUploadAvatarMaxsize()+"").concat("M"));
		}
		if (errInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.videoFileFacade.uploadVideoImage(id,file);
	}

	@PostMapping("video")
	private RequestResult uploadVideoFile(@RequestParam(required = false) String id,@RequestParam MultipartFile file){
		Map<String, Object> errInfo = new HashMap<>();
		if (file == null) {
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLeagelVideoFileType(file)) {
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadVideoMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031").concat(":").concat(AppBeanInjector.configuration.getUploadVideoMaxsize()+"").concat("M"));
		}
		if (errInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.videoFileFacade.uploadVideoFile(id,file);
	}
	@PostMapping
	private RequestResult addVideoFile(@RequestBody VideoFile videoFile, @RequestParam(required = false) String coverId,@RequestParam String pathId){
		videoFile.setCreated(DateUtil.getSystemDate());
		videoFile.setCreator(WebUtils.getCurrUserId());
		videoFile.setDisabled(false);
		RequestResult result =  videoFile.validateFields();
		if(result!=null){
			return result;
		}
		return this.videoFileFacade.addVideoFile(videoFile,coverId,pathId);
	}

	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id, @RequestBody MapContext mapContext){
		RequestResult result= VideoFile.validateFields(mapContext);
		if(result!=null){
			return result;
		}
		return this.videoFileFacade.updata(id,mapContext);
	}
	@DeleteMapping("{id}")
	private RequestResult deleteById(@PathVariable String id){
		return this.videoFileFacade.deleteById(id);
	}
}
