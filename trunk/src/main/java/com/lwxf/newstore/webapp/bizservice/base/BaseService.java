package com.lwxf.newstore.webapp.bizservice.base;



import java.io.Serializable;

import com.lwxf.mybatis.utils.MapContext;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:26:47
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BaseService<T, K extends Serializable> {
	K getNextId();

	String getNextStrId();

	int add(T entity);

	int updateByMapContext(MapContext mapContext);

	int deleteById(K id);

	T findById(K id);

	Boolean isExist(K id);


}
