package com.lwxf.newstore.webapp.facade.cart.impl;

import javax.annotation.Resource;
import javax.naming.Name;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.cart.CartService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dto.cart.CartDto;
import com.lwxf.newstore.webapp.domain.dto.cart.CartPriceDto;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.cart.CartFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/25 10:07
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("cartFacade")
public class CartFacadeImpl extends BaseFacadeImpl implements CartFacade {

	@Resource(name = "cartService")
	private CartService cartService;
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Override
	@Transactional
	public RequestResult deleteByIds(String... ids) {
		if (null != ids && ids.length != 0) {
			if (this.cartService.findById(ids[ids.length-1]).getMemberId().equals(WebUtils.getCurrUserId())) {
				this.cartService.deleteByIds(ids);
				return ResultFactory.generateRequestResult(this.cartService.selectByMemberId(WebUtils.getCurrUserId()));
			}
		}
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult updateCountById(String id, MapContext mapContext) {
		if (this.cartService.findById(id).getMemberId().equals(WebUtils.getCurrUserId())) {
			this.cartService.updateById(id, mapContext);
		}
		return ResultFactory.generateRequestResult(this.cartService.findById(id));
	}

	@Override
	public RequestResult findRequestData(String... ids) {
		return ResultFactory.generateRequestResult(this.cartService.selectByMemberId(WebUtils.getCurrUserId(),ids));
	}

	@Override
	@Transactional
	public RequestResult insertCart(Cart cart) {
		List<Cart> findCart = this.cartService.findMemberId(WebUtils.getCurrUserId());
		//商品下架或者商品不存在返回资源未找到
		Goods goods =this.goodsService.findById(cart.getGoodsId());
		if(goods==null||goods.getDisabled()){
			return ResultFactory.generateResNotFoundResult();
		}
		GoodsExtend goodsExtend= this.goodsExtendService.findById(cart.getExtendId());
		//报价不存在或者报价并非该商品下的报价 返回找不到资源
		if(goodsExtend==null||!goodsExtend.getGoodsId().equals(cart.getGoodsId())){
			return ResultFactory.generateResNotFoundResult();
		}
		Integer cartCount= this.cartService.findCountByGoodsExtends(cart.getExtendId(),WebUtils.getCurrUserId());
		if(cartCount==null){
			cartCount = 0;
		}
		//如果库存小于加入购物车的数量就返回库存不足异常
		if(goodsExtend.getStock()<(cart.getCount()+cartCount)){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_LACK_OF_STOCK_10063,LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_LACK_OF_STOCK_10063"),goodsExtend.getStock()));
		}
		// 查找这个用户的购物车的所有商品
		for (Cart c : findCart){
			// 将前台传回来的商品ID和查找到的商品ID进行判单，查看商品是否存在
			if(c.getGoodsId().equals(cart.getGoodsId())){
				//若商品存在，则判断规格是否一致，若是一致就在原数量上加新数量
				if(c.getExtendId().equals(cart.getExtendId())){
					this.cartService.updateCountById(c.getId(), c.getCount()+cart.getCount());
					return ResultFactory.generateRequestResult(this.cartService.selectByMemberId(WebUtils.getCurrUserId()));
				}
			}
		}
		this.cartService.add(cart);
		return ResultFactory.generateRequestResult(this.cartService.selectByMemberId(WebUtils.getCurrUserId()));
	}

	@Override
	public RequestResult findByIds(List<String> ids) {

		return ResultFactory.generateRequestResult(this.cartService.findByIds( ids));

	}

}
