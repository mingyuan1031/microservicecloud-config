package com.lwxf.newstore.webapp.bizservice.company.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.company.CompanyDao;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.domain.entity.company.Company;


/**
 * 功能：
 * 
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-06-29 19:07:20
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl<Company, String, CompanyDao> implements CompanyService {


	@Resource

	@Override	public void setDao( CompanyDao companyDao) {
		this.dao = companyDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Company> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<Company> findAll() {
		return this.dao.findAll();
	}
}