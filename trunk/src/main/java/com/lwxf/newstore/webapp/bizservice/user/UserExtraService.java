package com.lwxf.newstore.webapp.bizservice.user;


import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;

/**
 * 用户扩展信息：密码、盐
 */
public interface UserExtraService {
	PaginatedList<UserExtra> selectByFilter(PaginatedFilter paginatedFilter);

	/**
	 * 新增密码
	 *
	 * @param userExtra
	 * @return
	 */
	int add(UserExtra userExtra);
	/**
	 * 查询用户扩展信息
	 *
	 * @param userId
	 * @return
	 */
	UserExtra findById(String userId);

	/**
	 * 更新用户扩展信息
	 * @param mapContext
	 * @return
	 */
	int updateByMapContext(MapContext mapContext);

	/**
	 * 验证用户密码是否正确
	 * @param userId
	 * @param inputPassword
	 * @return
	 */
	Boolean isPasswordCorrect(String userId, String inputPassword);

	/**
	 * 更新用户密码
	 *
	 * @param userExtra
	 * @return
	 */
	int updateUserExtra(UserExtra userExtra);
}