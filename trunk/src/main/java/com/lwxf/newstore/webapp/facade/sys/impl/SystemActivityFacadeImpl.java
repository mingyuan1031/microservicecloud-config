package com.lwxf.newstore.webapp.facade.sys.impl;

import com.lwxf.newstore.webapp.bizservice.sys.SystemActivityService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.sys.SystemActivityFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/24 10:17
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("systemActivityFacade")
public class SystemActivityFacadeImpl extends BaseFacadeImpl implements SystemActivityFacade {
	@Resource(name = "systemActivityService")
	private SystemActivityService systemActivityService;

	@Override
	public RequestResult findActivity(Integer pageNum,Integer pageSize) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		return ResultFactory.generateRequestResult(this.systemActivityService.selectByFilter(filter));
	}

	@Override
	@Transactional
	public RequestResult deleteActivityByIds(String[] ids) {
		this.systemActivityService.deleteByIds(ids);
		return ResultFactory.generateSuccessResult();
	}
}
