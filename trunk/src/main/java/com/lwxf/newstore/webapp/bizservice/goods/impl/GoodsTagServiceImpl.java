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
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsTagDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTagService;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsTagsInfo;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsTag;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsTagService")
public class GoodsTagServiceImpl extends BaseServiceImpl<GoodsTag, String, GoodsTagDao> implements GoodsTagService {


	@Resource

	@Override	public void setDao( GoodsTagDao goodsTagDao) {
		this.dao = goodsTagDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsTag> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<GoodsTag> findAll() {
		return this.dao.findAll();
	}

	@Override
	public boolean isExistByTagId(String tagId) {
		return this.dao.isExistByTagId(tagId);
	}

	@Override
	public int deleteByGoodsIdAndTagId(String goodsId, String tagId) {
		return this.dao.deleteByGoodsIdAndTagId(goodsId,tagId);
	}

	@Override
	public List<GoodsTagsInfo> findByGoodsId(String goodsId) {
		return this.dao.findByGoodsId(goodsId);
	}

	@Override
	public boolean isExistByGoodsIdAndTagId(String goodsId, String tagId) {
		return this.dao.isExistByGoodsIdAndTagId(goodsId,tagId);
	}

	@Override
	public int deleteByGoodsId(String goodsId) {
		return this.dao.deleteByGoodsId(goodsId);
	}
}