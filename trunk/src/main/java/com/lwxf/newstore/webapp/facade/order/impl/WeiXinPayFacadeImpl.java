package com.lwxf.newstore.webapp.facade.order.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.util.SignUtils;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderService;
import com.lwxf.newstore.webapp.bizservice.order.PaidRecordsService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.order.OrderStatus;
import com.lwxf.newstore.webapp.common.enums.order.PaidRecordsType;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.AddOrderSuccessForClerkMsg;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.wxpayutil.*;
import com.lwxf.newstore.webapp.config.WeixinPayConfig;
import com.lwxf.newstore.webapp.config.wxpay.WxPayProperties;
import com.lwxf.newstore.webapp.domain.dto.order.GoodsDto;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.domain.entity.order.PaidRecords;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.order.WeiXinPayFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/23 9:51
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("weiXinPayFacade")
public class WeiXinPayFacadeImpl extends BaseFacadeImpl implements WeiXinPayFacade {
	private static final String UNIFIED_ORDER_BODY_TEMPLATE = "{0}-商品订单支付";
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Resource
	private OrderService orderService;
	@Resource
	private PaidRecordsService paidRecordsService;
	@Autowired
	private WxPayProperties properties;
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Resource(name = "orderGoodsService")
	private OrderGoodsService orderGoodsService;
	@Resource(name = "addressService")
	private AddressService addressService;
	@Resource(name = "userThirdInfoService")
	private UserThirdInfoService userThirdInfoService;
	@Override
	@Transactional
	public RequestResult mpPay(String oId) {
		/**
		 * 判断oid是否存在
		 */
		Order order = this.orderService.findById(oId);
		if (null == order) {
			return ResultFactory.generateResNotFoundResult();
		}
		String currUserId = WebUtils.getCurrUserId();
		// 只能支付自己的订单
		if(!order.getMemberId().equals(currUserId)){
			return ResultFactory.generateResNotFoundResult();
		}

		UserThirdInfo thirdInfo = AppBeanInjector.userThirdInfoService.findByUserId(currUserId);
		if(LwxfStringUtils.isBlank(thirdInfo.getWxOpenId())){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_MB_NOT_BIND_WX_10029,AppBeanInjector.i18nUtil.getMessage("BIZ_MB_NOT_BIND_WX_10029"));
		}
		List<GoodsDto> goodsDtos = this.orderService.findGoodsDtoByOrderIds(new HashSet<String>(){{add(oId);}});
		// 商品不存在
		if(goodsDtos.size() == 0){
			return ResultFactory.generateResNotFoundResult();
		}
		// 商品中存在下架的
		if(goodsDtos.stream().anyMatch(gd -> gd.getDisabled())){
			return ResultFactory.generateResNotFoundResult();
		}

		Company company = AppBeanInjector.companyService.findById(WebUtils.getCurrCompanyId());

		Map<String,String> unifiedOrderSignMap = new HashMap<>();
		String appId = this.properties.getAppId();
		String machId = this.properties.getMchId();
		String deviceInfo = "WEB";
		String nonceStr = StringUtil.getRandomString(32);
		String signType = WxPayConstants.SignType.MD5;
		String body = LwxfStringUtils.format(UNIFIED_ORDER_BODY_TEMPLATE,company.getName());
		String attach = company.getName();
		String notifyUrl = this.properties.getNotifyUrl();
		String tradeNo = AppBeanInjector.uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.TRADE_NO); // TODO：设成实际的订单编号
		int totleFee = (int)(order.getPaidPrice().doubleValue()*100);
		String tradeType = WxPayConstants.TradeType.JSAPI;
		String spbillCreateIp = WebUtils.getClientIpAddress();
		String openId = thirdInfo.getWxOpenId();
		String detail = this.createUnifiedOrderDetail(order,goodsDtos);

		unifiedOrderSignMap.put("appid",appId);
		unifiedOrderSignMap.put("mch_id",machId);
		unifiedOrderSignMap.put("device_info",deviceInfo);
		unifiedOrderSignMap.put("nonce_str",nonceStr);
		unifiedOrderSignMap.put("sign_type",signType);
		unifiedOrderSignMap.put("body",body);
		unifiedOrderSignMap.put("attach",attach);
		unifiedOrderSignMap.put("notify_url",notifyUrl);
		unifiedOrderSignMap.put("out_trade_no",tradeNo);
		unifiedOrderSignMap.put("total_fee",totleFee+"");
		unifiedOrderSignMap.put("trade_type",tradeType);
		unifiedOrderSignMap.put("spbill_create_ip",spbillCreateIp);
		unifiedOrderSignMap.put("openid",openId);
		unifiedOrderSignMap.put("detail",detail);

		//修改订单的trade_no
		MapContext context=MapContext.newOne();
		context.put(WebConstant.KEY_ENTITY_ID,order.getId());
		context.put(WebConstant.KEY_ENTITY_TRADENO,tradeNo);
		this.orderService.updateByMapContext(context);
		this.logger.error("************************** 微信支付参数：".concat(JsonUtil.toJson(unifiedOrderSignMap)));
		String unifiedSign = SignUtils.createSign(unifiedOrderSignMap,signType,this.properties.getMchKey(),Boolean.FALSE.booleanValue());

		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setAppid(appId);
		request.setMchId(machId);
		request.setDeviceInfo(deviceInfo);
		request.setNonceStr(nonceStr);
		request.setSignType(signType);
		request.setBody(body);
		request.setAttach(attach);
		request.setNotifyUrl(notifyUrl);
		request.setOutTradeNo(tradeNo);
		request.setTotalFee(totleFee);
		request.setTradeType(tradeType);
		request.setSpbillCreateIp(spbillCreateIp);
		request.setOpenid(openId);
		request.setDetail(detail);
		request.setSign(unifiedSign);
		WxPayUnifiedOrderResult result ;
		Map<String, String> map=new HashMap<>();
		try {
			result = AppBeanInjector.wxPayService.unifiedOrder(request);
			if(LwxfStringUtils.isNotBlank(result.getErrCode())){
				this.logger.error("统一下单出现错误：errorCode = ".concat(result.getErrCode()).concat(" ，errorCodeDes = ".concat(result.getErrCodeDes())));
				return ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001, AppBeanInjector.i18nUtil.getMessage("SYS_EXECUTE_FAIL_00001"));
			}
			this.logger.error("************************** 统一下单返回结果："+result.getXmlString());
			// 生成支付签名
			//appId、timeStamp、nonceStr、package、signType
			map.put("appId",this.properties.getAppId());
			map.put("timeStamp",DateUtil.getTimeStamp());
			map.put("nonceStr",nonceStr);
			map.put("package","prepay_id=".concat(result.getPrepayId()));
			map.put("signType",signType);
			map.put("paySign",SignUtils.createSign(map,signType,this.properties.getMchKey(),Boolean.FALSE.booleanValue()));
			map.put("pg",map.remove("package"));
			logger.error("********************** 微信支付返回前台参数：".concat(JsonUtil.toJson(map)));
		} catch (Exception e) {
			this.logger.error("统一下单出现异常",e);
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001, AppBeanInjector.i18nUtil.getMessage("SYS_EXECUTE_FAIL_00001"));
		}
		return ResultFactory.generateRequestResult(map);
	}

	@Override
	public RequestResult closeOrder(String orderId) {
		Order order = this.orderService.findById(orderId);
		if(order == null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!order.getMemberId().equals(WebUtils.getCurrUserId())){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		try {
			this.logger.error("********************** 关闭微信订单操作 **********************");
			this.logger.error("********************** order id：".concat(order.getId()));
			this.logger.error("********************** order no：".concat(order.getOrderNumber()));
			this.logger.error("********************wx trade no：".concat(order.getTradeNo()));
			WxPayOrderCloseResult closeResult = AppBeanInjector.wxPayService.closeOrder(order.getTradeNo());
			if(LwxfStringUtils.isNotBlank(closeResult.getErrCode())){
				return ResultFactory.generateErrorResult(closeResult.getErrCode(),closeResult.getErrCodeDes());
			}
			return ResultFactory.generateSuccessResult();
		} catch (WxPayException e) {
			this.logger.error("关闭订单出现异常",e);
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001,AppBeanInjector.i18nUtil.getMessage("SYS_EXECUTE_FAIL_00001"));
		}
	}

	@Override
	@Transactional
	public RequestResult createPaidRecord(String orderId) {
		/**
		 * 判断订单是否存在
		 */
		Order order = this.orderService.findById(orderId);
		if (null == order) {
			return ResultFactory.generateResNotFoundResult();
		}
		String currUserId = WebUtils.getCurrUserId();

		// 只能为自己的订单创建支付记录
		if(!order.getMemberId().equals(currUserId)){
			return ResultFactory.generateResNotFoundResult();
		}

		Date currDate = com.lwxf.commons.utils.DateUtil.getSystemDate();
		PaidRecords paidRecords = new PaidRecords();
		paidRecords.setCreated(currDate);
		paidRecords.setMemberId(currUserId);
		paidRecords.setOrderId(orderId);
		paidRecords.setPaidNum(AppBeanInjector.uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.PAID_RECORDS_NO));
		paidRecords.setPaidPrice(order.getPaidPrice());
		paidRecords.setPaidTime(currDate);
		paidRecords.setType(PaidRecordsType.ORDER_PAID.getValue());
		this.paidRecordsService.add(paidRecords);
		MapContext mapContext=MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,order.getId());
		mapContext.put(WebConstant.KEY_ENTITY_STATUS,OrderStatus.ALREADYPAID.getValue());
		mapContext.put(WebConstant.KEY_ENTITY_PAIDTIME, com.lwxf.commons.utils.DateUtil.getSystemDate());
		this.orderService.updateByMapContext(mapContext);
		//支付成功后 订单下的商品的库存-1 销量+1
		List<OrderGoods> orderGoodsList = this.orderGoodsService.selectByOrderId(orderId);
		for (OrderGoods orderGoods:orderGoodsList) {
			this.goodsService.updataStock(orderGoods.getGoodsExtendId());
		}
		Map<String,String> map = new HashMap<>();
		map.put("order_number",order.getOrderNumber());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("order_time",format.format(order.getCreated()));
		Address address= this.addressService.findById(order.getReceiptId());
		map.put("receipt_id",address.getReceiverAddress());
		map.put("contacts",address.getReceiver());
		map.put("status","0");
		AddOrderSuccessForClerkMsg addOrderSuccessForClerkMsg = new AddOrderSuccessForClerkMsg();
		List<UserThirdInfo> clerks= this.userThirdInfoService.findAllClerks();
		addOrderSuccessForClerkMsg.setContentMsg(map);
		if(clerks.size()!=0){
			for(UserThirdInfo userThirdInfo:clerks){
				addOrderSuccessForClerkMsg.setTouser(userThirdInfo.getWxOpenId());
				AppBeanInjector.weixinTemplateMsgService.pushMsg(addOrderSuccessForClerkMsg);
			}
		}
		return ResultFactory.generateRequestResult(paidRecords);
	}

	private String createUnifiedOrderDetail(Order order, List<GoodsDto> goodsDtos){
		UnifiedOrderDetail orderDetail = new UnifiedOrderDetail();
		orderDetail.setOrderId(order.getId());
		orderDetail.setCostPrice(order.getPaidPrice());
		orderDetail.setUserId(order.getMemberId());
		List<UnifiedOrderGoodsDetail> orderGoodsInfos = new ArrayList<>();
		orderDetail.setGoodsDetails(orderGoodsInfos);
		UnifiedOrderGoodsDetail orderGoodsDetail;
		for(GoodsDto goodsDto:goodsDtos){
			orderGoodsDetail = new UnifiedOrderGoodsDetail();
			orderGoodsDetail.setGoodsId(goodsDto.getGoodsId());
			orderGoodsDetail.setGoodsName(goodsDto.getName());
			orderGoodsDetail.setGoodsExtendId(goodsDto.getGoodsExtendId());
			orderGoodsDetail.setOptions(goodsDto.getOptions());
			orderGoodsDetail.setPaidPrice(goodsDto.getPaidPrice());
			orderGoodsDetail.setSmallimage(goodsDto.getSmallimage());

			orderGoodsInfos.add(orderGoodsDetail);
		}
		return JsonUtil.toJson(orderDetail);
	}
}

class UnifiedOrderDetail{
	String orderId;
	BigDecimal costPrice;
	String userId;
	List<UnifiedOrderGoodsDetail> goodsDetails;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<UnifiedOrderGoodsDetail> getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(List<UnifiedOrderGoodsDetail> goodsDetails) {
		this.goodsDetails = goodsDetails;
	}
}

class UnifiedOrderGoodsDetail{
	private String goodsId;
	private String goodsName;
	private String goodsExtendId;
	private BigDecimal paidPrice;
	private String options;
	private String smallimage;

	public String getGoodsExtendId() {
		return goodsExtendId;
	}

	public void setGoodsExtendId(String goodsExtendId) {
		this.goodsExtendId = goodsExtendId;
	}

	public BigDecimal getPaidPrice() {
		return paidPrice;
	}

	public void setPaidPrice(BigDecimal paidPrice) {
		this.paidPrice = paidPrice;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getSmallimage() {
		return smallimage;
	}

	public void setSmallimage(String smallimage) {
		this.smallimage = smallimage;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}