package com.lwxf.newstore.webapp.domain.dto.news;

import com.lwxf.newstore.webapp.domain.entity.news.News;

/**
 * 功能：
 *
 * @author：dongshibo(F_baisi)
 * @create：2018/8/21/021 16:39
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class NewsDto extends News {
	private String newsTypeName;

	public String getNewsTypeName() {
		return newsTypeName;
	}

	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}
}
