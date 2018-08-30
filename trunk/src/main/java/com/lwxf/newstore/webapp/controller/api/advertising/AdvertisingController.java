package com.lwxf.newstore.webapp.controller.api.advertising;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.advertising.AdvertisingFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/13 9:21
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */

@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class AdvertisingController {

	@Resource(name = "advertisingFacade")
	private AdvertisingFacade advertisingFacade;

	/**
	 * 上传广告位的图片
	 * @param multipartFile 图片文件
	 * @return
	 */
	@PostMapping(value = "/advertisings/files")
	public RequestResult uploadAdvTempImage(@RequestBody MultipartFile multipartFile,
											@RequestParam(required = false) String advertisingId){
		Map<String,Object> errorInfo = new HashMap<>();
		if(multipartFile==null){
			errorInfo.put("multipartFile",ErrorCodes.VALIDATE_NOTNULL);
		}else if (!FileMimeTypeUtil.isLegalImageType(multipartFile)) {
			errorInfo.put("multipartFile", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (multipartFile.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadBackgroundMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadBackgroundMaxsize()));
		}
		if (errorInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errorInfo);
		}
		return this.advertisingFacade.uploadAdvTempImage(multipartFile,advertisingId);
	}

	/**
	 * 添加广告
	 *
	 * @param advertising 广告的信息
	 * @param imageId 广告图片
	 * @return
	 */
	@PostMapping(value = "/advertisings")
	public RequestResult addAdvertising(@RequestBody Advertising advertising,@RequestParam String... imageId){
		advertising.setCreated(DateUtil.getSystemDate());
		advertising.setCreator(WebUtils.getCurrUserId());
		RequestResult result = advertising.validateFields();
		if(result!=null){
			return result;
		}
		if(imageId==null||imageId.length==0){
			return ResultFactory.generateResNotFoundResult();
		}
//		if(advertising==null||imageId==null){
//			return ResultFactory.generateResNotFoundResult();
//		}
		return this.advertisingFacade.addAdvertising(advertising,imageId);
	}

	/**
	 * 查询所有广告信息
	 * @return
	 */
	@GetMapping(value = "/advertisings")
	public RequestResult selectAllAdvertising(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
											  @RequestParam(required = false,defaultValue = "30") Integer pageSize){
		return this.advertisingFacade.selectAllAdvertising(pageSize,pageNum);
	}

	/**
	 * 根据广告id删除广告的信息以及upload_files的图片
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/advertisings/{id}")
	public RequestResult deleteAdvertisingById(@PathVariable String id){
		return this.advertisingFacade.deleteAdvertising(id);
	}


	/**
	 * 修改广告信息
	 * @param mapContext
	 * @return
	 */
	@PutMapping(value = "/advertisings/{id}")
	public RequestResult updateAdvertisingById(@RequestBody MapContext mapContext,@PathVariable String id){
		RequestResult validResult = Advertising.validateFields(mapContext);
		if(null != validResult){
			return validResult;
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		return this.advertisingFacade.updateAdvertisingById(mapContext,id);
	}

}
