package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsTypeFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/16/016 14:56
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsTypeFacade")
public class GoodsTypeFacadeImpl extends BaseFacadeImpl implements GoodsTypeFacade {
	@Resource(name="goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name="goodsSpecService")
	private GoodsSpecService goodsSpecService;
	@Resource(name="specOptionService")
	private SpecOptionService specOptionService;

	@Override
	public RequestResult findAll(){
		MapContext mapContext =MapContext.newOne();
		mapContext.put("goodsType",this.goodsTypeService.findall());
		mapContext.put("typeSpec",this.goodsSpecService.findAll());
		mapContext.put("specOption",this.specOptionService.findAll());
		return ResultFactory.generateRequestResult(mapContext);
	}

	@Override
	public RequestResult findById(String id) {
		if(this.goodsTypeService.isExist(id)){
			List typeList = new ArrayList<GoodsType>();
			GoodsType goodsType= this.goodsTypeService.findById(id);
			typeList.add(this.goodsTypeService.findById(goodsType.getParentId()));
			typeList.add(goodsType);
			return ResultFactory.generateRequestResult(typeList);
		}
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult updataGoodsType(String id, MapContext mapContext) {
		GoodsType goodsType=this.goodsTypeService.findById(id);

		if(null!=goodsType){

			//不修改是否禁用 走if 进行判断当前数据及父类数据 有无被禁用情况
			if(mapContext.get("disabled")==null){
				//判断该分类是否商品引用 或者下级被商品引用
				Map<String,Object> map= isExistGoodsByTypeId(id);
				if(map!=null&&map.get("num")!=null&&map.get("num").equals("1")){
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
				}
				if(goodsType.getDisabled()){
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
				}
				String parentId =  goodsType.getParentId();
				//是否有上一级
				if (null != parentId){
					GoodsType parentGoodsType = this.goodsTypeService.findById(parentId);
					//上一级是否为null
					if(parentGoodsType==null){
						Map<String,String> validResult = new HashMap<>();
						validResult.put("parentId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
						return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
					}else if(parentGoodsType.getDisabled()==true){//上级是否被禁用
						return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
					}else if(parentGoodsType.getParentId()!=null){//上级是否还存在上级  规定只允许有两级分类
						Map<String,String> validResult = new HashMap<>();
						validResult.put("parentId",ErrorCodes.VALIDATE_RES_NOT_ADD_LOWER_LEVERL_20037);
						return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
					}
				}
			}
			//是否是修改分类名称操作
			String name = (String) mapContext.get("name");
			if(name!=null){
				GoodsType GoodsType = this.goodsTypeService.findByNameAndParentId(name,goodsType.getParentId());
				//所要修改的分类名存在且id和当前修改的数据id不同 则返回不可重复异常(防止分类名未修改却受到不可重复异常)
				if(GoodsType!=null&&!GoodsType.getId().equals(id)){
					Map<String,String> validResult = new HashMap<>();
					validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
					return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
				}
			}
			//当进行修改禁用字段 或者 当前数据 及父类无禁用 进行 修改操作
			mapContext.put(WebConstant.KEY_ENTITY_ID,id);
			return ResultFactory.generateRequestResult(this.goodsTypeService.updateByMapContext(mapContext));
		}
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setParams(id);
		tsManualData.setClazz(GoodsType.class);
		tsManualData.setEvent(SystemActivityEvent.GOODSTYPE_DELETE.getValue());

		//判断该分类是否商品引用 或者下级被商品引用
		Map<String,Object> map= isExistGoodsByTypeId(id);
		if(map!=null&&map.get("num")!=null&&map.get("num").equals("1")){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		//没有被引用 就删除其中的判断元素
		map.remove("num");
		//然后便利map 把存储的所要删除的下级元素的id 取出来 然后删除
		for (String o : map.keySet()) {
			goodsTypeService.deleteById((String) map.get(o));
		}
		//下级删除完毕 没有异常 且当当前级为一级时  删除所属规格及规格选项 分类都没有被用  那下面的规格也不会被用到 所以不需要再去查询判断
		GoodsType goodsType=goodsTypeService.findById(id);
		if(goodsType!=null&&goodsType.getParentId()==null){
			List<GoodsSpec> goodsSpecList= goodsSpecService.findByTypeId(id);
			for (GoodsSpec goodsSpec:goodsSpecList) {
				specOptionService.deleteBySpecId(goodsSpec.getId());
			}
			goodsSpecService.deleteByTypeId(id);
		}
		//删除当前级
		goodsTypeService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult addGoodsType(GoodsType goodsType) {
		if(goodsType.getParentId()!=null){
			GoodsType parentGoodsType = this.goodsTypeService.findById(goodsType.getParentId());
			//上一级是否为null
			if(parentGoodsType==null){
				Map<String,String> validResult = new HashMap<>();
				validResult.put("parentId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(parentGoodsType.getDisabled()==true){//上级是否被禁用
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
			}else if(parentGoodsType.getParentId()!=null){//上级是否还存在上级  规定只允许有两级分类
				Map<String,String> validResult = new HashMap<>();
				validResult.put("parentId",ErrorCodes.VALIDATE_RES_NOT_ADD_LOWER_LEVERL_20037);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
		}
		if(this.goodsTypeService.findByNameAndParentId(goodsType.getName(),goodsType.getParentId())!=null){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(goodsType.getName().trim().equals("")){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		goodsTypeService.add(goodsType);
		return ResultFactory.generateRequestResult(goodsType);
	}

	@Override
	public RequestResult findTypeAll() {
		return ResultFactory.generateRequestResult(this.goodsTypeService.findall());
	}


	public 	Map<String,Object> isExistGoodsByTypeId(String id){
		Map<String,Object> map = new HashMap<String, Object>();
		//如果当前级别被引用 报出异常
		if(goodsService.isExistByGoodsTypeId(id)){
			map.put("num","1");
			return map;
		}
		//当前不被引用查询下级是存在 是否被引用
		List<GoodsType> goodsTypes= goodsTypeService.findByParentId(id);
		//下级不存在就返回false
		if(goodsTypes==null){
			return map;
		}
		//下级存在就进行便利
		for (GoodsType goodsType : goodsTypes) {
			//下级是否存在被引用
			if(goodsService.isExistByGoodsTypeId(goodsType.getId())){
				map.put("num","1");
				return map;
			}else{
				//下级不被引用就判断下下级是否存在
				List<GoodsType> goodsTypes1= goodsTypeService.findByParentId(goodsType.getId());
				//不存在就跳过本次循环
				if(goodsTypes1==null){
					continue;
				}else{
					//下下级存在 就便利下下级
					for(GoodsType goodsType1:goodsTypes1){
						//下下级存在引用就返回异常
						if(goodsService.isExistByGoodsTypeId(goodsType1.getId())){
							map.put("num","1");
							return map;
						}
						map.put(goodsType1.getId(),goodsType1.getId());
					}
				}
				map.put(goodsType.getId(),goodsType.getId());
			}
		}
		return map;
	}

}
