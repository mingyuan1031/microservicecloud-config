package com.lwxf.newstore.webapp.controller.api.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.order.AddressFacade;
import com.lwxf.newstore.webapp.facade.order.OrderFacade;
import com.lwxf.newstore.webapp.facade.user.UserFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/27 15:14
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/users", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MyController {
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	@Resource(name = "addressFacade")
	private AddressFacade addressFacade;

	/**
	 * 查询会员信息
	 * @return
	 */
	@GetMapping("/0")
	public RequestResult getUser(){
		String id = WebUtils.getCurrUserId();
		User user = this.userFacade.findUserById(id);
		return  ResultFactory.generateRequestResult(user);
	}

	@GetMapping("/0/paidrecords")
	public  RequestResult getPaidrecords() {
		String id = WebUtils.getCurrUserId();
		return this.orderFacade.getPaidrecords(id);
	}

	/**
	 * 查询会员得所有订单
	 * @return
	 */
	@GetMapping("/0/orders")
	public RequestResult getOrderByMemberId()
	{
		String mid=WebUtils.getCurrUserId();
 		return  this.orderFacade.findOrderListByMemberId(mid);
	}

	/**
	 * 查询会员得所有地址
	 * @return
	 */
	@GetMapping("/0/address")
	public RequestResult getAddressByMemberId()
	{
		String mid=WebUtils.getCurrUserId();
		return  this.addressFacade.selectByMemberId(mid);
	}

	/**
	 * 修改用户得默认地址
	 * @param aid
	 * @return
	 */
	@PutMapping("/0/address/{aid}/defaulted")
	public  RequestResult updateAddressDefaulted(@PathVariable("aid") String aid)
	{

		String currUserId = WebUtils.getCurrUserId();
		return this.addressFacade.defaultAddressByMidAndAid(currUserId,aid);
	}

	/**
	 * 查询用户的默认地址
	 * @return
	 */
	@GetMapping("/0/address/defaulted")
	public RequestResult selectDefaultedAddress(){
		String currUserId = WebUtils.getCurrUserId();
		return this.addressFacade.selectDefaultedAddress(currUserId);
	}
	@GetMapping("/0/orderGoods/{ogid}/details")
	public  RequestResult findOrderDetailsByOrderId(@PathVariable("ogid") String ogid)
	{
      return  this.orderFacade.findOrderDetailsByOrderId(ogid);
	}
}
