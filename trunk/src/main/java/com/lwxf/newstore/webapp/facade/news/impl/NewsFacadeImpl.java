package com.lwxf.newstore.webapp.facade.news.impl;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.file.FileUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.news.NewsImgService;
import com.lwxf.newstore.webapp.bizservice.news.NewsService;
import com.lwxf.newstore.webapp.bizservice.news.NewsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.news.News;
import com.lwxf.newstore.webapp.domain.entity.news.NewsImg;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.news.NewsFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 17:25
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("newsFacade")
public class NewsFacadeImpl extends BaseFacadeImpl implements NewsFacade {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Resource(name = "newsService")
	private NewsService newsService;
	@Resource(name = "newsTypeService")
	private NewsTypeService newsTypeService;
	@Resource(name = "newsImgService")
	private NewsImgService newsImgService;

	@Override
	@Transactional
	public RequestResult addNews(News news) {
		NewsType newsType = this.newsTypeService.findById(news.getNewsTypeId());
		//名称不允许为空
		if (news.getName() == null || news.getName().trim().equals("")) {
			Map<String, String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME, com.lwxf.commons.exception.ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR, validResult);
		}
		//分类不存在或者被禁用
		if (newsType == null || newsType.getDisabled()) {
			return ResultFactory.generateResNotFoundResult();
		}
		this.newsService.add(news);
		return ResultFactory.generateRequestResult(news);
	}

	@Override
	@Transactional
	public RequestResult uploadHtml(String newsTypeId, String id, MultipartFile file) {
		News news = this.newsService.findById(id);
		//资源是否存在,以及是否分类无误
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateResNotFoundResult();
		}
		//上传文件
		try {
			//获取classes下的static的根目录
			String dir = AppBeanInjector.configuration.getUploadFileRootDir();
			//设定news的目录
			String basePath = AppBeanInjector.configuration.getUploadNewsFile();
			//设定数据库存储路径
			String path = basePath.concat("/").concat(newsTypeId).concat("/").concat(id).concat(WebConstant.KEY_NEWS_HTML);
			//根目录拼上news目录
			String filePath = dir.concat(basePath);
			//判断文件上传的目录是否存在,不存在就创建
			FileUtil.createDir(filePath);
			//拼接上分类id
			filePath = filePath.concat("/").concat(newsTypeId);
			//判断该分类的文件夹是否存在,不存在就创建
			FileUtil.createDir(filePath);
			//目标文件夹存在无误,就拼接上文件id以及后缀
			filePath = filePath.concat("/").concat(id).concat(WebConstant.KEY_NEWS_HTML);
			//上传无误的话 并且之前资源存在 就删除原来的 删除原来的文件 并修改数据
			if (news.getPath() != null) {
				//删除原来的资源文件
				FileUtil.delete(dir.concat(news.getPath()));
			}
			//创建文件
			file.transferTo(new File(filePath));
			MapContext mapContext = MapContext.newOne();
			mapContext.put(WebConstant.KEY_ENTITY_ID, id);
			mapContext.put("path", path);
			this.newsService.updateByMapContext(mapContext);
			return ResultFactory.generateRequestResult(path);
		} catch (IOException e) {
			this.logger.error("上传新闻动态出现异常", e);
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_IO_EXCEPTION_00007, AppBeanInjector.i18nUtil.getMessage("SYS_IO_EXCEPTION_00007"));
		}
	}

	@Override
	public RequestResult findByMapper(Integer pageNum, Integer pageSize, MapContext mapContext) {
		PaginatedFilter filter = new PaginatedFilter();
		filter.setFilters(mapContext);
		Pagination pagination = new Pagination();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		return ResultFactory.generateRequestResult(this.newsService.findByMapper(filter));
	}

	@Override
	@Transactional
	public RequestResult updateNews(String newsTypeId, String id, MapContext mapContext) {
		News news = this.newsService.findById(id);
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateResNotFoundResult();
		}
		//名称不允许为空
		if (mapContext.get("name") == null || ((String) mapContext.get("name")).trim().equals("")) {
			Map<String, String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME, com.lwxf.commons.exception.ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR, validResult);
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID, id);
		this.newsService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findAll(Integer pageNum,Integer pageSize) {
		PaginatedFilter filter = new PaginatedFilter();
		Pagination pagination = new Pagination();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		return ResultFactory.generateRequestResult(this.newsService.findAll(filter));
	}

	@Override
	@Transactional
	public RequestResult deleteNews(String newsTypeId, String id) {
		News news = this.newsService.findById(id);
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateSuccessResult();
		}
		if(news.getPath()!=null){
			//获取classes下的static的根目录
			String dir = AppBeanInjector.configuration.getUploadFileRootDir().concat(news.getPath());
			//删除资源文件
			if(news.getPath()!=null){
				FileUtil.delete(dir);
			}
		}
		this.newsService.deleteById(id);
		//删除图片资源文件
		String imgPath = AppBeanInjector.configuration.getUploadFileRootDir().concat(AppBeanInjector.configuration.getUploadNewsFile()).concat("/").concat(newsTypeId).concat("/").concat(id);
		AppBeanInjector.baseFileUploadComponent.deleteFileByDir(imgPath);
		this.newsImgService.deleteByNewsId(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updateTop(String newsTypeId, String id) {
		News news = this.newsService.findById(id);
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateResNotFoundResult();
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID, id);
		if (news.getTop()) {
			mapContext.put("top", false);
		} else {
			mapContext.put("top", true);
		}
		this.newsService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updateDisabled(String newsTypeId, String id) {
		News news = this.newsService.findById(id);
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateResNotFoundResult();
		}
		//页面资源不允许为空
		if (news.getPath() == null) {
			Map<String, String> validResult = new HashMap<>();
			validResult.put("path", com.lwxf.commons.exception.ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR, validResult);
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID, id);
		if (news.getDisabled()) {
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED, false);
		} else {
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED, true);
		}
		this.newsService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult uploadImgList(String newsTypeId, String id, MultipartFile[] imgList) {
		News news = this.newsService.findById(id);
		//资源是否存在,以及是否分类无误
		if (news == null || !news.getNewsTypeId().equals(newsTypeId)) {
			return ResultFactory.generateResNotFoundResult();
		}
		Set set = new HashSet<String>();
		for (MultipartFile file : imgList) {
			set.add(file.getOriginalFilename());
			if (this.newsImgService.isExistByNewsIdAndName(id, file.getOriginalFilename())) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NON_REPEATABLE_UPLOAD_10064, AppBeanInjector.i18nUtil.getMessage("BIZ_NON_REPEATABLE_UPLOAD_10064"));
			}
		}
		if (set.size() != imgList.length) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NON_REPEATABLE_UPLOAD_10064, AppBeanInjector.i18nUtil.getMessage("BIZ_NON_REPEATABLE_UPLOAD_10064"));
		}
		for (MultipartFile file : imgList) {
			NewsImg newsImg = new NewsImg();
			newsImg.setName(file.getOriginalFilename());
			newsImg.setNewsTypeId(newsTypeId);
			newsImg.setCreated(DateUtil.getSystemDate());
			newsImg.setNewsId(id);
			//上传文件
			//获取classes下的static的根目录
			String dir = AppBeanInjector.configuration.getUploadFileRootDir();
			//设定news的目录
			String basePath = AppBeanInjector.configuration.getUploadNewsFile();
			//根目录拼上news目录
			String filePath = dir.concat(basePath);
			//判断文件上传的目录是否存在,不存在就创建
			FileUtil.createDir(filePath);
			//拼接上分类id
			filePath = filePath.concat("/").concat(newsTypeId);
			//判断该分类的文件夹是否存在,不存在就创建
			FileUtil.createDir(filePath);
			//拼上动态的id
			filePath = filePath.concat("/").concat(id);
			//判断是否存在 不存在就新建
			FileUtil.createDir(filePath);
			//拼上图片的名称
			filePath = filePath.concat("/").concat(file.getOriginalFilename());
			//给实体类赋值图片路径
			newsImg.setPath(basePath.concat("/").concat(newsTypeId).concat("/").concat(id).concat("/").concat(file.getOriginalFilename()));
			try {
				file.transferTo(new File(filePath));
			} catch (IOException e) {
				this.logger.error("上传新闻动态出现异常", e);
				return ResultFactory.generateErrorResult(ErrorCodes.SYS_IO_EXCEPTION_00007, AppBeanInjector.i18nUtil.getMessage("SYS_IO_EXCEPTION_00007"));
			}
			this.newsImgService.add(newsImg);
		}
		return ResultFactory.generateSuccessResult();
	}
}
