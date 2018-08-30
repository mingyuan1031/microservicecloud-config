package com.lwxf.newstore.webapp.bizservice.base;

import java.lang.reflect.ParameterizedType;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseNoIdDao;
import com.lwxf.newstore.webapp.domain.entity.base.AbstractNoIdEntity;

/**
 * 功能：Service层基础服务抽象类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:27:47
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseNoIdServiceImpl<T extends AbstractNoIdEntity, D extends BaseNoIdDao<T>> implements BaseNoIdService<T> {
	protected D dao;
	private Class<T> entityClass;
	public BaseNoIdServiceImpl() {
		try {
			//获取泛型T的实际类型，有些实现类没有使用泛型这里报错，忽略掉 by LT
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
		}
	}
	public abstract void setDao(D dao);

	@Override
	public int add(T entity) {
		return this.dao.insert(entity);
	}

	@Override
	public int updateByMapContext(MapContext mapContext) {
		return this.dao.updateByMapContext(mapContext);
	}

	@Override
	public int deleteByMapContext(MapContext mapContext) {
		return this.dao.deleteByMapContext(mapContext);
	}
}
