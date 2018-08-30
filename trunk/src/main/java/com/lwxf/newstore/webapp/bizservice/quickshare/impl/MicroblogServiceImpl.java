package com.lwxf.newstore.webapp.bizservice.quickshare.impl;


import java.util.List;
import java.util.Map;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogDao;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:24
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("microblogService")
public class MicroblogServiceImpl extends BaseServiceImpl<Microblog, String, MicroblogDao> implements MicroblogService {


	@Resource(name = "microblogDao")
	@Override
	public void setDao( MicroblogDao microblogDao) {
		this.dao = microblogDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Microblog> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })



	@Override
	public PaginatedList<Microblog> findByType(PaginatedFilter paginatedFilter) {
		return this.dao.findByType(paginatedFilter);
	}

	@Override
	public int deleteByIdAndUserId(Map<String, String> map) {
		return this.dao.deleteByIdAndUserId(map);
	}

	@Override
	public Integer findCountByStatus() {
		return this.dao.findCountByStatus();
	}

	@Override
	public PaginatedList<Microblog> findMicroblogByMemberId(PaginatedFilter paginatedFilter) {
		return this.dao.findMicroblogByMemberId(paginatedFilter);
	}


}