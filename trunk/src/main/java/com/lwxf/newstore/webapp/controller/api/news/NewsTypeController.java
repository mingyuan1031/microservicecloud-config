package com.lwxf.newstore.webapp.controller.api.news;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.news.NewsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;
import com.lwxf.newstore.webapp.facade.news.NewsTypeFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 9:41
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/newstypes", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class NewsTypeController {
	@Resource(name="newsTypeFacade")
	private NewsTypeFacade newsTypeFacade;

	/**
	 * 添加分类
	 * @param newsType
	 * @return
	 */
	@PostMapping
	private RequestResult addTypes(@RequestBody NewsType newsType){
		newsType.setDisabled(false);
		RequestResult result = newsType.validateFields();
		if(result==null){
			return this.newsTypeFacade.addType(newsType);
		}
		return result;
	}

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	private RequestResult deleteType(@PathVariable String id){
		return this.newsTypeFacade.deleteType(id);
	}

	/**
	 * 查询全部分类
	 * @return
	 */
	@GetMapping
	private RequestResult findAll(){
		return this.newsTypeFacade.findAll();
	}

	/**
	 * 修改分类状态
	 * @param id
	 * @return
	 */
	@PutMapping("{id}/disabled")
	private RequestResult update(@PathVariable String id){
		return this.newsTypeFacade.updateDisabled(id);
	}

	/**
	 * 修改分类名称
	 * @param mapContext
	 * @param id
	 * @return
	 */
	@PutMapping("{id}")
	private RequestResult updateName(@RequestBody MapContext mapContext,@PathVariable String id){
		RequestResult result = NewsType.validateFields(mapContext);
		if(result!=null){
			return result;
		}
		return this.newsTypeFacade.updateName(mapContext,id);
	}
}
