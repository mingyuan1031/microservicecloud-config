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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsTypeDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:49
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl extends BaseServiceImpl<GoodsType, String, GoodsTypeDao> implements GoodsTypeService {


	@Resource

	@Override	public void setDao( GoodsTypeDao goodsTypeDao) {
		this.dao = goodsTypeDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsType> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<GoodsType> findall() {
		return this.dao.findAll();
	}

	@Override
	public List<GoodsType> findByParentId(String id) {
		return this.dao.findByParentId(id);
	}

	@Override
	public GoodsType findByNameAndParentId(String name,String parentId) {
		return this.dao.findByNameAndParentId(name,parentId);
	}

	@Override
	public GoodsType findByName(String name) {
		return this.dao.findByName(name);
	}

}