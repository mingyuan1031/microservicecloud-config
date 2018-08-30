package com.lwxf.newstore.webapp.bizservice.cart.impl;


import java.util.List;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dao.cart.CartDao;
import com.lwxf.newstore.webapp.bizservice.cart.CartService;
import com.lwxf.newstore.webapp.domain.dto.cart.CartDto;
import com.lwxf.newstore.webapp.domain.dto.cart.CartPriceDto;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-14 11:35:08
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("cartService")
public class CartServiceImpl extends BaseServiceImpl<Cart, String, CartDao> implements CartService {

	@Resource(name = "cartDao")
	@Override
	public void setDao( CartDao cartDao) {
		this.dao = cartDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Cart> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public int deleteByIds(String... ids) {
		return this.dao.deleteByIds(ids);
	}

	@Override
	public int updateByMapContext(MapContext mapContext){
		return this.dao.updateByMapContext(mapContext);
	}


	@Override
	public int insert(Cart cart) {
		return this.dao.insert(cart);
	}

	@Override
	public List<CartDto> selectByMemberId(String memberId,String... ids) {
		return this.dao.selectByMemberId(memberId,ids);
	}

	@Override
	public int updateById(String id,MapContext mapContext){
		return this.dao.updateById(id, mapContext);
	}

	@Override
	public int updateCountById(String id, Integer count) {
		return this.dao.updateCountById(id, count);
	}

	@Override
	public List<Cart> findMemberId(String memberId) {
		return this.dao.findMemberId(memberId);
	}

	@Override
	public List<CartPriceDto> findByIds(List<String> ids) {
		MapContext params = MapContext.newOne();
		params.put("ids",ids);
		String memberId = WebUtils.getCurrUserId();
		params.put("memberId",memberId);
		return this.dao.findByIds(params);
	}


	@Override
	public Integer findCountByGoodsExtends(String goodsExtendsId,String memberId) {
		return this.dao.findCountByGoodsExtends(goodsExtendsId,memberId);
	}
}