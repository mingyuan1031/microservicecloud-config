package com.lwxf.newstore.webapp.common.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:52:38
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LocalVars {
	private LwxfShiroRealm shiroRealm;
	private SessionDAO sessionDAO;

	LwxfShiroRealm getShiroRealm() {
		if (this.shiroRealm == null) {
			this.shiroRealm = (LwxfShiroRealm) SpringContextUtil.getBean("shiroRealm");
		}
		return this.shiroRealm;
	}

	SessionDAO getSessionDAO() {
		if (sessionDAO == null) {
			DefaultSecurityManager sm = (DefaultSecurityManager) SpringContextUtil.getBean("securityManager");
			sessionDAO = ((DefaultSessionManager) sm.getSessionManager()).getSessionDAO();
		}
		return sessionDAO;
	}
}