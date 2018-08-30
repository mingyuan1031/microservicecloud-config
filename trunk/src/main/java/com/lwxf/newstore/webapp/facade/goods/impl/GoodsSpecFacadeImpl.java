package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsSpecFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:42
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsSpecFacade")
public class GoodsSpecFacadeImpl extends BaseFacadeImpl implements GoodsSpecFacade {

	@Resource(name = "goodsSpecService")
	private GoodsSpecService goodsSpecService;
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Resource(name="goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Override
	public RequestResult findByTypeId(String id) {
		if(goodsTypeService.isExist(id)){
			return ResultFactory.generateRequestResult(goodsSpecService.findByTypeId(id));
		}
		return ResultFactory.generateResNotFoundResult();
	}


	@Override
	public RequestResult findAll() {
		return ResultFactory.generateRequestResult(goodsSpecService.findAll());
	}

	@Override
	@Transactional
	public RequestResult addGoodsSpec(GoodsSpec goodsSpec) {
		GoodsType goodsType= this.goodsTypeService.findById(goodsSpec.getGoodsTypeId());
		if(null==goodsType){
			return ResultFactory.generateResNotFoundResult();
		}
		if(null==goodsType.getParentId()||goodsType.getParentId().equals("")){
			if(this.goodsSpecService.isExistByName(goodsType.getId(),goodsSpec.getName())){
				Map<String,String> validResult = new HashMap<>();
				validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			goodsSpecService.add(goodsSpec);
			return ResultFactory.generateRequestResult(goodsSpec);
		}
		return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NOT_ALLOW_OPERATION_10020,AppBeanInjector.i18nUtil.getMessage("BIZ_NOT_ALLOW_OPERATION_10020"));
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id,String goodsTypeId) {
		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setParams(id);
		tsManualData.setClazz(GoodsSpec.class);
		tsManualData.setEvent(SystemActivityEvent.GOODSTYPE_SPEC_DELETE.getValue());

		GoodsSpec goodsSpec = this.goodsSpecService.findById(id);
		if(goodsSpec==null){
			return ResultFactory.generateSuccessResult();
		}
		if(!goodsSpec.getGoodsTypeId().equals(goodsTypeId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		List<SpecOption> optionList= this.specOptionService.findBySpecId(id);
		if(optionList==null){
			this.goodsSpecService.deleteById(id);
			return ResultFactory.generateSuccessResult();
		}
		for (SpecOption specOption: optionList){
			if(this.goodsExtendService.isExistByLikeOptions(specOption.getId())){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
			}
		}
		for(SpecOption specOption: optionList){
			this.specOptionService.deleteById(specOption.getId());
		}
		this.goodsSpecService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updata(String id, MapContext mapContext,String goodsTypeId) {
		GoodsSpec goodsSpec= this.goodsSpecService.findById(id);

		//判断该规格是否存在
		if(goodsSpec==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsSpec.getGoodsTypeId().equals(goodsTypeId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		//判断该规格是否更新名称
		if(mapContext.get("name")!=null&&goodsSpecService.isExistByName(id,(String) mapContext.get("name"))){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		List<SpecOption> optionList= this.specOptionService.findBySpecId(goodsSpec.getId());
		//判断是否有规格选项
		if(optionList!=null){
			for (SpecOption option :optionList) {
				if (goodsExtendService.isExistByLikeOptions(option.getId())){
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
				}
			}
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.goodsSpecService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}
}
