package com.lwxf.newstore.webapp.common.worker.activity.order.Builder;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.apache.poi.hssf.record.PageBreakRecord;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.bizservice.reservation.ReservationService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.order.AddressResEntity;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *用户地址
 * @author：wangmingyuan
 * @create：2018/7/24 16:38
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class AddressActivityBuilder extends BaseActivityBuilder {

	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "addressService")
	private AddressService addressService;
	@Override
	public void registerToWorker() {
			this.systemActivityWorker.register(Address.class,this);
	}
	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		AddressResEntity resEntity=new AddressResEntity();
		Map<String, Object> attrMap;
		Address address;
		String id;
		switch (sqlType)
		{
			case INSERT:
				address=(Address)params;
				systemActivity.setEvent(SystemActivityEvent.ADDRESS_CREATE.getValue());
				systemActivity.setR1(address.getId());
				resEntity.setReceiver(address.getReceiver());
				resEntity.setReceiverPhone(address.getReceiverPhone());
				resEntity.setReceiverAddress(address.getReceiverAddress());
				resEntity.setCityAreaId(address.getCityAreaId());
				resEntity.setMemberId(address.getMemberId());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				address = this.addressService.findById(id);
				systemActivity.setR1(id);
				resEntity=new AddressResEntity();
				resEntity.setReceiver(address.getReceiver());
				resEntity.setMemberId(address.getMemberId());
				resEntity.setReceiverPhone(address.getReceiverPhone());
				resEntity.setReceiverAddress(address.getReceiverAddress());
				resEntity.setCityAreaId(address.getCityAreaId());
				Object status = paramsMap.get("status");
				if (status!=null) {
					systemActivity.setEvent(SystemActivityEvent.ADDRESS_STATUS_MD.getValue());
					Byte statsValue = Byte.valueOf(status.toString());
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
					activityInfoEntity.setAttr(attrMap);
				}else{
					systemActivity.setEvent(SystemActivityEvent.ADDRESS_MD.getValue());
					attrMap = new HashMap<>();
					paramsMap.forEach((key,value) ->{
						if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_STATUS)){
							return;
						}
						attrMap.put(key,value);
					});
					activityInfoEntity.setAttr(attrMap);
				}
				break;
			case DELETE:
				id = (String)tsManagerEntity.getMapperParams();
				address = this.addressService.findById(id);
				systemActivity.setR1(id);
				resEntity=new AddressResEntity();
				resEntity.setReceiver(address.getReceiver());
				resEntity.setMemberId(address.getMemberId());
				resEntity.setReceiverPhone(address.getReceiverPhone());
				resEntity.setReceiverAddress(address.getReceiverAddress());
				resEntity.setCityAreaId(address.getCityAreaId());
				systemActivity.setEvent(SystemActivityEvent.ADDRESS_DELETE.getValue());
				break;
			default:
				return null;
		}
		activityInfoEntity.setRes(resEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}




}
