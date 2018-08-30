package com.lwxf.newstore.webapp.facade.order;

import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/21 16:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface AddressFacade extends BaseFacade {
	RequestResult AddAddress(Address address);
	RequestResult  deleteAddress(String id);
	RequestResult  updateAddress(String id,MapContext context);
	RequestResult findAddressById(String id);
	RequestResult selectByMemberId(String memberId);
	RequestResult getAddressList(Integer pageNum,Integer pageSize,MapContext params);
	RequestResult defaultAddressByMidAndAid(String mid,String aid);
	RequestResult selectDefaultedAddress(String memberId);
	RequestResult  updateAddressStatus(String id,Integer status);
}
