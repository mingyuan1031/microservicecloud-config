package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsShowService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.goods.GoodsFacade;
import com.lwxf.newstore.webapp.facade.goods.GoodsShowFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/4/004 14:35
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goods/{goodsId}/goodsshows", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsShowController {
	@Resource(name = "goodsShowFacade")
	private GoodsShowFacade goodsShowFacade;

	@PostMapping("files")
	private RequestResult uploadGoodsShowFile(@PathVariable String goodsId, @RequestBody MultipartFile file){
		Map<String, Object> errInfo = new HashMap<>();
		if (file == null) {
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalImageType(file)) {
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadBackgroundMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadBackgroundMaxsize()));
		}
		if (errInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return  goodsShowFacade.uploadGoodsShowFile(goodsId,file);
	}
	@GetMapping
	private RequestResult findAll(@PathVariable String goodsId){
		return goodsShowFacade.findAll(goodsId);
	}
	@PutMapping("{id}")
	private RequestResult setDefault(@PathVariable String id,@PathVariable String goodsId){
		return goodsShowFacade.setDefault(id,goodsId);
	}
	@DeleteMapping("{id}")
	private RequestResult deleteById(@PathVariable String id,@PathVariable String goodsId){
		return goodsShowFacade.deleteById(id,goodsId);
	}
}
