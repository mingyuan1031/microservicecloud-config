package com.lwxf.newstore.webapp.domain.dao.news.impl;


import java.util.List;
import java.util.Map;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.news.NewsImgDao;
import com.lwxf.newstore.webapp.domain.entity.news.NewsImg;


/**
 * 功能：
 * 
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-22 14:09:33
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Repository("newsImgDao")
public class NewsImgDaoImpl extends BaseDaoImpl<NewsImg, String> implements NewsImgDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<NewsImg> selectByFilter(PaginatedFilter paginatedFilter) {
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//  过滤查询参数
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<NewsImg> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public Boolean isExistByNewsIdAndName(String id, String imgName) {
		String sqlId = this.getNamedSqlId("isExistByNewsIdAndName");
		MapContext mapContext = MapContext.newOne();
		mapContext.put("id",id);
		mapContext.put("imgName",imgName);
		return this.getSqlSession().selectOne(sqlId,mapContext);
	}

	@Override
	public List<NewsImg> findByNewsId(String newsId) {
		String sqlId = this.getNamedSqlId("findByNewsId");
		return this.getSqlSession().selectList(sqlId,newsId);
	}

	@Override
	public int deleteByNewsId(String newsId) {
		String sqlId = this.getNamedSqlId("deleteByNewsId");
		return this.getSqlSession().delete(sqlId,newsId);
	}

}