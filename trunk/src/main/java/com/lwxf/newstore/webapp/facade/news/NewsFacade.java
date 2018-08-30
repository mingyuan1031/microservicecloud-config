package com.lwxf.newstore.webapp.facade.news;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.news.News;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/20/020 17:25
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface NewsFacade extends BaseFacade {
	RequestResult addNews(News news);
	RequestResult uploadHtml(String newsTypeId, String id, MultipartFile file);
	RequestResult findByMapper(Integer pageNum, Integer pageSize, MapContext mapContext);
	RequestResult updateNews(String newsTypeId,String id,MapContext mapContext);
	RequestResult findAll(Integer pageNum,Integer pageSize);
	RequestResult deleteNews(String newsTypeId,String id);
	RequestResult updateTop(String newsTypeId,String id);
	RequestResult updateDisabled(String newsTypeId,String id);
	RequestResult uploadImgList(String newsTypeId,String id,MultipartFile[] imgList);
}
