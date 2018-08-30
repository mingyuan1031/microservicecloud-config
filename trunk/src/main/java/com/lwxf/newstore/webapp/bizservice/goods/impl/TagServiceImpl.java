package com.lwxf.newstore.webapp.bizservice.goods.impl;


import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.goods.TagDao;
import com.lwxf.newstore.webapp.bizservice.goods.TagService;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-14 16:06:48
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("tagService")
public class TagServiceImpl extends BaseServiceImpl<Tag, String, TagDao> implements TagService {


	@Resource

	@Override	public void setDao( TagDao tagDao) {
		this.dao = tagDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Tag> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public List<Tag> findListByName(String name) {
		return this.dao.findListByName(name);
	}

	@Override
	public List<Tag> findAll() {
		return this.dao.findAll();
	}

	@Override
	public List<Tag> findListByParams(MapContext params) {
		return this.dao.findListByParams(params);
	}

	@Override
	public PaginatedList<Tag> findListByParamsForPaging(PaginatedFilter paginatedFilter) {
		return this.dao.findListByParamsForPaging(paginatedFilter);
	}

	@Override
	public int deleteById(String id) {
		return this.dao.deleteById(id);
	}
}