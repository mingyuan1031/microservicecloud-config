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
import com.lwxf.newstore.webapp.domain.dao.news.NewsImgDao;
import com.lwxf.newstore.webapp.bizservice.news.NewsImgService;
import com.lwxf.newstore.webapp.domain.entity.news.NewsImg;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-22 14:09:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("newsImgService")
public class NewsImgServiceImpl extends BaseServiceImpl<NewsImg, String, NewsImgDao> implements NewsImgService {


	@Resource

	@Override	public void setDao( NewsImgDao newsImgDao) {
		this.dao = newsImgDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<NewsImg> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public Boolean isExistByNewsIdAndName(String id, String imgName) {
		return this.dao.isExistByNewsIdAndName(id,imgName);
	}

	@Override
	public List<NewsImg> findByNewsId(String newsId) {
		return this.dao.findByNewsId(newsId);
	}

	@Override
	public int deleteByNewsId(String newsId) {
		return this.dao.deleteByNewsId(newsId);
	}

}