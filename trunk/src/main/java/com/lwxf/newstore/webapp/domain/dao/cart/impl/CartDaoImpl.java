package com.lwxf.newstore.webapp.domain.dao.cart.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.cart.CartDao;
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
@Repository("cartDao")
public class CartDaoImpl extends BaseDaoImpl<Cart, String> implements CartDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Cart> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Cart> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@Override
	public int deleteByIds(String... ids) {
		String sqlId = this.getNamedSqlId("deleteByIds");
		return this.getSqlSession().delete(sqlId, ids);
	}

	/**
	 * 根据ID修改商品数量
	 * @param id
	 * @param mapContext
	 */
	@Override
	public int updateById(String id, MapContext mapContext) {
		String sqlId = this.getNamedSqlId("updateById");
		Map<String,Object> data = new HashMap<>();
		data.put("id", id);
		data.put("count", mapContext.get("count"));
		return this.getSqlSession().update(sqlId,data);
	}

	/**
	 * 显示登陆用户的购物车商品
	 * @param memberId
	 * @return
	 */
	@Override
	public List<CartDto> selectByMemberId(String memberId,String... ids) {
		String sqlId = this.getNamedSqlId("selectByMemberId");
		MapContext data = MapContext.newOne();
		data.put("memberId", memberId);
		if (null != ids && ids.length == 0) {
			ids = null;
		}
		data.put("ids", ids);
		

		return this.getSqlSession().selectList(sqlId,data);
	}

	@Override
	public int updateCountById(String id, Integer count) {
		String sqlId = this.getNamedSqlId("updateCountById");
		Map<String, Object> data = new HashMap<>();
		data.put("id", id);
		data.put("count", count);
		return this.getSqlSession().update(sqlId, data);
	}

	@Override
	public List<Cart> findMemberId(String memberId) {
		String sqlId = this.getNamedSqlId("findMemberId");
		return this.getSqlSession().selectList(sqlId, memberId);
	}

	@Override
	public List<CartPriceDto> findByIds(MapContext params) {
		String sqlId = this.getNamedSqlId("findByIds");
		return this.getSqlSession().selectList(sqlId, params);
	}

	@Override
	public Integer findCountByGoodsExtends(String goodsExtendsId,String memberId) {
		String sqlId = this.getNamedSqlId("findCountByGoodsExtends");
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_MEMBER_ID,memberId);
		mapContext.put("goodsExtendId",goodsExtendsId);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}
}