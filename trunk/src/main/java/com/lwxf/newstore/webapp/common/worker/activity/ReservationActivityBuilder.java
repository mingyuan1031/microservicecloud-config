package com.lwxf.newstore.webapp.common.worker.activity;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.reservation.ReservationService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.reservation.ReservationStatus;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.ReservationResEntity;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：预约活动日志生成
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-04 17:03
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class ReservationActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "reservationService")
	private ReservationService reservationService;
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Reservation.class, this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		// 创建数据库表SystemActivity对应的实体类对象
		SystemActivity systemActivity = newSystemActivityInstance();
		// 获取映射过来的对象，可能是map也可能是Object 用于下面操作使用
		Object params = tsManagerEntity.getMapperParams();
		// 封装的r3的数据格式 res 最下面使用，attr 修改的数据
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		// 创建自定义Entity，内容存放在res中
		ReservationResEntity resEntity = new ReservationResEntity();
		// 定义attr
		Map<String, Object> attrMap;
		// 定义实体类对象
		Reservation reservation;
		// 操作对象的ID，存放在r1中
		String id;
		// switch case 判断操作类型
		switch (sqlType) {
			case INSERT:
				reservation = (Reservation) params;
				systemActivity.setEvent(SystemActivityEvent.RESERVATION_CREATE.getValue());
				systemActivity.setR1(reservation.getId());
				resEntity.setName(reservation.getName());
				resEntity.setPhone(reservation.getPhone());
				resEntity.setArea(reservation.getArea());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}

				reservation = this.reservationService.findById(id);
				systemActivity.setR1(id);
				resEntity = new ReservationResEntity();
				resEntity.setName(reservation.getName());
				resEntity.setPhone(reservation.getPhone());
				resEntity.setArea(reservation.getArea());
				Object status = paramsMap.get("status");
				if (status != null) {
					systemActivity.setEvent(SystemActivityEvent.RESERVATION_STATUS_MD.getValue());
					Byte statsValue = Byte.valueOf(status.toString());

					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_STATUS,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,ReservationStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.RESERVATION_MD.getValue());
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
				reservation = this.reservationService.findById(id);
				systemActivity.setR1(id);
				resEntity = new ReservationResEntity();
				resEntity.setName(reservation.getName());
				resEntity.setPhone(reservation.getPhone());
				resEntity.setArea(reservation.getArea());
				systemActivity.setEvent(SystemActivityEvent.RESERVATION_DELETE.getValue());
				break;
			default:
				return null;
		}
		activityInfoEntity.setRes(resEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) { // 不存在埋点的情况
		return null;
	}
}
