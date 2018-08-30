package com.lwxf.newstore.webapp.facade.reservation;

import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/6/16 11:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface ReservationFacade extends BaseFacade {

	/**
	 * 添加预约订单
	 * @param reservation
	 * @return
	 */
	RequestResult postReservation(Reservation reservation);

	/**
	 * 修改
	 * @return
	 */
	RequestResult putReservation(String reservationId, MapContext update);

	//删除预约单
	RequestResult deleteReservationById(String id);

	RequestResult findReservationFilter(Integer pageNum, Integer pageSize, MapContext mapContext);

	RequestResult findAllUserId();

	/**
	 * 获取预约总数
	 * @return
	 */
	Long findAllAmount();
}
