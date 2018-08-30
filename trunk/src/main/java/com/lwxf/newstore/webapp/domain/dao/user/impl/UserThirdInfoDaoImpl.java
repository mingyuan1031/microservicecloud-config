package com.lwxf.newstore.webapp.domain.dao.user.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.user.UserThirdInfoDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Repository("userThirdInfoDao")
public class UserThirdInfoDaoImpl extends BaseDaoImpl<UserThirdInfo, String> implements UserThirdInfoDao {

	@Override
	public List<UserThirdInfo> selectByWxOpenId(String wxOpenId) {
		String sqlId = this.getNamedSqlId("selectByWxOpenId");
		//
		return this.getSqlSession().selectList(sqlId, wxOpenId);
	}

	@Override
	public List<String> selectUserIdsBysWxOpenId(String wxOpenId) {
		String sqlId = this.getNamedSqlId("selectUserIdsBysWxOpenId");
		return this.getSqlSession().selectList(sqlId, wxOpenId);
	}

	@Override
	public List<UserThirdInfo> selectByWxUnionId(String wxUnionId) {
		String sqlId = this.getNamedSqlId("selectByWxUnionId");
		//
		return this.getSqlSession().selectList(sqlId, wxUnionId);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<UserThirdInfo> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		//MapContext filterItems = paginatedFilter.getFilterItems();
		//
		//  过滤查询参数
		//
		PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
		PageList<UserThirdInfo> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
		return this.toPaginatedList(pageList);
	}

	@Override
	public void deleteByOpenId(String openId) {
		String sqlId = this.getNamedSqlId("deleteByOpenId");
		this.getSqlSession().delete(sqlId, openId);
	}

	@Override
	public void deleteByUserId(String userId) {
		String sqlId = this.getNamedSqlId("deleteByUserId");
		this.getSqlSession().delete(sqlId, userId);
	}

	@Override
	public List<UserThirdInfo> findAllWithNotBlankWxOpenId() {
		String sqlId = this.getNamedSqlId("findAllWithNotBlankWxOpenId");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public List<UserThirdInfo> findByUserIds(List<String> userIdList) {
		String sqlId = this.getNamedSqlId("findByUserIds");
		return this.getSqlSession().selectList(sqlId, userIdList);
	}

	@Override
	public UserThirdInfo selectByUserId(String userId) {
		String sqlId = this.getNamedSqlId("selectByUserId");
		return this.getSqlSession().selectOne(sqlId, userId);
	}

	@Override
	public void updateByWxOpenId(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("updateByWxOpenId");
		this.getSqlSession().update(sqlId, mapContext);
	}

	@Override
	public List<UserThirdInfo> findAllClerks() {
		String sqlId = this.getNamedSqlId("findAllClerks");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public UserThirdInfo selectByOpenId(String wxOpenId) {
		String sqlId = this.getNamedSqlId("selectByOpenId");
		return this.getSqlSession().selectOne(sqlId, wxOpenId);
	}
}