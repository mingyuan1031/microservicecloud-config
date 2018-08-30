package com.lwxf.newstore.webapp.common.worker.activity.order.Builder;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.order.AddressService;
import com.lwxf.newstore.webapp.bizservice.order.LogisticsService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.order.LogisticsResEntity;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/25 11:35
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class LogisticsActivityBuilder extends BaseActivityBuilder {

	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "logisticsService")
	private LogisticsService logisticsService;
	@Resource(name = "addressService")
	private AddressService addressService;

	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Logistics.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		LogisticsResEntity logisticsResEntity = new LogisticsResEntity();
		Map<Object, Object> attrMap = new HashMap<Object, Object>();
		Logistics logistics;
		String id;
		switch (sqlType) {
			/*case INSERT:
				logistics = (Logistics) params;
				systemActivity.setEvent(SystemActivityEvent.LOGISTICS_CREATE.getValue());
				systemActivity.setR1(logistics.getId());
				logisticsResEntity.setLogisticsName(logistics.getLogisticsName());
				logisticsResEntity.setLogisticsNumber(logistics.getLogisticsNumber());
				logisticsResEntity.setOrderGoodsId(logistics.getOrderGoodsId());
				logisticsResEntity.setReceiptId(logistics.getReceiptId());
				if (!logistics.getRemarks().isEmpty()) {
					logisticsResEntity.setRemarks(logistics.getRemarks());
				}
				break;*/
			case UPDATE:
				Map paranMap = (Map) params;
				id = (String) paranMap.get(WebConstant.KEY_ENTITY_ID);
				logistics = this.logisticsService.findById(id);
				systemActivity.setEvent(SystemActivityEvent.LOGISTICS_MD.getValue());
				systemActivity.setR1(id);
				logisticsResEntity.setLogisticsName(logistics.getLogisticsName());
				logisticsResEntity.setLogisticsNumber(logistics.getLogisticsNumber());
				logisticsResEntity.setOrderGoodsId(logistics.getOrderId());
				logisticsResEntity.setReceiptAddress(this.addressService.findById(logistics.getReceiptId()).getReceiverAddress());
				//便利参数的map对象
				paranMap.forEach((key,value) ->{
					//如果参数是 id  或者 状态 则不允许修改
					if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_DISABLED)){
						return;
					}
					//把修改的属性值名称及对应的值放进attr中
					attrMap.put(key,value);
				});
				//把attr放入R3的实体类类型中
				activityInfoEntity.setAttr(attrMap);
				break;
			case DELETE:
				id = (String) params;
				logistics = this.logisticsService.findById(id);
				systemActivity.setEvent(SystemActivityEvent.LOGISTICS_DELETE.getValue());
				systemActivity.setR1(logistics.getId());
				logisticsResEntity.setLogisticsName(logistics.getLogisticsName());
				logisticsResEntity.setLogisticsNumber(logistics.getLogisticsNumber());
				logisticsResEntity.setOrderGoodsId(logistics.getOrderGoodsId());
				logisticsResEntity.setReceiptAddress(this.addressService.findById(logistics.getReceiptId()).getReceiverAddress());
				if (logistics.getRemarks() != null) {
					logisticsResEntity.setRemarks(logistics.getRemarks());
				}
				break;
		}
		activityInfoEntity.setRes(logisticsResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	/**
	 * 埋点的情况
	 *
	 * @param tsManualData
	 * @return
	 */
	@Override
	public Object build(TSManualData tsManualData) {//添加
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params = tsManualData.getParams();
		Logistics logistics;
		if (tsManualData.getEvent().equals(SystemActivityEvent.LOGISTICS_CREATE.getValue())) {
			logistics=(Logistics)params;
			systemActivity.setEvent(SystemActivityEvent.LOGISTICS_CREATE.getValue());
			systemActivity.setR1(logistics.getId());
			ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
			LogisticsResEntity logisticsResEntity = new LogisticsResEntity();
			logisticsResEntity.setLogisticsName(logistics.getLogisticsName());
			logisticsResEntity.setLogisticsNumber(logistics.getLogisticsNumber());
			logisticsResEntity.setOrderGoodsId(logistics.getOrderGoodsId());
			logisticsResEntity.setReceiptAddress(this.addressService.findById(logistics.getReceiptId()).getReceiverAddress());
			if (!logistics.getRemarks().isEmpty()) {
				logisticsResEntity.setRemarks(logistics.getRemarks());
			}
			activityInfoEntity.setRes(logisticsResEntity);
			systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
			systemActivity.setCompanyId(WebUtils.getCurrCompanyId());
		} else {
			return null;
		}
		return systemActivity;
	}
}