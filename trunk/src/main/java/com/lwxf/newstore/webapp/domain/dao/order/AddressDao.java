package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.order.Address;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-06-20 20:23:04
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface AddressDao extends BaseDao<Address, String> {

	PaginatedList<Address> selectByFilter(PaginatedFilter paginatedFilter);
	List<Address> selectByMemberId(String memberId);
	Address selectByIsDefaulted(String memberId);

}