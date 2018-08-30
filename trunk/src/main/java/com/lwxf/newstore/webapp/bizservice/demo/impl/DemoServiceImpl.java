package com.lwxf.newstore.webapp.bizservice.demo.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.demo.DemoDao;
import com.lwxf.newstore.webapp.bizservice.demo.DemoService;
import com.lwxf.newstore.webapp.domain.entity.demo.Demo;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-05 11:26:14
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo, String, DemoDao> implements DemoService {


	@Resource

	@Override	public void setDao( DemoDao demoDao) {
		this.dao = demoDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Demo> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

}