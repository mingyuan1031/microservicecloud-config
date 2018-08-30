package com.lwxf.newstore.webapp.facade.order.impl;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.order.AddressStatus;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.order.AddressFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/21 16:28
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("addressFacade")
public class AddressFacadeImpl extends BaseFacadeImpl implements AddressFacade {

	@Resource
	private AddressService addressService;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult AddAddress(Address address) {
		//判断是否存在默认地址 不存在就设置此条为默认地址
		if(this.addressService.selectByIsDefaulted(WebUtils.getCurrUserId())==null){
			address.setDefaulted(Boolean.TRUE);
		}
		this.addressService.add(address);
		return ResultFactory.generateRequestResult(address);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult deleteAddress(String id) {
		Address address = this.addressService.findById(id);
		if (address == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		int userRole = WebUtils.getCurrUser().getRole().intValue();
		if (userRole == UserRole.MEMBER.getValue()) {
			if (!address.getMemberId().equals(WebUtils.getCurrUserId())) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
			}
		}
		if (address.getStatus().intValue() != AddressStatus.NORMAL.getValue()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003, AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		mapContext.put("status",-1);
		mapContext.put(WebConstant.KEY_ENTITY_DEFAULTED,false);
		this.addressService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateAddress(String id, MapContext mapContext) {
//		return this.addressService.updateByMapContext(context);
		Address address = this.addressService.findById(id);
		if (address == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		int userRole = WebUtils.getCurrUser().getRole().intValue();
		if (userRole == UserRole.MEMBER.getValue()) {
			if (!address.getMemberId().equals(WebUtils.getCurrUserId())) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
			}
		}
		if (address.getStatus().intValue() != AddressStatus.NORMAL.getValue()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003, AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID, id);
		this.addressService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findAddressById(String id) {
		return ResultFactory.generateRequestResult(this.addressService.findById(id));
	}

	@Override
	public RequestResult selectByMemberId(String memberId) {

		List<Address> list = this.addressService.selectByMemberId(memberId);
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	public RequestResult getAddressList(Integer pageNum, Integer pageSize, MapContext params) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(params);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		PaginatedList<Address> list = this.addressService.selectByFilter(filter);
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult defaultAddressByMidAndAid(String mid, String aid) {
//		List<Address> list = this.addressService.selectByMemberId(mid);
//		for (Address address : list) {
//			MapContext mapContext=new MapContext();
//			mapContext.put(WebConstant.KEY_ENTITY_ID,address.getId());
//			mapContext.put(WebConstant.KEY_ENTITY_DEFAULTED,false);
//			this.addressService.updateByMapContext(mapContext);
//		}
		Address oldAddress = this.addressService.selectByIsDefaulted(mid);
		Address newAddress = this.addressService.findById(aid);
		if (newAddress==null) {
			return ResultFactory.generateResNotFoundResult();
		}
		//判断新的默认地址是不是当前用户的
		if(!newAddress.getMemberId().equals(WebUtils.getCurrUserId())){
			return ResultFactory.generateResNotFoundResult();
		}
		//如果存在旧的默认地址
		if(oldAddress!=null){
			//判断旧的地址是不是当前用户的地址
			if (!oldAddress.getMemberId().equals(WebUtils.getCurrUserId())) {
				return ResultFactory.generateResNotFoundResult();
			}
			MapContext oldUpdate = MapContext.newOne();
			oldUpdate.put(WebConstant.KEY_ENTITY_ID, oldAddress.getId());
			oldUpdate.put(WebConstant.KEY_ENTITY_DEFAULTED, 0);
			this.addressService.updateByMapContext(oldUpdate);
		}
		MapContext newUpdate = new MapContext();
		newUpdate.put(WebConstant.KEY_ENTITY_ID, newAddress.getId());
		newUpdate.put(WebConstant.KEY_ENTITY_DEFAULTED, 1);
		this.addressService.updateByMapContext(newUpdate);
		return ResultFactory.generateRequestResult(newUpdate);
	}

	@Override
	public RequestResult selectDefaultedAddress(String memberId) {
		Address address = this.addressService.selectByIsDefaulted(memberId);
		return ResultFactory.generateRequestResult(address);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateAddressStatus(String id, Integer status) {
		Address address = this.addressService.findById(id);
		if (address == null) {
			return ResultFactory.generateResNotFoundResult();
		}
		int userRole = WebUtils.getCurrUser().getRole().intValue();
		if (userRole == UserRole.MEMBER.getValue()) {
			if (!address.getMemberId().equals(WebUtils.getCurrUserId())) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
			}
		}
		if (address.getStatus().intValue() != AddressStatus.NORMAL.getValue()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003, AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		MapContext update = MapContext.newOne();
		update.put(WebConstant.KEY_ENTITY_ID, id);
		update.put(WebConstant.KEY_ENTITY_STATUS, status);
		this.addressService.updateByMapContext(update);
		return ResultFactory.generateRequestResult(update);
	}
}
