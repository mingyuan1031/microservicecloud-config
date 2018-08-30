package com.lwxf.newstore.webapp.bizservice.news;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.news.NewsImg;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-22 14:09:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface NewsImgService extends BaseService <NewsImg, String> {

	PaginatedList<NewsImg> selectByFilter(PaginatedFilter paginatedFilter);
	Boolean isExistByNewsIdAndName(String id,String imgName);
	List<NewsImg> findByNewsId(String newsId);
	int deleteByNewsId(String newsId);
}