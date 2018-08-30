package com.lwxf.newstore.webapp.domain.dao.user.impl;


import java.util.List;

import org.springframework.stereotype.Component;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.user.UserResetpassDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;


@Component("userResetpassDao")
public class UserResetpassDaoImpl extends BaseDaoImpl<UserResetpass, String> implements UserResetpassDao {

	@Override
	public List<UserResetpass> selectByFilter(MapContext filter) {
		//
		String sqlId = this.getNamedSqlId("selectByFilter");
		//
		return this.getSqlSession().selectList(sqlId, filter);
	}

	@Override
	public UserResetpass selectByEmail(String email) {
		String sqlId = this.getNamedSqlId("selectByEmail");
		return this.getSqlSession().selectOne(sqlId, email);
	}

	@Override
	public int updateUserResetpass(UserResetpass userResetpass) {
		String sqlId = this.getNamedSqlId("updateUserResetpass");
		return this.getSqlSession().update(sqlId, userResetpass);
	}

}