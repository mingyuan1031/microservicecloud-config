package com.lwxf.newstore.webapp.domain.dao.base;

import javax.annotation.Resource;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.utils.TypeUtil;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;

@Component("baseNoIdDao")
public abstract class BaseNoIdDaoImpl<T> implements BaseNoIdDao<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<?> sqlTargetInterfaceType = TypeUtil.getInterfaceTypeWithAnnotation(this.getClass(), IBatisSqlTarget.class);

    protected String defaultSqlNameSpace = sqlTargetInterfaceType.getName();
    protected String defaultDataSubject = TypeUtil.getSuperClassGenericType(this.getClass(), 0).getSimpleName();

    @Resource
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
        return this.sqlSession;
    }

    @Override
    public int insert(T entity) {
        return this.getSqlSession().insert(this.getNamedSqlId("insert"), entity);
    }

    @Override
    public int updateByMapContext(MapContext mapContext) {
        return this.getSqlSession().insert(this.getNamedSqlId("updateByMapContext"), mapContext);
    }

    @Override
    public int deleteByMapContext(MapContext mapContext) {
        return this.getSqlSession().insert(this.getNamedSqlId("deleteByMapContext"), mapContext);
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
