package com.lwxf.newstore.webapp.controller.path;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.commons.collections.map.HashedMap;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.wxpayutil.StringUtil;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.order.WeiXinPayFacade;
import com.lwxf.newstore.webapp.facade.reservation.ReservationFacade;

/**
 * 功能：需要用户登录后才能访问的路径
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-13 9:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Controller
public class UserLoginedController extends LoadBaseCfgAndSysCfgController {
	private Logger logger = LoggerFactory.getLogger(PortalsController.class);
	private static final String PAGE_STORECARD = "storecard";
	private static final String PAGE_QUICKSHARE = "quickshare";
	private static final String PAGE_RESERVATION = "reservation";
	@Resource(name = "weiXinPayFacade")
	private WeiXinPayFacade weiXinPayFacade;
	@Resource(name = "reservationFacade")
	private ReservationFacade reservationFacade;

	@Override
	protected Map<String, Object> loadUserPreload(HttpServletRequest request) {
		Map<String, Object> userPrelaod = new HashedMap();
		User currUser = ShiroUtil.getCurrUser();
		if(null != currUser){
			userPrelaod.put("user",currUser);
		}
		String reqPath = request.getServletPath();
		if(reqPath.indexOf("/reservation")>-1){
			userPrelaod.put("reservations",this.reservationFacade.findAllAmount());
		}
		return userPrelaod;
	}

	/**
	 * 店铺名片
	 * @return
	 */
	@GetMapping("/storecard")
	public String goStoreCardHome() {
		noCahce();
		return WebUtils.getPortalsPagePath(PAGE_STORECARD);
	}

	/**
	 * 跟路径
	 * @return
	 */
	@GetMapping("/")
	public String goBasePath() {
		noCahce();
		if(WebUtils.getUserAgent().isPc()){
			return LwxfStringUtils.format(WebConstant.REDIRECT_PATH_TEMPLATE,WebConstant.REDIRECT_PATH_ADMIN);
		}
		return LwxfStringUtils.format(WebConstant.REDIRECT_PATH_TEMPLATE,WebConstant.REDIRECT_PATH_MALL);
	}

	/**
	 * 快享
	 * @return
	 */
	@GetMapping("/quickshare")
	public String goQuickShareHome() {
		noCahce();
		return WebUtils.getPortalsPagePath(PAGE_QUICKSHARE);
	}

	/**
	 * 预约
	 * @return
	 */
	@GetMapping("/reservation")
	public String goReservationHome() {
		noCahce();
		return WebUtils.getPortalsPagePath(PAGE_RESERVATION);
	}

	/**
	 * 公众号支付
	 * @param model
	 * @return
	 */
	@GetMapping("/payment/mppay")
	public String goPaymentHome(ModelMap model) {
		noCahce();
		RequestResult requestResult = weiXinPayFacade.mpPay("43lby6vuwydc");
		model.addAttribute("payData",requestResult.getData());
		logger.error(this.json.toJson(requestResult.getData()));
		return "payment/payment";
	}
}
