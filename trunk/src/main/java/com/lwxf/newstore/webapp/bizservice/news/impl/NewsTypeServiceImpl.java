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
import com.lwxf.newstore.webapp.domain.dao.news.NewsTypeDao;
import com.lwxf.newstore.webapp.bizservice.news.NewsTypeService;
import com.lwxf.newstore.webapp.domain.entity.news.NewsType;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:35:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("newsTypeService")
public class NewsTypeServiceImpl extends BaseServiceImpl<NewsType, String, NewsTypeDao> implements NewsTypeService {


	@Resource

	@Override	public void setDao( NewsTypeDao newsTypeDao) {
		this.dao = newsTypeDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<NewsType> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public Boolean isExistName(String name) {
		return this.dao.isExistName(name);
	}

	@Override
	public List<NewsType> findByParentId(String id) {
		return this.dao.findByParentId(id);
	}

	@Override
	public Boolean findByParentIds(List types) {
		return this.dao.findByParentIds(types);
	}

	@Override
	public List<NewsType> findAll() {
		return this.dao.findAll();
	}

}