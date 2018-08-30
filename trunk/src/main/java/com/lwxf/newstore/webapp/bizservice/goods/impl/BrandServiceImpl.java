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
import com.lwxf.newstore.webapp.domain.dao.goods.BrandDao;
import com.lwxf.newstore.webapp.bizservice.goods.BrandService;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("brandService")
public class BrandServiceImpl extends BaseServiceImpl<Brand, String, BrandDao> implements BrandService {


	@Resource

	@Override	public void setDao( BrandDao brandDao) {
		this.dao = brandDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Brand> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}
	@Override
	public List<Brand> findAll() {
		return this.dao.findAll();
	}


	@Override
	public Brand findByBrandName(String name) {
		return this.dao.findByBrandName(name);
	}

	@Override
	public List<Brand> findListByParams(MapContext mapContext) {
		return this.dao.findListByParams(mapContext);
	}

	@Override
	public List<Brand> findLikeName(String name) {
		return this.dao.findLikeName(name);
	}

}