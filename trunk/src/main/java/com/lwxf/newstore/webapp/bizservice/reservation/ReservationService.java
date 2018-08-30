package com.lwxf.newstore.webapp.bizservice.reservation;



import java.util.List;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-14 11:35:08
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface ReservationService extends BaseService <Reservation, String> {

	PaginatedList<Reservation> selectByFilter(PaginatedFilter paginatedFilter);

	int insert(Reservation reservation);

	Reservation selectById(String id);

	List<Reservation> findAllByUserid(String userId);

	/**
	 * 获取预约总数
	 * @return
	 */
	Long findAllAmount();
}