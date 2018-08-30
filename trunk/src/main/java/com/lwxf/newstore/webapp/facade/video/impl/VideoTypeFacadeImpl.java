package com.lwxf.newstore.webapp.facade.video.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.video.VideoFileService;
import com.lwxf.newstore.webapp.bizservice.video.VideoTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.video.VideoTypeFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 9:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("videoTypeFacade")
public class VideoTypeFacadeImpl extends BaseFacadeImpl implements VideoTypeFacade {
	@Resource(name = "videoTypeService")
	private VideoTypeService videoTypeService;
	@Resource(name = "videoFileService")
	private VideoFileService videoFileService;
	@Override
	public RequestResult findByFilter(String disabled) {
		return ResultFactory.generateRequestResult(this.videoTypeService.findByFilter(disabled));
	}

	@Override
	@Transactional
	public RequestResult add(VideoType videoType) {
		if(videoTypeService.isExistByName(videoType.getName())){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		this.videoTypeService.add(videoType);
		return ResultFactory.generateRequestResult(videoType);
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		if(this.videoFileService.isExistByTypeId(id)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		this.videoTypeService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updata(String id,MapContext mapContext) {
		if(null!=mapContext.get("name")){
			if (this.videoTypeService.isExistByName((String) mapContext.get("name"))){
				Map<String,String> validResult = new HashMap<>();
				validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.videoTypeService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}
}
