package com.lwxf.newstore.webapp.bizservice.cart;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
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
public interface CartService extends BaseService <Cart, String> {

	PaginatedList<Cart> selectByFilter(PaginatedFilter paginatedFilter);

	int deleteByIds(String... ids);

	int insert(Cart cart);

	int updateById(String id,MapContext mapContext);

	List<CartDto> selectByMemberId(String memberId,String... ids);

	int updateCountById(String id,Integer count);

	List<Cart> findMemberId(String memberId);

	List<CartPriceDto> findByIds(List<String> ids);

	Integer findCountByGoodsExtends(String goodsExtendsId,String memberId);
}