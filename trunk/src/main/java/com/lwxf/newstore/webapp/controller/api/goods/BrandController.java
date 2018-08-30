package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.goods.BrandFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/15/015 14:49
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/brands", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class BrandController {
	@Resource(name= "brandFacade")
	private BrandFacade brandFacade;
	//查询全部
	@GetMapping
	public RequestResult brandList(@RequestParam(required = false) Boolean disabled,@RequestParam(required = false) String name){
		MapContext mapContext = this.createParamsForFindList(name,disabled);
		if(mapContext.size()>0){
			return brandFacade.findListByParams(mapContext);
		}
		return brandFacade.findAll();
	}
	//删除 只允许单个删除
	@DeleteMapping("{id}")
	public RequestResult deleteIds(@PathVariable String id){
		return brandFacade.deleteById(id);
	}
	//添加品牌(名称及默认显示)
	@PostMapping
	public RequestResult addBrand(@RequestBody Brand brand){
		//验证数据有效性
		brand.setDisabled(false);
		RequestResult requestResult = brand.validateFields();
		if(requestResult==null){
			return brandFacade.addBrand(brand);
		}
		return requestResult;
	}

	//查询品牌详情
	@GetMapping("/{id}")
	public RequestResult findById(@PathVariable String id){
		return brandFacade.findById(id);
	}
	//更新(完善)品牌信息
	@PutMapping("/{id}")
	public RequestResult updataBrand(@PathVariable String id,@RequestBody MapContext mapContext){
		// 验证数据有效性
		RequestResult result = Brand.validateFields(mapContext);
		if(null != result){
			return result;
		}
		return brandFacade.updataBrand(id,mapContext);
	}
	@PostMapping("/{brandId}/files")
	public RequestResult uploadFile(@PathVariable String brandId, @RequestParam MultipartFile file) {
		Map<String, Object> errInfo = new HashMap<>();
		if (file == null) {
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalImageType(file)) {
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadAvatarMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadAvatarMaxsize()));
		}
		if (errInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.brandFacade.uploadBrandImage(brandId,file);
	}
	private MapContext createParamsForFindList(String name,Boolean disabled){
		MapContext params = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			params.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		if(disabled != null){
			params.put("disabled",false);
		}
		return params;
	}
}
