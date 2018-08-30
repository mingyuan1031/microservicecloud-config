package com.lwxf.newstore.webapp.facade.reservation.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.reservation.ReservationService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.reservation.ReservationStatus;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.reservation.ReservationFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/16 11:55
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("reservationFacade")
public class ReservationFacadeImpl extends BaseFacadeImpl implements ReservationFacade {

	@Resource(name = "reservationService")
	private ReservationService reservationService;

	@Override
	@Transactional
	public RequestResult postReservation(Reservation reservation) {
		reservation.setCreated(DateUtil.getSystemDate());
		reservation.setUserId(WebUtils.getCurrUserId());
		reservationService.add(reservation);
		return ResultFactory.generateSuccessResult();
	}


	@Override
	@Transactional
	public RequestResult putReservation(String reservationId, MapContext update) {
		Reservation reservation = this.reservationService.findById(reservationId);
		if(null == reservation){
			return ResultFactory.generateResNotFoundResult();
		}
		update.put(WebConstant.KEY_ENTITY_ID,reservationId);
		this.reservationService.updateByMapContext(update);
		return ResultFactory.generateSuccessResult();
	}


	@Override
	@Transactional
	public RequestResult deleteReservationById(String id) {
		Reservation verify = this.reservationService.selectById(id);
		int userRole = WebUtils.getCurrUser().getRole().intValue();
		if(userRole == UserRole.MEMBER.getValue()){
			if(!verify.getUserId().equals(WebUtils.getCurrUserId())){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
			}
		}
		if(verify.getStatus().byteValue() != ReservationStatus.UNTREATED.getValue() && verify.getStatus().byteValue() != ReservationStatus.CANCELED.getValue()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003, AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		this.reservationService.deleteById(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findReservationFilter(Integer pageNum, Integer pageSize, MapContext mapContext) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(mapContext);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		PaginatedList<Reservation> list = this.reservationService.selectByFilter(filter);
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	public RequestResult findAllUserId() {
		return ResultFactory.generateRequestResult(this.reservationService.findAllByUserid(WebUtils.getCurrUserId()));
	}

	@Override
	public Long findAllAmount() {
		return this.reservationService.findAllAmount();
	}
}
