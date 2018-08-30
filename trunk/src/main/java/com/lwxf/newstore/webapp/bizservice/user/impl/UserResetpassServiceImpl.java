package com.lwxf.newstore.webapp.bizservice.user.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.user.UserResetpassService;
import com.lwxf.newstore.webapp.domain.dao.user.UserResetpassDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;


@Service("userResetpassService")
public class UserResetpassServiceImpl extends BaseServiceImpl<UserResetpass, String, UserResetpassDao> implements UserResetpassService {


	@Override
	@Resource
	public void setDao(UserResetpassDao userResetpassDao) {
		this.dao = userResetpassDao;
	}


	@Override
	public List<UserResetpass> selectByFilter(MapContext filter) {
		return this.dao.selectByFilter(filter);
	}

	@Override
	public UserResetpass findByEmail(String email) {
		return this.dao.selectByEmail(email);
	}

	@Override
	public UserResetpass updateUserResetpass(UserResetpass userResetpass) {
		this.dao.updateUserResetpass(userResetpass);
		return userResetpass;
	}

}