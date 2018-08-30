package com.lwxf.newstore.webapp.bizservice.goods.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.goods.SpecOptionDao;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("specOptionService")
public class SpecOptionServiceImpl extends BaseServiceImpl<SpecOption, String, SpecOptionDao> implements SpecOptionService {


	@Resource

	@Override	public void setDao( SpecOptionDao specOptionDao) {
		this.dao = specOptionDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<SpecOption> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<SpecOption> findAll() {
		return this.dao.findAll();
	}

	@Override
	public List<SpecOption> findBySpecId(String id) {
		return this.dao.findBySpecId(id);
	}

	@Override
	public boolean isExistByName(String goodsSpecId, String name) {
		return this.dao.isExistByName(goodsSpecId,name);
	}

	@Override
	public int deleteBySpecId(String id) {
		return this.dao.deleteBySpecId(id);
	}

	@Override
	public int findByIds(String[] ids) {
		return this.dao.findByIds(ids);
	}

	@Override
	public int findSpecByOptionids(String[] ids) {
		return this.dao.findSpecByOptionids(ids);
	}

	@Override
	public int findTypeByOptions(String[] ids) {
		return this.dao.findTypeByOptions(ids);
	}
}