package com.lwxf.newstore.webapp.controller.api.reservation;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.xml.crypto.Data;

import org.springframework.web.bind.annotation.*;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.reservation.ReservationFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/19 18:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class ReservationController {

	@Resource(name = "reservationFacade")
	private ReservationFacade reservationFacade;

	@DeleteMapping(value = "/reservations/{id}")
	public RequestResult delReservations(@PathVariable String id){
		return this.reservationFacade.deleteReservationById(id);
	}

	@PutMapping(value = "/reservations/{id}")
	public RequestResult putReservation(@PathVariable String id, @RequestBody MapContext update){
		RequestResult result = Reservation.validateFields(update);
		if(null != result){
			return result;
		}
		boolean isMember = WebUtils.getCurrUser().getRole().intValue()==UserRole.MEMBER.getValue();
		// 非店员用户没有修改状态和描述的权限
		if(isMember){
			if(update.containsKey(WebConstant.KEY_ENTITY_STATUS) || update.containsKey(WebConstant.KEY_ENTITY_DESCR)){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
			}
		}
		return this.reservationFacade.putReservation(id, update);
	}

	@GetMapping(value = "/reservations")
	public RequestResult inquires(@RequestParam(required = false) String name,
								  @RequestParam(required = false) String phone,
								  @RequestParam(required = false) Integer status,
								  @RequestParam(required = false) String startTime,
								  @RequestParam(required = false) String endTime,
								  @RequestParam(required = false,defaultValue = "1") Integer pageNum,
								  @RequestParam(required = false,defaultValue = "30") Integer pageSize){
		MapContext mapContext = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			mapContext.put(WebConstant.KEY_ENTITY_NAME,name);
		}
		if(LwxfStringUtils.isNotBlank(phone)){
			mapContext.put(WebConstant.KEY_ENTITY_PHONE, phone);
		}
		if(null != status && LwxfStringUtils.isNotBlank(status.toString())){
			mapContext.put(WebConstant.KEY_ENTITY_STATUS, status);
		}
		if(LwxfStringUtils.isNotBlank(startTime)){
		    mapContext.put(WebConstant.KEY_ENTITY_STARTTIME, startTime);
		}
		if(LwxfStringUtils.isNotBlank(endTime)){
		    mapContext.put(WebConstant.KEY_ENTITY_ENDTIME, endTime);
		}
		return this.reservationFacade.findReservationFilter(pageNum, pageSize, mapContext);
	}
}
