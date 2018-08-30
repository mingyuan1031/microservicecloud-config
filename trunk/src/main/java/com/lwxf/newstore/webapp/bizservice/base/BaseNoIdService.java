package com.lwxf.newstore.webapp.bizservice.base;


import com.lwxf.mybatis.utils.MapContext;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:26:47
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BaseNoIdService<T> {
	int add(T entity);
	int updateByMapContext(MapContext mapContext);
	int deleteByMapContext(MapContext mapContext);
}
