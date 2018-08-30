package com.lwxf.newstore.webapp.domain.dao.base;

import java.io.Serializable;

import com.lwxf.mybatis.utils.MapContext;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:38:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BaseDao<T, K extends Serializable> {

    int insert(T entity);

    int updateByMapContext(MapContext mapContext);

    int deleteById(K id);

    T selectById(K id);

	Boolean isExist(Class<T> entityClass, K id);

}
