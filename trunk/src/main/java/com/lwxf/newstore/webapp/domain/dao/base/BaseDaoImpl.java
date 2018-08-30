package com.lwxf.newstore.webapp.domain.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.utils.TypeUtil;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

public abstract class BaseDaoImpl<T, K extends Serializable> implements BaseDao<T, K> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<?> sqlTargetInterfaceType = TypeUtil.getInterfaceTypeWithAnnotation(this.getClass(), IBatisSqlTarget.class);

    protected String defaultSqlNameSpace = sqlTargetInterfaceType.getName();
    protected String defaultDataSubject = TypeUtil.getSuperClassGenericType(this.getClass(), 0).getSimpleName();

    @Resource
    @SuppressWarnings("SpringJavaAutowiringInspection")
    protected SqlSession sqlSession;

    protected String getDefaultSqlNameSpace() {
        return this.defaultSqlNameSpace;
    }

    protected String getDefaultDataSubject() {
        return this.defaultDataSubject;
    }

    protected String getNamedSqlId(Class<?> sqlNameSpaceClass, String sqlId) {
        return sqlNameSpaceClass.getName() + "." + sqlId;
    }

    protected String getNamedSqlId(String sqlNameSpace, String sqlId) {
        return sqlNameSpace + "." + sqlId;
    }

    protected String getNamedSqlId(String sqlId) {
        return this.getDefaultSqlNameSpace() + "." + sqlId;
    }

    protected SqlSession getSqlSession() {
        // return SqlSessionUtils.getSqlSession(sqlSessionFactory);
        return this.sqlSession;
    }

    @Override
    public int insert(T entity) {
        //
        String sqlId = this.getNamedSqlId("insert");
        //
        return this.getSqlSession().insert(sqlId, entity);
    }


    @Override
    public int deleteById(K id) {
        String sqlId = this.getNamedSqlId("deleteById");
        //
        return this.getSqlSession().delete(sqlId, id);
    }

    @Override
    public int updateByMapContext(MapContext mapContext) {
        //
        String sqlId = this.getNamedSqlId("updateByMapContext");
        //
        return this.getSqlSession().update(sqlId, mapContext);
    }

    @Override
    public T selectById(K id) {
        String sqlId = this.getNamedSqlId("selectById");
        //
        return this.getSqlSession().selectOne(sqlId, id);
    }

    @Override
    public Boolean isExist(Class<T> entityClass, K id) {
        String tableName = getTableName(entityClass);
        if (tableName == null) {
            return Boolean.FALSE;
        }
        Map<String, Object> map = this.newParamMap();
        map.put("id", id);
        map.put("tableName", tableName);
        String sqlId = this.getNamedSqlId(BaseDao.class, "isExist");
        return this.getSqlSession().selectOne(sqlId, map);
    }

    // Dao , Service 层输入的数据和返回的数据必须是通用格式
    // 通用分页信息 => Mybatis所需的分页信息
    protected PageBounds toPageBounds(Pagination pagination, List<Map<String, String>> sortItems) {
        if (sortItems == null || sortItems.isEmpty()) {
            return new PageBounds(pagination.getPageNum(), pagination.getPageSize());
        } else {
            List<Order> orderItems = new ArrayList<Order>();
            Map.Entry<String, String> sortEntry;
            for (Map<String, String> sortItem : sortItems) {
                if (null == sortItem) {
                    continue;
                }
                sortEntry = sortItem.entrySet().iterator().next();
                String colName = sortEntry.getKey();
                String order = sortEntry.getValue();
                Order orderItem = Order.create(colName, order);
                orderItems.add(orderItem);
            }
            return new PageBounds(pagination.getPageNum(), pagination.getPageSize(), orderItems);
        }

    }

    protected PageBounds toPageBounds(Pagination pagination) {
        return new PageBounds(pagination.getPageNum(), pagination.getPageSize());
    }

    // Mybatis分页信息 => 通用分页信息
    protected <E> PaginatedList<E> toPaginatedList(PageList<E> pageList) {
        PaginatedList<E> paginatedList = PaginatedList.newOne();
        Pagination pagination = paginatedList.getPagination();
        //
        Paginator paginator = pageList.getPaginator();
        pagination.setTotalCount(paginator.getTotalCount());
        pagination.setPageSize(paginator.getLimit());
        pagination.setPageNum(paginator.getPage());

        paginatedList.setRows(pageList);
        //
        return paginatedList;
    }

    // 创建Map<String, Object>的便捷方法
    protected Map<String, Object> newParamMap() {
        return MapContext.newOne();
    }

    // 根据实体类获取其对应的表名
    protected String getTableName(Class<?> entityClass) {
        Table tableAnno = entityClass.getAnnotation(Table.class);
        return tableAnno == null ? null : tableAnno.name();
    }

}
