package com.lwxf.newstore.webapp.bizservice.base;

import java.io.Serializable;

/**
 * 功能：拖拽排序基本功能
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:21:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BasePositionService<T, K extends Serializable> extends BaseService<T, K> {
	int batchUpdateEntityPosition(Integer position, Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2);
}
