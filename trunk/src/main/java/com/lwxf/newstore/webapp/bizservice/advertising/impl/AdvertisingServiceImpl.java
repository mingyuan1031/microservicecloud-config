package com.lwxf.newstore.webapp.bizservice.advertising.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.advertising.AdvertisingDao;
import com.lwxf.newstore.webapp.bizservice.advertising.AdvertisingService;
import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;


/**
 * 功能：
 * 
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-07-13 09:07:55
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("advertisingService")
public class AdvertisingServiceImpl extends BaseServiceImpl<Advertising, String, AdvertisingDao> implements AdvertisingService {


	@Resource

	@Override	public void setDao( AdvertisingDao advertisingDao) {
		this.dao = advertisingDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Advertising> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<Advertising> findAll() {
		return this.dao.findAll();
	}
}