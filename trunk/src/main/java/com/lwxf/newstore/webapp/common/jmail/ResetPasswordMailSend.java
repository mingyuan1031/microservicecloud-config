package com.lwxf.newstore.webapp.common.jmail;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:01
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface ResetPasswordMailSend extends MailSend {
	/**
	 * 重置密码，用户激活邮件对象
	 * @param resetPass
	 */
	void setResetPass(UserResetpass resetPass);

	/**
	 * 邮箱邀请码或组织批量邀请码
	 * @param codeMap
	 */
	void setCodeMap(MapContext codeMap);

	/**
	 * 用户对象
	 * @param user
	 */
	void setUser(User user);
}
