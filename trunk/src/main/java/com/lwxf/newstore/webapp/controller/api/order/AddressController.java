package com.lwxf.newstore.webapp.controller.api.order;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.facade.order.AddressFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/21 16:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/addresses", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class AddressController {
	@Resource(name = "addressFacade")
	private AddressFacade addressFacade;

	@PutMapping(value = "/{id}")
	public RequestResult updateAddress(@PathVariable("id") String id , @RequestBody MapContext update)
	{

		RequestResult validResult = Address.validateFields(update);
		if(null != validResult){
			return validResult;
		}
		update.put("updated",DateUtil.getSystemDate());
		return  this.addressFacade.updateAddress(id,update);
	}
	@PutMapping(value = "/{id}/status/{status}")
	public RequestResult updateAddressStatus(@PathVariable("id") String id ,@PathVariable("status") Integer status )
	{
		return  this.addressFacade.updateAddressStatus(id,status);
	}
	@DeleteMapping(value = "/{id}")
	public  RequestResult deleteAddress(@PathVariable("id") String id)
	{
			return this.addressFacade.deleteAddress(id);
	}
	@PostMapping
	public RequestResult addAddress(@RequestBody Address address)
	{
		address.setDefaulted(Boolean.FALSE);
		address.setMemberId(WebUtils.getCurrUserId());
		address.setCreated(DateUtil.getSystemDate());
		address.setStatus(0);
		RequestResult validResult = address.validateFields();
		if(null!=validResult)
		{
			return  validResult;
		}
		return this.addressFacade.AddAddress(address);
	}

	@GetMapping(value = "/{id}")
	public RequestResult getAddressById(@PathVariable("id")  String id){
		return this.addressFacade.findAddressById(id);
	}

	@GetMapping
	public RequestResult getAddressList(@RequestParam(required = false) String memberId,@RequestParam(required = false) Boolean defaulted,@RequestParam(required = false,defaultValue = "1") Integer pageNum,
										@RequestParam(required = false,defaultValue = "50") Integer pageSize){



			return this.addressFacade.getAddressList(pageNum,pageSize,this.createParamsForFindList(memberId,defaulted,0));


	}
	private MapContext createParamsForFindList(String memberId,Boolean defaulted,Integer status){
		MapContext params = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(memberId)){
			params.put(WebConstant.KEY_ENTITY_MEMBER_ID,memberId);
		}
		if(defaulted != null){
			params.put(WebConstant.KEY_ENTITY_DEFAULTED,defaulted);
		}
		if (status!=null)
		{
			params.put(WebConstant.KEY_ENTITY_STATUS,status);
		}

		return params;
	}
}
