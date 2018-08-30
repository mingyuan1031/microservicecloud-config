package com.lwxf.newstore.webapp.facade.news.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.news.NewsService;
import com.lwxf.newstore.webapp.bizservice.news.NewsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.news.NewsTypeFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 9:48
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("newsTypeFacade")
public class NewsTypeFacadeImpl extends BaseFacadeImpl implements NewsTypeFacade {
	@Resource(name = "newsTypeService")
	private NewsTypeService newsTypeService;
	@Resource(name = "newsService")
	private NewsService newsService;
	@Override
	@Transactional
	public RequestResult addType(NewsType newsType) {
		//判断名称是否为字符串的空
		if(newsType.getName().trim().equals("")){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		//判断名称是否重复
		if(this.newsTypeService.isExistName(newsType.getName())){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		//如果有父级分类,判断父级分类是是否存在,是否被禁用
		if(newsType.getParentId()!=null){
			NewsType parentNewsType= this.newsTypeService.findById(newsType.getParentId());
			if(parentNewsType==null||parentNewsType.getDisabled()){
				return ResultFactory.generateResNotFoundResult();
			}
			//如果父级分类还存在父级分类,则返回验证异常,现在规定 最多两级
			if(parentNewsType.getParentId()!=null){
				Map<String,String> valiResult = new HashMap<>();
				valiResult.put(WebConstant.KEY_ENTITY_PARENTID,ErrorCodes.VALIDATE_RES_NOT_ADD_LOWER_LEVERL_20037);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,valiResult);
			}
		}
		this.newsTypeService.add(newsType);
		return ResultFactory.generateRequestResult(newsType);
	}

	@Override
	@Transactional
	public RequestResult deleteType(String id) {
		//该分类是否被资讯引用
		if(this.newsService.findByNesTypeId(id)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
		}
		//该分类是否有下级,下级是否被资讯引用
		List<NewsType> list= this.newsTypeService.findByParentId(id);
		if(list==null||list.size()==0){
			this.newsTypeService.deleteById(id);
		}else{
			//下级是否被引用
			if(!this.newsTypeService.findByParentIds(list)){
				for (NewsType newsType:list) {
					this.newsTypeService.deleteById(newsType.getParentId());
				}
				this.newsTypeService.deleteById(id);
			}else{
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
			}
		}
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findAll() {
		return ResultFactory.generateRequestResult(this.newsTypeService.findAll());
	}

	@Override
	@Transactional
	public RequestResult updateDisabled(String id) {
		NewsType newsType= this.newsTypeService.findById(id);
		//修改的数据是否存在
		if(newsType==null){
			return ResultFactory.generateResNotFoundResult();
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		if(newsType.getDisabled()){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,false);
		}else{
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,true);
		}
		this.newsTypeService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updateName(MapContext mapContext,String id) {
		//判断该条数据是否存在
		if(!this.newsTypeService.isExist(id)){
			return ResultFactory.generateResNotFoundResult();
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		if(this.newsTypeService.isExistName((String) mapContext.get(WebConstant.KEY_ENTITY_NAME))){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		this.newsTypeService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

}
