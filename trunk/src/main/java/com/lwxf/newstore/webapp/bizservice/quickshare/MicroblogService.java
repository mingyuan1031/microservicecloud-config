package com.lwxf.newstore.webapp.bizservice.quickshare;


import java.util.List;
import java.util.Map;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:24
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MicroblogService extends BaseService <Microblog, String> {

	PaginatedList<Microblog> selectByFilter(PaginatedFilter paginatedFilter);

	PaginatedList<Microblog> findByType(PaginatedFilter paginatedFilter);

	int deleteByIdAndUserId(Map<String,String> map);

	Integer findCountByStatus();

	PaginatedList<Microblog> findMicroblogByMemberId(PaginatedFilter paginatedFilter);


}