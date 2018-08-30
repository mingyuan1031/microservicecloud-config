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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsSpecDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.domain.dto.goods.SpecOptionList;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsSpec;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsSpecService")
public class GoodsSpecServiceImpl extends BaseServiceImpl<GoodsSpec, String, GoodsSpecDao> implements GoodsSpecService {


	@Resource

	@Override	public void setDao( GoodsSpecDao goodsSpecDao) {
		this.dao = goodsSpecDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsSpec> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<GoodsSpec> findAll() {
		return this.dao.findAll();
	}

	@Override
	public List<GoodsSpec> findByTypeId(String id) {
		return this.dao.findByTypeId(id);
	}

	@Override
	public boolean isExistByName(String goodsTypeId, String name) {
		return this.dao.isExistByName(goodsTypeId,name);
	}

	@Override
	public int deleteByTypeId(String typeId) {
		return this.dao.deleteByTypeId(typeId);
	}
}