package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
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
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.goods.GoodsExtendFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:18
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goods/{goodsId}/goodsextends", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsExtendController {
	@Resource(name="goodsExtendFacade")
	private GoodsExtendFacade goodsExtendFacade;

	@GetMapping
	private RequestResult findAll(@PathVariable String goodsId){
		return goodsExtendFacade.findByGoodsId(goodsId);
	}

	@PostMapping
	private RequestResult addGoodsExtends(@PathVariable String goodsId, @RequestBody List<GoodsExtend> goodsExtendList){
		RequestResult result = null;
		for (int i = 0; i <goodsExtendList.size() ; i++) {
			goodsExtendList.get(i).setGoodsId(goodsId);
			result= goodsExtendList.get(i).validateFields();
			if(result!=null){
				return result;
			}
		}
		return goodsExtendFacade.add(goodsId,goodsExtendList);
	}
	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id,@PathVariable String goodsId, @RequestBody MapContext mapContext){
		RequestResult result =  GoodsExtend.validateFields(mapContext);
		if(result!=null){
			return result;
		}
		return goodsExtendFacade.updata(id,mapContext,goodsId);
	}
	@PostMapping("{extendId}/files")
	private RequestResult uploadFile(@PathVariable String extendId, @RequestBody MultipartFile file,@PathVariable String goodsId){
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
		return this.goodsExtendFacade.uploadExtendImage(extendId,file,goodsId);
	}
}
