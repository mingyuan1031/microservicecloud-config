package com.lwxf.newstore.webapp.controller.api.scheme;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.domain.entity.scheme.Scheme;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.scheme.SchemeFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 10:06
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class SchemeController {

	@Resource(name = "schemeFacade")
	private SchemeFacade schemeFacade;

	/**
	 * 上传设计方案的临时图片（完成）
	 * （cover代表需要上传到是封面的图片 panorama代表传的是全景图的图片）
	 * @param files
	 * @param type
	 * @return
	 */
	@PostMapping(value = "schemes/{type}/files")
//	@PostMapping(value = "/scheme/{type}/files")
	public RequestResult uploadSchemeImage(@PathVariable String type,
										   @RequestBody MultipartFile files,
											@RequestParam(required = false) String schemeId){
		Map<String,Object> errorInfo = new HashMap<String, Object>();
		if(files==null){
			errorInfo.put("files",ErrorCodes.VALIDATE_NOTNULL);
		}else if (!FileMimeTypeUtil.isLegalImageType(files)) {
			errorInfo.put("files", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (files.getSize() > 1024L * 1024L* AppBeanInjector.configuration.getUploadBackgroundMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadBackgroundMaxsize()));
		}
		if (errorInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errorInfo);
		}
		return this.schemeFacade.uploadSchemeImage(files,type,schemeId);

	}


	/**
	 *  添加设计案例  （完成）
	 * @param scheme
	 * @return
	 */
	@PostMapping(value = "/schemes")
	public RequestResult addScheme(@RequestBody Scheme scheme,String...ids){
		return this.schemeFacade.addScheme(scheme,ids);
	}

	/**
	 *  通过id查询设计案例的详细信息
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/schemes/{id}")
	public RequestResult findById(@PathVariable String id){
		return this.schemeFacade.findById(id);
	}

	/**
	 *  查询所有的设计方案 （完成）
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@GetMapping(value = "/schemes")
	public RequestResult findAll(@RequestParam(required = false,defaultValue = "30") Integer pageSize,
								 @RequestParam(required = false,defaultValue = "1") Integer pageNum,
								@RequestBody(required = false) MapContext mapContext	){
		return this.schemeFacade.findAll(pageSize,pageNum,mapContext);
	}

	/**
	 *  通过id删除设计方案 （完成）
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/schemes/{id}")
	public RequestResult deleteById(@PathVariable String id){
		return this.schemeFacade.deleteById(id);
	}

	/**
	 * 根据id修改方案的状态（1表示启用，0表示禁用） （完成）
	 * @param id
	 * @param status
	 * @return
	 */
	@PutMapping(value = "/schemes/{id}/status/{status}")
	public RequestResult updateStatusById(@PathVariable String id,@PathVariable Integer status){
		if (StringUtils.isBlank(id)||status==null)
		{
			ResultFactory.generateErrorResult(com.lwxf.newstore.webapp.common.exceptions.ErrorCodes.SYS_ILLEGAL_ARGUMENT_00005,"参数异常");
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_STATUS,status);
		return this.schemeFacade.updateById(mapContext,id);
	}

	/**
	 * 根据id修设计方案的信息 （完成）
	 * @param mapContext
	 * @param id
	 * @return
	 */
	@PutMapping(value ="/schemes/{id}" )
	public RequestResult updateById(@RequestBody MapContext mapContext,@PathVariable String id){
		RequestResult validResult = Scheme.validateFields(mapContext);
		if(null!= validResult){
			return validResult;
		}
		if (null==this.schemeFacade.findById(id)){
			return ResultFactory.generateResNotFoundResult();
		}
		return this.schemeFacade.updateById(mapContext,id);
	}


}
