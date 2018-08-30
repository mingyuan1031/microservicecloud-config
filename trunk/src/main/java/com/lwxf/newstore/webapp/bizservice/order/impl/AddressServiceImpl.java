package com.lwxf.newstore.webapp.bizservice.order.impl;


import java.util.List;


import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.order.AddressDao;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.domain.entity.order.Address;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("addressService")
public class AddressServiceImpl extends BaseServiceImpl<Address, String, AddressDao> implements AddressService {


	@Resource

	@Override	public void setDao( AddressDao addressDao) {
		this.dao = addressDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Address> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<Address> selectByMemberId(String memberId) {
		return this.dao.selectByMemberId(memberId);
	}

	@Override
	public Address selectByIsDefaulted(String memberId) {
		return this.dao.selectByIsDefaulted(memberId);
	}
}