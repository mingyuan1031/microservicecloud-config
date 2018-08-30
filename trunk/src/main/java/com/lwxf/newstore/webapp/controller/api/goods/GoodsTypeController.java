package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;
import com.lwxf.newstore.webapp.facade.goods.GoodsSpecFacade;
import com.lwxf.newstore.webapp.facade.goods.GoodsTypeFacade;
import com.lwxf.newstore.webapp.facade.goods.SpecOptionFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/16/016 16:36
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/goodstypes", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsTypeController {
	@Resource(name="goodsTypeFacade")
	private GoodsTypeFacade goodsTypeFacade;
	@Resource(name = "goodsSpecFacade")
	private GoodsSpecFacade goodsSpecFacade;
	@Resource(name = "specOptionFacade")
	private SpecOptionFacade specOptionFacade;
	@GetMapping("all")
	public RequestResult findAll(){
		return goodsTypeFacade.findAll();
	}
	//查询全部分类
	@GetMapping("type")
	public RequestResult goodsTypeList(){
		return goodsTypeFacade.findTypeAll();
	}
	//通过id查询详情
	@GetMapping("{id}")
	public RequestResult goodsTypeById(@PathVariable String id){
		return goodsTypeFacade.findById(id);
	}
	//通过id修改详情
	@PutMapping("/{id}")
	public RequestResult goodsTypeUpdata(@PathVariable String id, @RequestBody MapContext mapContext){
		RequestResult requestResult= GoodsType.validateFields(mapContext);
		if(requestResult!=null){
			return requestResult;
		}
		return goodsTypeFacade.updataGoodsType(id,mapContext);
	}
	//删除单个
	@DeleteMapping("{id}")
	public RequestResult deleteIds(@PathVariable String id){
		return goodsTypeFacade.deleteById(id);
	}
	//添加商品分类
	@PostMapping
	public RequestResult addGoodsType(@RequestBody GoodsType GoodsType){
		//验证数据有效性
		RequestResult requestResult = GoodsType.validateFields();
		if(requestResult==null){
			return goodsTypeFacade.addGoodsType(GoodsType);
		}
		return requestResult;
	}
	@GetMapping("spec")
	private RequestResult findspecAll(){
		return goodsSpecFacade.findAll();
	}
	@GetMapping("option")
	private RequestResult findoptionAll(){
		return specOptionFacade.findAll();
	}
	@GetMapping("options")
	private RequestResult findByOptions(@RequestParam String[] options){
		return specOptionFacade.findByOptions(options);
	}
}
