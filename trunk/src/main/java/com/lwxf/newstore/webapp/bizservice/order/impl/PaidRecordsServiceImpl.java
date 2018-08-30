package com.lwxf.newstore.webapp.bizservice.order.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.order.PaidRecordsDao;
import com.lwxf.newstore.webapp.bizservice.order.PaidRecordsService;
import com.lwxf.newstore.webapp.domain.dto.user.PaidDto;
import com.lwxf.newstore.webapp.domain.entity.order.PaidRecords;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 19:58:03
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("paidRecordsService")
public class PaidRecordsServiceImpl extends BaseServiceImpl<PaidRecords, String, PaidRecordsDao> implements PaidRecordsService {


	@Resource

	@Override	public void setDao( PaidRecordsDao paidRecordsDao) {
		this.dao = paidRecordsDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<PaidRecords> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<PaidDto> findByMemberId(String id) {
		return this.dao.findByMemberId(id);
	}
}