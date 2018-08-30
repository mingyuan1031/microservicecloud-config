package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.SpecOptionFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/28/028 9:33
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("specOptionFacade")
public class SpecOptionFacadeImpl extends BaseFacadeImpl implements SpecOptionFacade {
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Resource(name="goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Resource(name="goodsSpecService")
	private GoodsSpecService goodsSpecService;
	@Override
	public RequestResult findAll() {
		return ResultFactory.generateRequestResult(this.specOptionService.findAll());
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id,String goodsTypeId,String specId) {
		GoodsSpec goodsSpec = this.goodsSpecService.findById(specId);
		if(goodsSpec==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsSpec.getGoodsTypeId().equals(goodsTypeId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		SpecOption specOption = this.specOptionService.findById(id);
		if(specOption==null){
			return ResultFactory.generateSuccessResult();
		}
		if(!specOption.getGoodsSpecId().equals(specId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsSpecId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(this.goodsExtendService.isExistByLikeOptions(id)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		return ResultFactory.generateRequestResult(this.specOptionService.deleteById(id));
	}

	@Override
	@Transactional
	public RequestResult addSpecOption(SpecOption specOption,String goodsTypeId) {
		if(specOption.getName().trim().equals("")){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		GoodsSpec goodsSpec = this.goodsSpecService.findById(specOption.getGoodsSpecId());
		if(goodsSpec==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsSpec.getGoodsTypeId().equals(goodsTypeId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(this.specOptionService.isExistByName(goodsSpec.getId(),specOption.getName())){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		specOptionService.add(specOption);
		return ResultFactory.generateRequestResult(specOption);
	}

	@Override
	public RequestResult findBySpecId(String id) {
		return ResultFactory.generateRequestResult(specOptionService.findBySpecId(id));
	}

	@Override
	@Transactional
	public RequestResult updata(String id, MapContext mapContext,String specId,String goodsTypeId) {
		GoodsSpec goodsSpec = this.goodsSpecService.findById(specId);
		if(goodsSpec==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsSpec.getGoodsTypeId().equals(goodsTypeId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		SpecOption specOption= this.specOptionService.findById(id);
		if(specOption==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!specOption.getGoodsSpecId().equals(specId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsSpecId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(this.goodsExtendService.isExistByLikeOptions(id)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		if(mapContext.get("name")!=null&&specOptionService.isExistByName(specOption.getId(),(String) mapContext.get("name"))){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.specOptionService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findByOptions(String[] options) {
		List list = new ArrayList<String>();
		for (int i = 0; i <options.length ; i++) {
			SpecOption specOption= this.specOptionService.findById(options[i]);
			if(specOption==null){
				return ResultFactory.generateResNotFoundResult();
			}
			list.add(specOption.getName());
		}
		return ResultFactory.generateRequestResult(list);
	}
}
