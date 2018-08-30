package com.lwxf.newstore.webapp.controller.api.news;

import me.chanjar.weixin.common.util.DataUtils;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.news.News;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.news.NewsFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 17:24
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/news", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class NewsController {
	@Resource(name = "newsFacade")
	private NewsFacade newsFacade;

	/**
	 * 多条件查询企业动态列表
	 * @param newsTypeId
	 * @param pageSize
	 * @param pageNum
	 * @param disabled
	 * @param name
	 * @return
	 */
	@GetMapping("{newsTypeId}")
	private RequestResult findByMapper(@PathVariable String newsTypeId,@RequestParam(required = false,defaultValue = "10") Integer pageSize,
	@RequestParam(required = false,defaultValue = "1") Integer pageNum,@RequestParam(required = false) Boolean disabled,
	@RequestParam(required = false) String name){
		return this.newsFacade.findByMapper(pageNum,pageSize,createrMapper(disabled,name,newsTypeId));
	}

	/**
	 * 查询全部企业动态
	 * @return
	 */
	@GetMapping
	private RequestResult findAll(@RequestParam(required = false,defaultValue = "1") Integer pageNum,@RequestParam(required = false,defaultValue = "10") Integer pageSize){
		return this.newsFacade.findAll(pageNum,pageSize);
	}
	/**
	 * 添加企业资讯
	 * @param news
	 * @return
	 */
	@PostMapping("{newsTypeId}")
	private RequestResult addNews(@RequestBody News news,@PathVariable String newsTypeId){
		news.setCreated(DateUtil.getSystemDate());
		news.setCreator(WebUtils.getCurrUserId());
		news.setDisabled(true);
		news.setTop(false);
		news.setNewsTypeId(newsTypeId);
		RequestResult result = news.validateFields();
		if(result==null){
			return this.newsFacade.addNews(news);
		}
		return result;
	}

	/**
	 * 上传企业资讯的页面资源
	 * @param newsTypeId
	 * @param id
	 * @param files
	 * @return
	 */
	@PutMapping("{newsTypeId}/{id}/htmls")
	private RequestResult uploadHtml(@PathVariable String newsTypeId, @PathVariable String id,@RequestBody MultipartFile files){
		Map<String, Object> errInfo = new HashMap<>();
		if (files == null) {
			errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
		} else if (!FileMimeTypeUtil.isLegalHtmlType(files)) {
			errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		} else if (files.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadHtmlMaxsize()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadHtmlMaxsize()));
		}
		if (errInfo.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
		}
		return this.newsFacade.uploadHtml(newsTypeId,id,files);
	}

	/**
	 * 修改企业动态基础信息
	 * @param newsTypeId
	 * @param id
	 * @param mapContext
	 * @return
	 */
	@PutMapping("{newsTypeId}/{id}")
	private RequestResult updateNews(@PathVariable String newsTypeId,@PathVariable String id,@RequestBody MapContext mapContext){
		RequestResult result = News.validateFields(mapContext);
		if(result!=null){
			return result;
		}
		return this.newsFacade.updateNews(newsTypeId,id,mapContext);
	}

	/**
	 * 修改企业动态置顶状态
	 * @param newsTypeId
	 * @param id
	 * @return
	 */
	@PutMapping("{newsTypeId}/{id}/top")
	private RequestResult updateTop(@PathVariable String newsTypeId,@PathVariable String id){
		return this.newsFacade.updateTop(newsTypeId,id);
	}

	/**
	 * 修改企业动态的禁用状态
	 * @param newsTypeId
	 * @param id
	 * @return
	 */
	@PutMapping("{newsTypeId}/{id}/disabled")
	private RequestResult updateDisabled(@PathVariable String newsTypeId,@PathVariable String id){
		return this.newsFacade.updateDisabled(newsTypeId,id);
	}
	/**
	 * 删除企业动态
	 * @param newsTypeId
	 * @param id
	 * @return
	 */
	@DeleteMapping("{newsTypeId}/{id}")
	private RequestResult deleteNews(@PathVariable String newsTypeId,@PathVariable String id){
		return this.newsFacade.deleteNews(newsTypeId,id);
	}

	/**
	 * 批量上传动态图片
	 * @param fileList
	 * @param newsTypeId
	 * @param id
	 * @return
	 */
	@PostMapping("{newsTypeId}/{id}/files")
	private RequestResult uploadImgFiles(@PathVariable String newsTypeId,@PathVariable String id, @RequestBody MultipartFile... fileList){
		Map<String, Object> errInfo = new HashMap<>();
		if(null == fileList || fileList.length == 0){
			return ResultFactory.generateResNotFoundResult();
		}
		for (MultipartFile file:fileList) {
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
		}
		return this.newsFacade.uploadImgList(newsTypeId,id,fileList);
	}
	private MapContext createrMapper(Boolean disabled,String name,String newsTypeId){
		MapContext mapContext = MapContext.newOne();
		if(disabled!=null){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,disabled);
		}
		if(name!=null){
			mapContext.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		mapContext.put("newsTypeId",newsTypeId);
		return mapContext;
	}
}
