package com.lwxf.newstore.webapp.bizservice.goods;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SpecOptionService extends BaseService <SpecOption, String> {

	PaginatedList<SpecOption> selectByFilter(PaginatedFilter paginatedFilter);

	List<SpecOption> findAll();

	List<SpecOption> findBySpecId(String id);

	boolean isExistByName(String goodsSpecId,String name);

	int deleteBySpecId(String id);

	int findByIds(String[] ids);

	int findSpecByOptionids(String[] ids);

	int findTypeByOptions(String[] ids);
}