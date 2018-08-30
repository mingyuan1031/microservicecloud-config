package com.lwxf.newstore.webapp.controller.api.order;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.CityArea;
import com.lwxf.newstore.webapp.facade.order.CityAreaFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/21 11:16
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/cities", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class CityAreaController {

	@Resource(name = "cityAreaFacade")
	private CityAreaFacade cityAreaFacade;

	@GetMapping("/{id}")
	public CityArea findCityAreaById(@PathVariable("id")  String id)
	{
        return  this.cityAreaFacade.findCityAreaById(id);
	}
	@GetMapping
	public RequestResult getCityAreaList(@RequestParam(required = false,defaultValue = "1") Integer pageNum,@RequestParam(required = false,defaultValue = "50") Integer pageSize,
										 @RequestParam(required = false)Integer levelType,@RequestParam(required = false)String parentId,@RequestParam(required = false)String name)
	{

		return  this.cityAreaFacade.selectCityAreaList(pageNum,pageSize,this.createParamsForFindList(name,parentId,levelType));
	}
	private MapContext createParamsForFindList(String name, String parentId, Integer levelType){
		MapContext params = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			params.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		if(LwxfStringUtils.isNotBlank(parentId)){
			params.put(WebConstant.KEY_ENTITY_PARENTID,parentId);
		}
		if(levelType != null){
			params.put(WebConstant.KEY_ENTITY_LEVELTYPE,levelType);
		}
		return params;
	}


}
