package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.facade.goods.SpecOptionFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/28/028 9:30
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goodstype/{goodsTypeId}/spec/{specId}/options", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class SpecOptionController {
	@Resource(name = "specOptionFacade")
	private SpecOptionFacade specOptionFacade;


	@DeleteMapping("{id}")
	private RequestResult deleteById(@PathVariable String id,@PathVariable String goodsTypeId,@PathVariable String specId){
		return specOptionFacade.deleteById(id,goodsTypeId,specId);
	}

	@PostMapping
	private RequestResult addSpecOption(@RequestBody SpecOption specOption,@PathVariable String specId,@PathVariable String goodsTypeId){
		specOption.setGoodsSpecId(specId);
		RequestResult result= specOption.validateFields();
		if(result!=null){
			return result;
		}
		return specOptionFacade.addSpecOption(specOption,goodsTypeId);
	}
	@GetMapping
	private RequestResult findBySpecId(@PathVariable String specId){
		return specOptionFacade.findBySpecId(specId);
	}
	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id, @RequestBody MapContext mapContext,@PathVariable String specId,@PathVariable String goodsTypeId){
		return specOptionFacade.updata(id,mapContext,specId,goodsTypeId);
	}

}
