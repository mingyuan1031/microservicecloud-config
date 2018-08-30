package com.lwxf.newstore.webapp.facade.order.impl;

import com.lwxf.commons.uniquekey.IdGererateFactory;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.cart.CartService;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.order.*;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.order.OrderStatus;
import com.lwxf.newstore.webapp.common.enums.order.PaidMethod;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.AddOrderSuccessForClerkMsg;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.OrderCloselToClerksMsg;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.OrderCloselToCustomerMsg;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dto.cart.CartPriceDto;
import com.lwxf.newstore.webapp.domain.dto.order.*;
import com.lwxf.newstore.webapp.domain.dto.user.PaidDto;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.domain.entity.order.PaidRecords;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.order.OrderFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/26 15:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("orderFacade")
public class OrderFacadeImpl extends BaseFacadeImpl implements OrderFacade {

	@Resource
	private LogisticsService logisticsService;
	@Resource
	private IdGererateFactory idGererateFactory;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderGoodsService orderGoodsService;
	@Resource
	private UserService userService;
	@Resource(name = "cartService")
	private CartService cartService;
	@Resource
	private AddressService addressService;
	@Resource(name = "cityAreaService")
	private CityAreaService cityAreaService;
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Resource(name = "paidRecordsService")
	private  PaidRecordsService paidRecordsService;
	@Resource(name = "userThirdInfoService")
	private UserThirdInfoService userThirdInfoService;
	@Resource(name = "storeConfigService")
	private StoreConfigService storeConfigService;
	/**
	 * 创建订单
	 * @param params
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult createOrder(Map<String, Object> params) {
		List<String> ids = (List<String>) params.get("ids");
		//获取购物车信息
		List<CartPriceDto> cartPriceDtos = cartService.findByIds(ids);
		if (cartPriceDtos.size() == 0) {
			return ResultFactory.generateResNotFoundResult();
		}
		//计算订单总金额
		double total_price = 0;
		String orderId = this.orderService.getNextId();
		//创建订单
		Order order = new Order();
		order.setId(orderId);
		//把购物车信息添加到商品订单列表中
		List<OrderGoods> orderGoodsList = new ArrayList<>();
		for (CartPriceDto cart : cartPriceDtos) {
			OrderGoods og = new OrderGoods();
			og.setId(this.idGererateFactory.nextStringId());
			og.setGoodsExtendId(cart.getExtendId());
			og.setGoodsAmount(cart.getCount());
			og.setOrderId(order.getId());
			og.setPaidPrice(new BigDecimal(cart.getPrice()));
			og.setStatus(0);
			orderGoodsList.add(og);
			total_price = total_price + cart.getPrice() * cart.getCount();
		}
		//total_price = total_price + Double.valueOf(params.get("freight").toString());
		//设置订单属性
		order.setMemberId(WebUtils.getCurrUserId());
		//order.setPaidPrice(new BigDecimal(total_price).setScale(2,BigDecimal.ROUND_HALF_UP));
		//order.setFreight(new BigDecimal(params.get("freight").toString()));
		order.setPaidMethod(PaidMethod.WeiXin.getValue());
		if (!StringUtils.isBlank(params.get("desrc").toString())) {
			order.setDescr(params.get("desrc").toString());
		}
		order.setStatus(0);
		order.setCreated(DateUtil.getSystemDate());
		if(!this.addressService.isExist(params.get("receiptId").toString())) {
			return  ResultFactory.generateResNotFoundResult();
		}
		order.setReceiptId(params.get("receiptId").toString());
		//判断金额是否够包邮足够的话 就设置邮费为0
		if(new BigDecimal(total_price).compareTo(new BigDecimal(this.storeConfigService.findOneByLimit().getPinkage()))!=-1){
			order.setFreight(new BigDecimal(0));
			order.setPaidPrice(new BigDecimal(total_price).setScale(2,BigDecimal.ROUND_HALF_UP));
		}else{
			order.setFreight(new BigDecimal(params.get("freight").toString()));
			order.setPaidPrice(new BigDecimal(total_price).setScale(2,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(params.get("freight").toString())));
		}
		//添加订单
		int add = this.orderService.add(order);
		//埋点开始
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Order.class);
		tsManualData.setEvent(SystemActivityEvent.ORDER_CREATE.getValue());
		tsManualData.setParams(order);
		//埋点结束
		//添加商品订单
		this.orderGoodsService.insertByBatch(orderGoodsList);
		String[] dIds = new String[ids.size()];
		//删除购物车
		this.cartService.deleteByIds(ids.toArray(dIds));
		return ResultFactory.generateRequestResult(order);
	}

	/**
	 * 删除订单
	 * @param oid
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult deleteOrder(String oid) {
		if (!this.orderService.isExist(oid)) {
			return ResultFactory.generateResNotFoundResult();
		}
		this.orderService.deleteById(oid);
		return ResultFactory.generateSuccessResult();
	}

	/**
	 * 修改订单
	 * @param oid
	 * @param
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateOrder(String oid,  Integer status) {
		Order order = this.orderService.findById(oid);
		if (order.equals(null)) {
			return ResultFactory.generateResNotFoundResult();
		}
		MapContext mapContext=MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_UPDATED, DateUtil.getSystemDate());
		mapContext.put(WebConstant.KEY_ENTITY_ID, oid);
		mapContext.put(WebConstant.KEY_ENTITY_STATUS,status);
		//埋点开始
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setEvent(SystemActivityEvent.ORDER_STATUS_MD.getValue());
		tsManualData.setClazz(Order.class);
		tsManualData.setParams(mapContext);
		//埋点结束
		this.orderService.updateByMapContext(mapContext);
		if(status.equals(OrderStatus.REVOKED.getValue()))
		{
			Set<String> strList = new HashSet<>();
			strList.add(oid);
			List<GoodsDto> goodsDtos = this.orderService.findGoodsDtoByOrderIds(strList);
			Map<String,String> map = new HashMap<>();
			map.put("order_number",order.getOrderNumber());

			if (goodsDtos.size()>0)
			{
				for (GoodsDto goodsDto:goodsDtos)
				{
					map.put("goods_name",goodsDto.getName());
					map.put("order_price",goodsDto.getPaidPrice().toString());
				}
			}

			OrderCloselToClerksMsg msg = new OrderCloselToClerksMsg();

			msg.setContentMsg(map);
			List<UserThirdInfo> clerks = AppBeanInjector.userThirdInfoService.findAllClerks();
			if (clerks.size()!=0){
				for (UserThirdInfo thirdInfo:clerks) {
					msg.setTouser(thirdInfo.getWxOpenId());
					AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
				}
			}

			OrderCloselToCustomerMsg msgToCustomer = new OrderCloselToCustomerMsg();
			msgToCustomer.setContentMsg(map);
			UserThirdInfo userThirdInfo = AppBeanInjector.userThirdInfoService.findByUserId(order.getMemberId());
			msg.setTouser(userThirdInfo.getWxOpenId());
			AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
		}
		return ResultFactory.generateSuccessResult();
	}

	/**
	 * 查询订单详情（前台）
	 * @param oid
	 * @return
	 */
	@Override
	public RequestResult findOrderById(String oid) {
		Order order = this.orderService.findById(oid);
		Set<String> orderIds = new HashSet<>();
		Map<String, OrderDto> orderDtoMap = new HashMap<String, OrderDto>();
		if (order==null)
		{
			return ResultFactory.generateResNotFoundResult();
		}
		orderIds.add(order.getId());
		OrderDto orderDto = order.clone();
		orderDtoMap.put(order.getId(), orderDto);
		List<GoodsDto> goodsDtos = this.orderService.findGoodsDtoByOrderIds(orderIds);
		goodsDtos.stream().forEach(gd -> {
			orderDtoMap.get(gd.getOrderId()).getGoodsDtos().add(gd);
		});
		Address address= this.addressService.findById(order.getReceiptId());
		orderDto.setAddress(address);
		return ResultFactory.generateRequestResult(orderDto);
	}

	/**
	 * 查询订单列表
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 */
	@Override
	public RequestResult findOrderList(Integer pageNum, Integer pageSize, MapContext params) {
		PaginatedFilter paginatedFilter = PaginatedFilter.newOne();
		paginatedFilter.setFilters(params);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		paginatedFilter.setPagination(pagination);
		PaginatedList<Order> list = this.orderService.selectByFilter(paginatedFilter);
		List<OrderDto> orderDtoList = new ArrayList<>();
		Set<String> orderIds = new HashSet<>();
		Map<String, OrderDto> orderDtoMap = new HashMap<String, OrderDto>();
		for (Order order : list.getRows()) {
			orderIds.add(order.getId());
			OrderDto orderDto = order.clone();
			orderDtoMap.put(order.getId(), orderDto);
			orderDtoList.add(orderDto);
		}

		List<GoodsDto> goodsDtos = this.orderService.findGoodsDtoByOrderIds(orderIds);
		goodsDtos.stream().forEach(gd -> {
			orderDtoMap.get(gd.getOrderId()).getGoodsDtos().add(gd);
		});
		PaginatedList paginatedList = PaginatedList.newOne();
		paginatedList.setPagination(list.getPagination());
		paginatedList.setRows(orderDtoList);
		return ResultFactory.generateRequestResult(paginatedList);
	}

	/**
	 * 查询会员订单
	 * @param mid
	 * @return
	 */
	@Override
	public RequestResult findOrderListByMemberId(String mid) {

		List<Order> list = this.orderService.findOrderList(mid);
		List<OrderDto> orderDtoList = new ArrayList<>();
		Set<String> orderIds = new HashSet<>();
		Map<String, OrderDto> orderDtoMap = new HashMap<String, OrderDto>();
		if(list==null||list.size()==0){

		}else{
			for (Order order : list) {
				orderIds.add(order.getId());
				OrderDto orderDto = order.clone();
				orderDtoMap.put(order.getId(), orderDto);
				orderDtoList.add(orderDto);
			}
		}
		if (orderIds.size()==0||orderIds.isEmpty())
		{
			return ResultFactory.generateRequestResult(null);
		}
		List<GoodsDto> goodsDtos = this.orderService.findGoodsDtoByOrderIds(orderIds);
		goodsDtos.stream().forEach(gd -> {
			orderDtoMap.get(gd.getOrderId()).getGoodsDtos().add(gd);
		});

		return ResultFactory.generateRequestResult(orderDtoList);
	}

	/**
	 * 查询商品订单列表
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 */
	@Override
	public RequestResult selsectOrderGoodsDtoList(Integer pageNum, Integer pageSize, MapContext params) {

		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(params);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		PaginatedList<Order> orderPaginatedList = this.orderService.selectByFilter(filter);
//		PaginatedList<OrderGoods> list = this.orderService.selectOrderGoodsDtoByFilter(filter);
//		List<OrderGoods> orderList = list.getRows();
		List<Order> orderList = orderPaginatedList.getRows();
		Set<String> orderIds = new HashSet<>();
		for (Order order : orderList) {
			orderIds.add(order.getId());
		}
		List<LogisticsAddressDto> logistics = null;
		List<GoodsDto> goodsDtoList=null;
		if (orderIds.size() > 0) {
			logistics = this.logisticsService.getLogisticsAddressDtoByOrderIds(orderIds);
			goodsDtoList = this.orderService.findGoodsDtoByOrderIds(orderIds);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("orders", orderList); // TODO：查订单表数据
		map.put("goods", goodsDtoList); // TODO：查询本次的订单列表中的所有商品数据
		map.put("logistics", logistics);

		return ResultFactory.generateRequestResult(map, orderPaginatedList.getPagination());

	}

	/**
	 * 查询商品订单详情
	 * @param ogid
	 * @return
	 */
	@Override
	public RequestResult findOrderDetailsByOrderId(String ogid) {
		if (!this.orderGoodsService.isExist(ogid)) {
			return ResultFactory.generateResNotFoundResult();
		}
		GoodsDto goodsDto = this.orderService.selectGoodsDtoByOrderGoodsId(ogid);
		Order order = this.orderService.findById(goodsDto.getOrderId());
		LogisticsAddressDto logisticsAddress = this.logisticsService.selectLogisticsAdDtosByOrderGoodsId(ogid);
		Map<String, Object> map = new HashMap<>();
		map.put("goods", goodsDto);
		map.put("order", order);
		if(logisticsAddress==null)
		{
			String currUserId = WebUtils.getCurrUserId();
			if (StringUtils.isBlank(currUserId))
			{
				return  ResultFactory.generateErrorResult(ErrorCodes.BIZ_USER_NOT_FOUND_10002,AppBeanInjector.i18nUtil.getMessage("BIZ_USER_NOT_FOUND_10002"));
			}
			Address address = addressService.findById(order.getReceiptId());
			map.put("logisticsAddress",address);
		}else {
			map.put("logisticsAddress", logisticsAddress);
		}
		return ResultFactory.generateRequestResult(map);
	}

	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	@Override
	public RequestResult getOrderDetails(String id) {

		if (!this.orderService.isOrderExist(id)) {
			return ResultFactory.generateResNotFoundResult();
		}
		Order order = this.orderService.findById(id);
		List<GoodsDetailsDto> goodsDetailsDtos = this.orderGoodsService.selectGoodsDetailsByOrderId(id);
		User user = this.userService.findById(order.getMemberId());
		List<LogisticsDto> logisticsDtos = this.logisticsService.selectLogisticsDtoByOrderId(id);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", order);
		map.put("goods", goodsDetailsDtos);
		map.put("member", user);
		if (logisticsDtos!=null) {
			map.put("logistics", logisticsDtos);
		}

		return ResultFactory.generateRequestResult(map);
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult addOrder( Map<String,Object> params) {
		//取出参数
		String id = (String) params.get("id");
		String receiptId = (String) params.get("receiptId");
		String desrc = (String) params.get("desrc");
		String freight = (String) params.get("freight");
		Integer amount = (Integer) params.get("amount");
		if (StringUtils.isBlank(id)||StringUtils.isBlank(receiptId)||StringUtils.isBlank(freight)||amount==null)
		{
			return  ResultFactory.generateResNotFoundResult();
		}
		//判断id是否存在，如果存在取出goodsExtend
		GoodsExtend goodsExtend = this.goodsExtendService.findById(id);
		if (goodsExtend==null) {
			return ResultFactory.generateResNotFoundResult();
		}
		//获得orderId
		String orderId = this.orderService.getNextId();
		//创建orderGoods
		OrderGoods orderGoods=new OrderGoods();
		orderGoods.setPaidPrice(goodsExtend.getPrice());
		orderGoods.setStatus(0);
		orderGoods.setGoodsExtendId(id);
		orderGoods.setGoodsAmount(amount);
		orderGoods.setOrderId(orderId);
		//计算金额
		BigDecimal multiplyValue = orderGoods.getPaidPrice().multiply(new BigDecimal(orderGoods.getGoodsAmount()));
		//创建order
		Order order=new Order();
		order.setId(orderId);
		order.setMemberId(WebUtils.getCurrUserId());
		order.setReceiptId(receiptId);
		order.setFreight(new BigDecimal(freight));
		order.setPaidMethod(PaidMethod.WeiXin.getValue());
		order.setStatus(0);
		order.setDescr(desrc);
		order.setCreated(DateUtil.getSystemDate());
		//判断金额是否够包邮足够的话 就设置邮费为0
		if(multiplyValue.compareTo(new BigDecimal(this.storeConfigService.findOneByLimit().getPinkage()))!=-1){
			order.setFreight(new BigDecimal(0));
			order.setPaidPrice(multiplyValue);
		}else{
			order.setFreight(new BigDecimal(freight));
			order.setPaidPrice(multiplyValue.add(new BigDecimal(freight)));
		}
		//把order和orderGoods添加到数据库
		this.orderService.add(order);
		this.orderGoodsService.add(orderGoods);
		Map<String,Object> map=MapContext.newOne();
		map.put("order",order);
		map.put("orderGoods",orderGoods);
		return ResultFactory.generateRequestResult(map);
	}

	@Override
	public RequestResult getPaidrecords(String id) {

		List<PaidDto> list =this.paidRecordsService.findByMemberId(id);
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateOrderMapContext(MapContext context) {
		this.orderService.updateByMapContext(context);
		return ResultFactory.generateRequestResult(context);
	}
}
