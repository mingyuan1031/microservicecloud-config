package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTagService;
import com.lwxf.newstore.webapp.bizservice.goods.TagService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.TagFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-22 11:20
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("tagFacade")
public class TagFacadeImpl extends BaseFacadeImpl implements TagFacade {
	@Resource(name = "tagService")
	private TagService tagService;
	@Resource(name="goodsTagService")
	private GoodsTagService goodsTagService;
	@Override
	@Transactional
	public RequestResult add(Tag tag) {
		if(tag.getName().trim().equals("")){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		// 验证名称是否重复
		List<Tag> tags = this.tagService.findListByName(tag.getName());
		if(tags.size()>0){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}

		// 保存
		this.tagService.add(tag);
		return ResultFactory.generateRequestResult(tag);
	}

	@Override
	@Transactional
	public RequestResult updateByMapContext(String id,MapContext mapContext) {
		Tag tag = this.tagService.findById(id);
		if(null == tag){
			return ResultFactory.generateResNotFoundResult();
		}
		if(mapContext.get("disabled")==null&&tag.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
		}
		if(mapContext.containsKey(WebConstant.KEY_ENTITY_NAME)){
			String name = mapContext.getTypedValue(WebConstant.KEY_ENTITY_NAME,String.class);
			List<Tag> tags = this.tagService.findListByName(name);
			if(tags.size()>0 && !id.equals(tags.get(0).getId())){
				Map<String,String> validResult = new HashMap<>();
				validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.tagService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		if(this.goodsTagService.isExistByTagId(id)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		this.tagService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findById(String id) {
		Tag tag= this.tagService.findById(id);
		if(null==tag){
			return ResultFactory.generateResNotFoundResult();
		}
		return ResultFactory.generateRequestResult(this.tagService.findById(id));
	}

	@Override
	public RequestResult findAll() {
		return ResultFactory.generateRequestResult(this.tagService.findAll());
	}

	@Override
	public RequestResult findListByParams(MapContext params) {
		return ResultFactory.generateRequestResult(this.tagService.findListByParams(params));
	}

	@Override
	public RequestResult findListByParamsForPaging(Integer pageNum,Integer pageSize,MapContext params) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(params);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		PaginatedList<Tag> tags = this.tagService.findListByParamsForPaging(filter);
		return ResultFactory.generateRequestResult(tags);
	}
}
