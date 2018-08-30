package com.lwxf.newstore.webapp.facade.cart;

import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.dto.cart.CartPriceDto;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/25 10:06
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface CartFacade extends BaseFacade {

	/**
	 * 删除购物车商品
	 * @param ids
	 * @return
	 */
	RequestResult deleteByIds(String... ids);

	/**
	 * 根据ID修改购物车商品的数量
	 * @param id
	 * @param mapContext
	 * @return
	 */
	RequestResult updateCountById(String id,MapContext mapContext);

	/**
	 * 查看购物车中所有的商品
	 * @return
	 */
	RequestResult findRequestData(String[] ids);

	/**
	 * 向购物车中添加商品
	 * @param cart
	 * @return
	 */
	RequestResult insertCart(Cart cart);

	RequestResult findByIds(List<String> ids);

}
