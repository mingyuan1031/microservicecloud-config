package com.lwxf.newstore.webapp.facade.user;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:54:50
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface UserResetpassFacade extends BaseFacade {
	/**
	 * 保存用户重置密码，如果存在记录更新，不存在记录添加
	 *
	 * @param email
	 * @return
	 */
	RequestResult saveResetPass(String email);

	/**
	 * 设置新密码
	 *
	 * @param code
	 * @param password
	 * @param mapContext
	 * @return
	 */
	RequestResult saveNewPass(String code, String password,MapContext mapContext);


	/**
	 * 设置新密码
	 *
	 * @param mapContext
	 * @return
	 */
	RequestResult saveNewPassword(MapContext mapContext);

	/**
	 * 根据标识查询
	 *
	 * @param id
	 * @return
	 */
	UserResetpass findById(String id);
}
