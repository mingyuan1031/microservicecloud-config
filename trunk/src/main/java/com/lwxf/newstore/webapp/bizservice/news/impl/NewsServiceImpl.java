package com.lwxf.newstore.webapp.bizservice.news.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.domain.dao.news.NewsDao;
import com.lwxf.newstore.webapp.bizservice.news.NewsService;
import com.lwxf.newstore.webapp.domain.dto.news.NewsDto;
import com.lwxf.newstore.webapp.domain.entity.news.News;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:35:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("newsService")
public class NewsServiceImpl extends BaseServiceImpl<News, String, NewsDao> implements NewsService {


	@Resource

	@Override	public void setDao( NewsDao newsDao) {
		this.dao = newsDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<News> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public Boolean findByNesTypeId(String id) {
		return this.dao.findByNesTypeId(id);
	}

	@Override
	public PaginatedList<NewsDto> findByMapper(PaginatedFilter paginatedFilter) {
		return this.dao.findByMapper(paginatedFilter);
	}

	@Override
	public PaginatedList<NewsDto> findAll(PaginatedFilter filter) {
		return this.dao.findAll(filter);
	}


	@Override
	public int add(News entity) {
		entity.setId(AppBeanInjector.uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.NEWS_ID));
		return this.dao.insert(entity);
	}
}