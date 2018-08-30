package com.lwxf.newstore.webapp.domain.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.annotation.Id;
import com.lwxf.mybatis.utils.StrUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:49:26
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("basePositionDao")
public abstract class BasePositionDaoImpl<T, K extends Serializable> extends BaseDaoImpl<T, K> implements BasePositionDao<T, K> {


	public int getEntityMaxPosition(Class<?> entityClass) {
		return getEntityMaxPosition(entityClass, null, null, null, null);
	}

	public int getEntityMaxPosition(Class<?> entityClass, String refFieldName, Object refFieldValue) {
		return getEntityMaxPosition(entityClass, refFieldName, refFieldValue, null, null);
	}

	public int getEntityMaxPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		String tableName = getTableName(entityClass);
		if (tableName == null) {
			return -1;
		}
		//
		String sqlId = this.getNamedSqlId(BasePositionDao.class, "getEntityMaxPosition");
		//
		Map<String, Object> params = this.newParamMap();
		params.put("tableName", tableName);
		if (StrUtil.hasText(refFieldName)) {
			String refColName = this.getColumnName(entityClass, refFieldName);
			if (!StrUtil.hasText(refColName)) {
				return -1;
			}
			params.put("colName", refColName);
			params.put("colValue", refFieldValue);
			//
			if (StrUtil.hasText(refFieldName2)) {
				String refColName2 = this.getColumnName(entityClass, refFieldName2);
				if (!StrUtil.hasText(refColName2)) {
					return -1;
				}
				params.put("colName2", refColName2);
				params.put("colValue2", refFieldValue2);
			}
		}
		return this.getSqlSession().selectOne(sqlId, params);
	}

	@Override
	public int batchUpdateEntityPosition(Integer position, Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		String tableName = getTableName(entityClass);
		if (tableName == null) {
			return -1;
		}
		Map<String, Object> params = this.doParamsForEntityPosition(entityClass, refFieldName, refFieldValue, refFieldName2, refFieldValue2);
		if (params == null) {
			return -1;
		}
		params.put("position", position);
		params.put("tableName", tableName);

		return this.getSqlSession().update(this.getNamedSqlId(BasePositionDao.class, "batchUpdateEntityPosition"), params);
	}

	@Override
	public int selectEntityMaxPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		//2017年6月26日　17时47分　党冬　处理取最大位置值出错问题
//        String tableName = getTableName(entityClass);
//        if (tableName == null) {
//            return -1;
//        }
//        Map<String, Object> params = this.doParamsForEntityPosition(entityClass,refFieldName,refFieldValue,refFieldName2,refFieldValue2);
//        if(params == null){
//            return -1;
//        }

//       return this.getSqlSession().update(this.getNamedSqlId(BaseDao.class, "getEntityMaxPosition"), params);
		return this.getEntityMaxPosition(entityClass, refFieldName, refFieldValue, refFieldName2, refFieldValue2);
	}

	@Override
	public int selectEntityMinPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		String tableName = getTableName(entityClass);
		if (tableName == null) {
			return -1;
		}
		//
		String sqlId = this.getNamedSqlId(BasePositionDao.class, "selectEntityMinPosition");
		//
		Map<String, Object> params = this.newParamMap();
		params.put("tableName", tableName);
		if (StrUtil.hasText(refFieldName)) {
			String refColName = this.getColumnName(entityClass, refFieldName);
			if (!StrUtil.hasText(refColName)) {
				return -1;
			}
			params.put("colName", refColName);
			params.put("colValue", refFieldValue);
			//
			if (StrUtil.hasText(refFieldName2)) {
				String refColName2 = this.getColumnName(entityClass, refFieldName2);
				if (!StrUtil.hasText(refColName2)) {
					return -1;
				}
				params.put("colName2", refColName2);
				params.put("colValue2", refFieldValue2);
			}
		}
		return this.getSqlSession().selectOne(sqlId, params);
	}

	// 根据实体类 获取其对应的id列名
	protected String getIdColName(Class<?> entityClass) {
		try {
			Field[] fields = entityClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				if (field.isAnnotationPresent(Id.class)) {
					Id idAnno = field.getAnnotation(Id.class);
					String idName = idAnno.name();
					if (!StrUtil.hasText(idName)) {
						idName = field.getName();
					}
					return idName;
				}
			}
		} catch (Exception e) {
			logger.error("异常", e);
		}
		return null;
	}

	// 根据实体类 和字段名 获取其对应的列名
	protected String getColumnName(Class<?> entityClass, String fieldName) {
		try {
			Field field = entityClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			Column colAnno = field.getAnnotation(Column.class);
			if (colAnno != null) {
				String colName = colAnno.name();
				if (StrUtil.hasText(colName)) {
					//colName = field.getName();
					return colName;
				}
			}
			// Id idAnno = field.getAnnotation(Id.class);
			// if (idAnno != null) {
			// String idName = idAnno.name();
			// if (!StrUtil.hasText(idName)) {
			// idName = field.getName();
			// }
			// return idName;
			// }
		} catch (Exception e) {
			logger.error("异常", e);
		}
		return null;
	}

	private Map<String, Object> doParamsForEntityPosition(Class<?> entityClass, String refFieldName, Object refFieldValue, String refFieldName2, Object refFieldValue2) {
		Map<String, Object> params = this.newParamMap();
		if (StrUtil.hasText(refFieldName)) {
			String refColName = this.getColumnName(entityClass, refFieldName);
			if (!StrUtil.hasText(refColName)) {
				return null;
			}
			params.put("colName", refColName);
			params.put("colValue", refFieldValue);
			//
			if (StrUtil.hasText(refFieldName2)) {
				String refColName2 = this.getColumnName(entityClass, refFieldName2);
				if (!StrUtil.hasText(refColName2)) {
					return null;
				}
				params.put("colName2", refColName2);
				params.put("colValue2", refFieldValue2);
			}
		}
		return params;
	}
}
