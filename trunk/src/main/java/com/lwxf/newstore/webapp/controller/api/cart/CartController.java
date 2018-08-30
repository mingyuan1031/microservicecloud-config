package com.lwxf.newstore.webapp.controller.api.cart;



import javax.annotation.Resource;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import org.apache.ibatis.annotations.Delete;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;
import com.lwxf.newstore.webapp.facade.cart.CartFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/15 16:22
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class CartController {

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	/**
	 * 根据ID获得用户购物车内容
	 * @return
	 */
	@GetMapping(value = "/users/0/carts")
	public RequestResult findCart(@RequestParam(required = false) String... id){
		return this.cartFacade.findRequestData(id);
	}

	/**
	 * 根据ID删除购物车的商品
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/users/0/carts")
	public RequestResult deleteCart(@RequestParam(required = false) String... id){
		return this.cartFacade.deleteByIds(id);
	}

	/**
	 * 跟据ID修改购物车中商品的数量
	 * @param id
	 * @param mapContext
	 * @return
	 */
	@PutMapping(value = "/users/0/carts/{id}")
	public RequestResult putCount(@PathVariable String id, @RequestBody MapContext mapContext){
		return this.cartFacade.updateCountById(id, mapContext);
	}

	/**
	 * 添加商品到用户的购物车
	 * @param cart
	 * @return
	 */
	@PostMapping(value = "/users/0/carts")
	public RequestResult insertCart(@RequestBody Cart cart){
		cart.setMemberId(WebUtils.getCurrUserId());
		cart.setCreated(DateUtil.getSystemDate());
		RequestResult result= cart.validateFields();
		if(result!=null){
			return result;
		}
		return this.cartFacade.insertCart(cart);
	}

//	@GetMapping(value = "/users/0/carts/toOrder")
//	public RequestResult toOrder(@RequestParam String... id){
//		return this.cartFacade.FindByIds(id);
//	}
//	@GetMapping(value = "/users/0/carts/toOrder")
//	public RequestResult toOrder(@RequestParam List<String> ids){
//		return this.cartFacade.FindByIds(ids) ;
//	}


}
