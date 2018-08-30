package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;



import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;
import com.lwxf.newstore.webapp.facade.goods.GoodsSpecFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goodstype/{goodsTypeId}/specs", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsSpecController {
	@Resource(name = "goodsSpecFacade")
	private GoodsSpecFacade goodsSpecFacade;


	@GetMapping
	private RequestResult findById(@PathVariable String goodsTypeId){
		return goodsSpecFacade.findByTypeId(goodsTypeId);
	}




	@PostMapping
	private RequestResult addGoodsSpec(@PathVariable String goodsTypeId, @RequestBody GoodsSpec goodsSpec){
		goodsSpec.setGoodsTypeId(goodsTypeId);
		RequestResult result= goodsSpec.validateFields();
		if(result!=null){
			return result;
		}
		return goodsSpecFacade.addGoodsSpec(goodsSpec);
	}
	@DeleteMapping("{id}")
	private RequestResult deleteById(@PathVariable String id,@PathVariable String goodsTypeId){
		return goodsSpecFacade.deleteById(id,goodsTypeId);
	}

	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id, @RequestBody MapContext mapContext,@PathVariable String goodsTypeId){
		RequestResult result= GoodsSpec.validateFields(mapContext);
		if(result==null){
			return goodsSpecFacade.updata(id,mapContext,goodsTypeId);
		}
		return result;
	}
}
