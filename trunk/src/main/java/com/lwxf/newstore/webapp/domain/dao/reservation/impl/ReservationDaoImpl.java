package com.lwxf.newstore.webapp.domain.dao.reservation.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.reservation.ReservationDao;
import com.lwxf.newstore.webapp.domain.entity.reservation.Reservation;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-14 11:35:08
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("reservationDao")
public class ReservationDaoImpl extends BaseDaoImpl<Reservation, String> implements ReservationDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Reservation> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<Reservation> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public List<Reservation> findAllByUserid(String userId){
		String sqlId = this.getNamedSqlId("findAllByUserid");
		return this.getSqlSession().selectList(sqlId, userId);
	}

	@Override
	public Long findAllAmount() {
		String sqlId = this.getNamedSqlId("findAllAmount");
		return this.getSqlSession().selectOne(sqlId);
	}
}