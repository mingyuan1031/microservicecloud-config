package com.lwxf.newstore.webapp.domain.dao.order.impl;


import java.util.List;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.order.AddressDao;
import com.lwxf.newstore.webapp.domain.entity.order.Address;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("addressDao")
public class AddressDaoImpl extends BaseDaoImpl<Address, String> implements AddressDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Address> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Address> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<Address> selectByMemberId(String memberId) {

		String sqlId = this.getNamedSqlId("selectByMemberId");
		return this.getSqlSession().selectList(sqlId,memberId);
	}

	@Override
	public Address selectByIsDefaulted(String memberId) {
		String sqlId = this.getNamedSqlId("selectByIsDefaulted");
		return this.sqlSession.selectOne(sqlId,memberId);
	}
}