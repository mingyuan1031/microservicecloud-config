package com.lwxf.newstore.webapp.domain.dao.goods;


import java.util.List;
import java.util.Map;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface SpecOptionDao extends BaseDao<SpecOption, String> {

	PaginatedList<SpecOption> selectByFilter(PaginatedFilter paginatedFilter);

	List<SpecOption> findAll();

	List<SpecOption> findBySpecId(String id);

	boolean isExistByName(String goodsSpecId, String name);

	int deleteBySpecId(String id);

	int findByIds(String[] ids);

	int findSpecByOptionids(String[] ids);

	int findTypeByOptions(String[] ids);
}