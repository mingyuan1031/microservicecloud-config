package com.lwxf.newstore.webapp.domain.dao.news;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.news.NewsImg;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-22 14:09:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface NewsImgDao extends BaseDao<NewsImg, String> {

	PaginatedList<NewsImg> selectByFilter(PaginatedFilter paginatedFilter);
	Boolean isExistByNewsIdAndName(String id,String imgName);
	List<NewsImg> findByNewsId(String newsId);
	int deleteByNewsId(String newsId);
}