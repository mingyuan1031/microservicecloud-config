package com.lwxf.newstore.webapp.domain.dao.order;


import java.util.List;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
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
@IBatisSqlTarget
public interface PaidRecordsDao extends BaseDao<PaidRecords, String> {

	PaginatedList<PaidRecords> selectByFilter(PaginatedFilter paginatedFilter);

	List<PaidDto> findByMemberId(String id);
}