
package com.lwxf.newstore.webapp.bizservice.base;

import java.io.Serializable;

import com.lwxf.newstore.webapp.domain.dao.base.BasePositionDao;
import com.lwxf.newstore.webapp.domain.entity.base.AbstractIdEntity;

/**
 * 功能：拖拽排序基本功能
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:21:58
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BasePositionServiceImpl<T extends AbstractIdEntity<K>, K extends Serializable, D extends BasePositionDao<T, K>> extends BaseServiceImpl<T, K, D> implements BasePositionService<T, K> {
	@Override
	public int batchUpdateEntityPosition(Integer position, Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		return this.dao.batchUpdateEntityPosition(position, entityClass, refFieldName, refFieldValue, refFieldName2, refFieldValue2);
	}
}