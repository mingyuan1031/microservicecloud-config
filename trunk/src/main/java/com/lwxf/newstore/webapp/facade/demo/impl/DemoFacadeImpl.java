package com.lwxf.newstore.webapp.facade.demo.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.bizservice.demo.DemoService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.domain.entity.demo.Demo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.demo.DemoFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-05 14:28
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("demoFacade")
public class DemoFacadeImpl extends BaseFacadeImpl implements DemoFacade {
	@Resource(name = "demoService")
	private DemoService demoService;
	@Resource(name = "uniquneCodeGenerator")
	private UniquneCodeGenerator uniquneCodeGenerator;
	@Override
	public RequestResult findDemos() {
		PaginatedFilter paginatedFilter = PaginatedFilter.newOne();
		paginatedFilter.getPagination().setPageNum(1);
		paginatedFilter.getPagination().setPageSize(20);
		PaginatedList<Demo> list = this.demoService.selectByFilter(paginatedFilter);
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	public RequestResult generateUniquneCodeTest() {
		for(int i = 0;i<20;i++){
			System.out.println("生成订单编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.ORDER_NO)));
			System.out.println("生成物流编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.LOGISTICS_NO)));
			System.out.println("生成支付记录编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.PAID_RECORDS_NO)));
		}

		AppBeanInjector.redisUtils.set(UniquneCodeGenerator.UniqueResource.PAID_RECORDS_NO.toString(),0l);

		for(int i = 0;i<20;i++){
			System.out.println("生成订单编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.ORDER_NO)));
			System.out.println("生成物流编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.LOGISTICS_NO)));
			System.out.println("生成支付记录编号：".concat(uniquneCodeGenerator.getNextNo(UniquneCodeGenerator.UniqueResource.PAID_RECORDS_NO)));
		}
		return ResultFactory.generateSuccessResult();
	}
}
