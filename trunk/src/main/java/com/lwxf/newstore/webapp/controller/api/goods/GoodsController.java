package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.goods.GoodsFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/20/020 18:12
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goods", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsController {
	@Resource(name="goodsFacade")
	private GoodsFacade goodsFacade;
	//查询默认商品列表 创建时间倒序
	@GetMapping
	private RequestResult goodsList(@RequestParam(required = false,defaultValue = "1") Integer pageNum,@RequestParam(required = false,defaultValue = "20") Integer pageSize ,@RequestParam(required = false) Boolean disabled,
	@RequestParam(required = false) String name,@RequestParam(required = false) BigDecimal smallprice,
	@RequestParam(required =false) BigDecimal bigprice,@RequestParam(required = false) String brandId,
	@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,
	@RequestParam(required = false) String goodsTypeId,@RequestParam(required = false) String goodsTypeName,@RequestParam(required = false) String brandName){
		return goodsFacade.findGoodsDefaultByParamsForPaging(pageNum,pageSize,this.createParamsForFindList(name,disabled,smallprice,bigprice,brandId,beginDate,endDate,goodsTypeId,goodsTypeName,brandName));
	}
	//添加商品
	@PostMapping
	private RequestResult addGoods(@RequestBody Goods goods){
		goods.setViews(0);
		goods.setDisabled(true);
		goods.setTops(false);
		goods.setFreight(new BigDecimal(0));
		goods.setCreated(DateUtil.getSystemDate());
		goods.setCreator(WebUtils.getCurrUserId());
		RequestResult requestResult= goods.validateFields();
		if(requestResult!=null){
			return requestResult;
		}
		return goodsFacade.addGoods(goods);
	}
	@GetMapping("home")
	private RequestResult findForHomePage(){
		return goodsFacade.findForHomePage();
	}
	//修改商品基本信息
	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id,@RequestBody MapContext mapContext){
		RequestResult result= Goods.validateFields(mapContext);
		if(result==null){
			return goodsFacade.updata(id,mapContext);
		}
		return result;
	}
	@GetMapping("{id}")
	private RequestResult findById(@PathVariable String id){
		return goodsFacade.findById(id);
	}
	@PostMapping("{goodsId}/files")
	private RequestResult uploadGoodsImage(@PathVariable String goodsId,@RequestBody MultipartFile file){
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
		return this.goodsFacade.uploadGoodsImage(goodsId,file);
	}

	@PutMapping("{goodsId}/contents")
	private RequestResult updataContents(@PathVariable String goodsId,@RequestBody MapContext map){

		return this.goodsFacade.updataContent(goodsId,null,(String)map.get("content"));
	}

	private MapContext createParamsForFindList(String name, Boolean disabled,BigDecimal smallprice,BigDecimal bigprice,String brandId,String beginDate,String endDate,String goodsTypeId,String goodsTypeName,String brandName){
		MapContext params = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			params.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		if(disabled != null){
			params.put(WebConstant.KEY_ENTITY_DISABLED,disabled);
		}
		if(smallprice!=null){
			params.put("smallprice",smallprice);
		}
		if(bigprice!=null){
			params.put("bigprice",bigprice);
		}
		if(smallprice!=null){
			params.put("smallprice",smallprice);
		}
		if(brandId!=null){
			params.put("brandId",brandId);
		}
		if(beginDate!=null){
			params.put("beginDate",beginDate);
		}
		if(endDate!=null){
			params.put("endDate",endDate);
		}
		if (goodsTypeId!=null){
			params.put("goodsTypeId",goodsTypeId);
		}
		if(goodsTypeName!=null){
			params.put("goodsTypeName",goodsTypeName);
		}
		if(brandName!=null){
			params.put("brandName",brandName);
		}
		return params;
	}

}
