package com.lwxf.newstore.webapp.domain.dao.reservation;


import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-14 11:35:08
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface ReservationDao extends BaseDao<Reservation, String> {

	PaginatedList<Reservation> selectByFilter(PaginatedFilter paginatedFilter);

	List<Reservation> findAllByUserid(String userId);

	/**
	 * 获取预约总数
	 * @return
	 */
	Long findAllAmount();
}