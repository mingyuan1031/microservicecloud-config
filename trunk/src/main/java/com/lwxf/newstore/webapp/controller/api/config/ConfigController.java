package com.lwxf.newstore.webapp.controller.api.config;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;
import com.lwxf.newstore.webapp.domain.entity.config.SystemConfig;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.config.ConfigFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-15 16:04
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class ConfigController {
	@Resource(name = "configFacade")
	private ConfigFacade configFacade;
	@PutMapping(value = "/syscfgs/{cfgId}")
	public RequestResult putSystemConfig(@PathVariable String cfgId, @RequestBody MapContext update){
		// 验证数据有效性
		RequestResult result = SystemConfig.validateFields(update);
		if(null != result){
			return result;
		}
		return this.configFacade.putSystemConfig(cfgId,update);
	}

	/**
	 * 创建微信菜单
	 * @param cfgId
	 * @param update
	 * @return
	 */
	@PutMapping(value = "/syscfgs/{cfgId}/wxmenus")
	public RequestResult putWeiXinMenus(@PathVariable String cfgId, @RequestBody MapContext update){
		// 验证数据有效性
		RequestResult result = SystemConfig.validateFields(update);
		if(null != result){
			return result;
		}
		return this.configFacade.putSystemConfig(cfgId,update);
	}


	@PutMapping(value = "/storecfgs/{cfgId}")
	public RequestResult putStoreConfig(@PathVariable String cfgId, @RequestBody MapContext update){
		// 验证数据有效性
		RequestResult result = StoreConfig.validateFields(update);
		if(null != result){
			return result;
		}
		return this.configFacade.putStoreConfig(cfgId,update);
	}
	@GetMapping(value = "/cfgdatas")
	public RequestResult getConfigDates(){
		return this.configFacade.findConfigData();
	}

	@PostMapping(value = "/storecfgs/{cfgId}/files")
	public RequestResult UploadFile(@PathVariable String cfgId,@RequestParam MultipartFile file,@RequestParam Integer type){
		Map<String,Object> errInfo = new HashMap<>();
		if(file == null){
		    errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalImageType(file)){
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadAvatarMaxsize()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_SYS_RES_NOT_ALLOW_DISABLE_10013, AppBeanInjector.i18nUtil.getMessage("BIZ_SYS_RES_NOT_ALLOW_DISABLE_10013").concat(":").concat(AppBeanInjector.configuration.getUploadAvatarMaxsize()+"").concat("M"));
		}
		if(errInfo.size() > 0){
		    return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.configFacade.uploadStorecfgImage(cfgId, file, type);
	}

	@GetMapping(value = "/homenavs")
	public RequestResult storeHomeNavs(){
		return this.configFacade.findHomeNavDatas();
	}

	/**
	 * 修改首页图标的图片
	 * @param navId
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/homenavs/{navId}/files")
	public RequestResult storeHomeNavsFiles(@PathVariable String navId, @RequestParam MultipartFile file){
		Map<String,Object> errInfo = new HashMap<>();
		if(file == null){
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalImageType(file)){
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadAvatarMaxsize()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_SYS_RES_NOT_ALLOW_DISABLE_10013, AppBeanInjector.i18nUtil.getMessage("BIZ_SYS_RES_NOT_ALLOW_DISABLE_10013").concat(":").concat(AppBeanInjector.configuration.getUploadAvatarMaxsize()+"").concat("M"));
		}
		if(errInfo.size() > 0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.configFacade.putHomeNav(navId, file);
	}

	/**
	 * 修改首页图标信息
	 * @param navId
	 * @param mapContext
	 * @return
	 */
	@PutMapping(value = "/homenavs/{navId}")
	public RequestResult updateHomeNav(@PathVariable String navId, @RequestBody MapContext mapContext){
		return this.configFacade.updateMapContext(navId, mapContext);
	}

//	@PostMapping(value = "/homenavs")
//	public RequestResult insert(@RequestParam MultipartFile file, @RequestBody StoreHomeNav storeHomeNav){
//		return this.configFacade.postHomeNavImage(file, storeHomeNav);
//	}

}
