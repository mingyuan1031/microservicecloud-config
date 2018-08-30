package com.lwxf.newstore.webapp.common.worker.cart;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.cart.CartService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/21 11:24
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class CartActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "cartService")
	private CartService cartService;
	@Resource(name = "userService")
	private UserService userService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Cart.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		CartResEntity cartResEntity = new CartResEntity();
		Map<String, Object> attrMap;
		Cart cart;
		String id;
		switch (sqlType){
			case INSERT:
				cart = (Cart) params;
				systemActivity.setEvent(SystemActivityEvent.CART_CREATE.getValue());
				systemActivity.setR1(cart.getId());
				cartResEntity.setName(WebUtils.getCurrUser().getName());
				cartResEntity.setCount(cart.getCount());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				cart = this.cartService.findById(id);
				systemActivity.setR1(id);
				cartResEntity = new CartResEntity();
				cartResEntity.setName(WebUtils.getCurrUser().getName());
				cartResEntity.setCount(cart.getCount());
				systemActivity.setEvent(SystemActivityEvent.CART_MD.getValue());
				attrMap = new HashMap<>();
				paramsMap.forEach((key,value) ->{
					if(key.equals(WebConstant.KEY_ENTITY_ID)){
						return;
					}
					attrMap.put(key,value);
				});
				activityInfoEntity.setAttr(attrMap);
				break;
			case DELETE:
				Map<String, Object> paramMap = (Map) params;
				String[] cartIds = (String[]) paramMap.get("array");
				List<SystemActivity> list = new ArrayList<>();
				for(String cartId : cartIds){
					SystemActivity systemActivitys = newSystemActivityInstance();
					cartResEntity = new CartResEntity();
					id = cartId;
					cart = this.cartService.findById(id);
					systemActivitys.setR1(id);
					cartResEntity.setName(WebUtils.getCurrUser().getName());
					cartResEntity.setCount(cart.getCount());
					systemActivitys.setEvent(SystemActivityEvent.CART_DELETE.getValue());
					activityInfoEntity.setRes(cartResEntity);
					systemActivitys.setR3(this.jsonMapper.toJson(activityInfoEntity));
					list.add(systemActivitys);
				}
				return list;
			default:
				return null;
		}
		activityInfoEntity.setRes(cartResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	// 没有埋点
	@Override
	public Object build(TSManualData tsManualData) {
		return null;
	}
}
