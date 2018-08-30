package com.lwxf.newstore.webapp.domain.dao.base;

import java.io.Serializable;

/**
 * 功能：处理原计划通用位置拖拽功能
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:47:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BasePositionDao<T, K extends Serializable> extends BaseDao<T, K> {
	/**
	 * 批量更新某个实体资源表的位置信息
	 *
	 * @param position       必须
	 * @param entityClass    必须
	 * @param refFieldName   非必须
	 * @param refFieldValue  非必须（传refFieldName时必传）
	 * @param refFieldName2  非必须
	 * @param refFieldValue2 非必须（传refFieldName2时必传）
	 * @return
	 */
	int batchUpdateEntityPosition(Integer position, Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2);

	/**
	 * 获取某个实体资源表的最大位置值
	 *
	 * @param entityClass    必须
	 * @param refFieldName   非必须
	 * @param refFieldValue  非必须（传refFieldName时必传）
	 * @param refFieldName2  非必须
	 * @param refFieldValue2 非必须（传refFieldName2时必传）
	 * @return
	 */
	int selectEntityMaxPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2);

	/**
	 * 获取某个实体资源表的最小位置值
	 *
	 * @param entityClass    必须
	 * @param refFieldName   非必须
	 * @param refFieldValue  非必须（传refFieldName时必传）
	 * @param refFieldName2  非必须
	 * @param refFieldValue2 非必须（传refFieldName2时必传）
	 * @return
	 */
	int selectEntityMinPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2);
}
