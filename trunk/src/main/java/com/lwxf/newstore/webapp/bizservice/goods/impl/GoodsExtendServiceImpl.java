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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsExtendDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsExtendService")
public class GoodsExtendServiceImpl extends BaseServiceImpl<GoodsExtend, String, GoodsExtendDao> implements GoodsExtendService {


	@Resource

	@Override	public void setDao( GoodsExtendDao goodsExtendDao) {
		this.dao = goodsExtendDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsExtend> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public boolean isExistByLikeOptions(String id) {
		return this.dao.isExistByLikeOptions(id);
	}

	@Override
	public List<GoodsExtend> findByGoodsId(String goodsId) {
		return this.dao.findByGoodsId(goodsId);
	}

	@Override
	public int removeDefaults(String goodsId) {
		return this.dao.removeDefaults(goodsId);
	}

	@Override
	public boolean isExistByDefaults(String goodsId) {
		return this.dao.isExistByDefaults(goodsId);
	}

	@Override
	public boolean isExistByGoodsIdAndExtendId(String goodsId, String extendId) {
		return this.dao.isExistByGoodsIdAndExtendId(goodsId,extendId);
	}


}