package com.lwxf.newstore.webapp.controller.api.reservation;

import javax.annotation.Resource;

import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.mobile.IMobileMsg;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.ReservationSuccessForClerkMsg;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.ReservationSuccessForUserMsg;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.clerk.ClerkFacade;
import org.springframework.web.bind.annotation.*;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.facade.reservation.ReservationFacade;

import java.util.List;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/30 9:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MemberReservationController {

	@Resource(name = "reservationFacade")
	private ReservationFacade reservationFacade;
	@Resource
	private UserThirdInfoService userThirdInfoService;
	@GetMapping(value = "/users/0/reservations")
	public RequestResult findAllUserId(){
		return this.reservationFacade.findAllUserId();

	}

	@PostMapping(value = "/users/0/reservations")
	public RequestResult postReservation(@RequestBody Reservation reservation){
		RequestResult requestResult = this.reservationFacade.postReservation(reservation);
		// 发给用户
		UserThirdInfo userThirdInfo = AppBeanInjector.userThirdInfoFacade.findByUserId(ShiroUtil.getCurrUserId());
		IMobileMsg mobileMsg = new ReservationSuccessForUserMsg();
		mobileMsg.setTouser(userThirdInfo.getWxOpenId());
		((ReservationSuccessForUserMsg) mobileMsg).setReservationInfo(reservation);
		AppBeanInjector.weixinTemplateMsgService.pushMsg(mobileMsg);

		// 发给店员
		List<UserThirdInfo> userThirdInfos = this.userThirdInfoService.findAllClerks();
		if (userThirdInfos.size()!=0){
			for (UserThirdInfo userThird:userThirdInfos) {
				IMobileMsg toClerkMsg = new ReservationSuccessForClerkMsg();
				toClerkMsg.setTouser(userThird.getWxOpenId());
				((ReservationSuccessForClerkMsg) toClerkMsg).setReservationInfo(reservation);
				AppBeanInjector.weixinTemplateMsgService.pushMsg(toClerkMsg);
			}
		}
		return requestResult;
	}
}
