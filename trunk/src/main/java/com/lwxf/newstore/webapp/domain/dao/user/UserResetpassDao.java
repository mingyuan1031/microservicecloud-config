package com.lwxf.newstore.webapp.domain.dao.user;


import java.util.List;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;


@IBatisSqlTarget
public interface UserResetpassDao extends BaseDao<UserResetpass, String> {

	List<UserResetpass> selectByFilter(MapContext filter);

	/**
	 * 根据邮箱查找
	 *
	 * @param email
	 * @return
	 */
	UserResetpass selectByEmail(String email);

	/**
	 * @param userResetpass
	 * @return
	 */
	int updateUserResetpass(UserResetpass userResetpass);
}