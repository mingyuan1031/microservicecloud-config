package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.facade.goods.GoodsTagFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/13/013 16:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goods/{goodsId}/goodstags", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsTagController {
	@Resource(name = "goodsTagFacade")
	private GoodsTagFacade goodsTagFacade;
	@PostMapping
	private RequestResult addGoodsTag(@PathVariable String goodsId,@RequestBody MapContext mapContext){
		List tagsList= (List) mapContext.get("tags");
		String [] tags = new String[tagsList.size()];
		tagsList.toArray(tags);
		return goodsTagFacade.addGoodsTag(goodsId, tags);
	}
	@DeleteMapping("{tagId}")
	private RequestResult deleteGoodsTag(@PathVariable String goodsId,@PathVariable String tagId){
		return goodsTagFacade.deleteGoodsTag(goodsId,tagId);
	}
}
