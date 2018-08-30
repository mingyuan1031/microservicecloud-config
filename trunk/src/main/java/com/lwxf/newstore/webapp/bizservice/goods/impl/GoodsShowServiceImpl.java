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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsShowDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsShowService;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsShow;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-22 15:30:32
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsShowService")
public class GoodsShowServiceImpl extends BaseServiceImpl<GoodsShow, String, GoodsShowDao> implements GoodsShowService {


	@Resource

	@Override	public void setDao( GoodsShowDao goodsShowDao) {
		this.dao = goodsShowDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsShow> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<GoodsShow> findByGoodsId(String id) {
		return this.dao.findByGoodsId(id);
	}

	@Override
	public int removeDefaults(String goodsId) {
		return this.dao.removeDefaults(goodsId);
	}

	@Override
	public int setDefault(String id) {
		return this.dao.setDefault(id);
	}

}