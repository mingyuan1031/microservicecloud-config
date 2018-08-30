package com.lwxf.newstore.webapp.domain.dao.base;

import com.lwxf.mybatis.utils.MapContext;

/**
 * 功能：没id主键的实体base dao定义
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:38:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BaseNoIdDao<T> {
    int insert(T entity);
    int updateByMapContext(MapContext mapContext);
	int deleteByMapContext(MapContext mapContext);
}
