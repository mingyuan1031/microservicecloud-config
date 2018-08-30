package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;
import com.lwxf.newstore.webapp.facade.goods.TagFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-22 17:49
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/tags", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class TagController {
	@Resource(name = "tagFacade")
	private TagFacade tagFacade;
	@PostMapping
	public RequestResult addTag(@RequestBody Tag tag){
		RequestResult validResult = tag.validateFields();
		if(null != validResult){
			return validResult;
		}
		return this.tagFacade.add(tag);
	}

	@PutMapping(value = "/{tagId}")
	public RequestResult updateTag(@PathVariable String tagId, @RequestBody MapContext update){
		RequestResult validResult = Tag.validateFields(update);
		if(null != validResult){
			return validResult;
		}
		return this.tagFacade.updateByMapContext(tagId,update);
	}

	@DeleteMapping(value = "/{tagId}")
	public RequestResult deleteTag(@PathVariable String tagId){
		return this.tagFacade.deleteById(tagId);
	}

	@GetMapping(value = "/{tagId}")
	public RequestResult findOneTagById(@PathVariable String tagId){
		return this.tagFacade.findById(tagId);
	}
//	通过判断条件查询 判断条件全无时查询全部
	@GetMapping
	public RequestResult findListByParams(@RequestParam(required = false) String name, @RequestParam(required = false) String color, @RequestParam(required = false) Boolean disabled){
		System.out.println(name+"   "+color);
		MapContext parms = this.createParamsForFindList(name,color,disabled);
		if(parms.size()>0){
			return this.tagFacade.findListByParams(parms);
		}
		return this.tagFacade.findAll();
	}

	/**
	 * 分页查询的例子（仅供参考，在实际情况中对品牌的查询不存在这种请求）
	 * @param name
	 * @param color
	 * @param disabled
	 * @return
	 */
	//@GetMapping
	public RequestResult findListByParamsForPaging(@RequestParam(required = false,defaultValue = "1") Integer pageNum,@RequestParam(required = false,defaultValue = "50") Integer pageSize,@RequestParam(required = false) String name,@RequestParam(required = false) String color,@RequestParam(required = false) Boolean disabled){
		return this.tagFacade.findListByParamsForPaging(pageNum,pageSize,this.createParamsForFindList(name,color,disabled));
	}

	private MapContext createParamsForFindList(String name,String color,Boolean disabled){
		MapContext params = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			params.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		if(LwxfStringUtils.isNotBlank(color)){
			params.put(WebConstant.KEY_ENTITY_COLOR,color);
		}
		if(disabled != null){
			params.put(WebConstant.KEY_ENTITY_DISABLED,disabled);
		}
		return params;
	}
}
