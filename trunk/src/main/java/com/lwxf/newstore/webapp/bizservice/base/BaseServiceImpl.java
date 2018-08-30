package com.lwxf.newstore.webapp.bizservice.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.lwxf.commons.uniquekey.IdGererateFactory;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.base.AbstractIdEntity;

/**
 * 功能：Service层基础服务抽象类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:27:47
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseServiceImpl<T extends AbstractIdEntity<K>, K extends Serializable, D extends BaseDao<T, K>> implements BaseService<T, K> {
	protected D dao;
	@Resource
	private IdGererateFactory idGererateFactory;
	private Class<T> entityClass;

	public BaseServiceImpl() {
		try {
			//获取泛型T的实际类型，有些实现类没有使用泛型这里报错，忽略掉 by LT
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
		}
	}

	public abstract void setDao(D dao);

	@Override
	public K getNextId() {
		return (K) this.idGererateFactory.nextStringId();
	}

	@Override
	public String getNextStrId() {
		return this.idGererateFactory.nextStringId();
	}

	@Override
	public int add(T entity) {
		entity.setId((K) getNextStrId());
		return this.dao.insert(entity);
	}

	@Override
	public int updateByMapContext(MapContext mapContext) {
		return this.dao.updateByMapContext(mapContext);
	}

	@Override
	public Boolean isExist(K id) {
		if (entityClass != null) {
			return this.dao.isExist(entityClass, id);
		}
		throw new IllegalAccessError("错误");
	}

	@Override
	public int deleteById(K id) {
		return this.dao.deleteById(id);
	}

	@Override
	public T findById(K id) {
		return this.dao.selectById(id);
	}


}
