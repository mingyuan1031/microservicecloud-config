package com.lwxf.newstore.webapp.common.shiro;

import java.util.regex.Pattern;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:04
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class ShiroUtil {
	public static final String WILDCARD_TOKEN = "*";
	public static final String PERMISSION_PART_DIVIDER_TOKEN = ":";

	public static final String CURR_LOGIN_USER_IN_SESSION_KEY = "curr_login_user";

	private final static LocalVars lv = new LocalVars();

	public final static User getCurrUser() {
		String userId = getCurrUserId();
		if (userId == null) {
			return null;
		}
		return AppBeanInjector.userFacade.findUserById(userId);
	}

	public final static String getCurrUserId() {
		Subject subject = getSubject();
		if (null == subject) {
			return null;
		}
		PrincipalCollection pc;
		try {
			pc = subject.getPrincipals();
		} catch (Exception e) {
			return null;
		}
		if (pc == null) {
			return null;
		}
		return pc.getPrimaryPrincipal().toString();
	}

	/**
	 * 注销当前登录用户
	 */
	public final static void logoutCurrUser(){
		if(getCurrUserId() == null){
			return;
		}
		SecurityUtils.getSubject().logout();
	}

	public final static void login(String userId,String token,boolean rememberMe){
		String currUserId = getCurrUserId();
		Subject subject = SecurityUtils.getSubject();
		if(currUserId != null){
			if(currUserId.equals(userId)){
				return;
			}
			subject.logout();
		}
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userId,token);
		usernamePasswordToken.setRememberMe(rememberMe);
		subject.login(usernamePasswordToken);
		usernamePasswordToken.clear();
	}

	/**
	 * 验证当前用户的权限
	 * 禁止直接调用，请使用用ShiroPermissionService提供的相关方法
	 *
	 * @param permission
	 * @return
	 */
	public final static boolean isPermitted(String permission) {
		return getSubject().isPermitted(permission);
	}

	public final static boolean[] isPermitted(String... permissions) {
		return getSubject().isPermitted(permissions);
	}

	public static PrincipalCollection createPrincipalCollection(String userId) {
		return lv.getShiroRealm().createPrincipalCollection(userId);
	}

	public static String generateMD5(String source) {
		if(LwxfStringUtils.isBlank(source)){
			return null;
		}
		Md5Hash md5 = new Md5Hash(source);
		return md5.toString();
	}


	private static final Pattern pathPattern = Pattern.compile("/(mall|admin|storecard|quickshare|reservation|payment(/mppay)?|wxpay/notify)");
	public static boolean checkAuthPagePath(String path){
		if(null == path){
			return Boolean.FALSE;
		}
		return pathPattern.matcher(path).matches();
	}

	private static <T> String joinPermissions(T... perms) {
		StringBuilder sb = new StringBuilder();
		String sep = WebConstant.STRING_EMPTY;
		for (T perm : perms) {
			sb.append(sep).append(perm);
			if (WebConstant.STRING_EMPTY.equals(sep)) {
				sep = ",";
			}
		}
		return sb.toString();
	}

	private static Subject getSubject() {
		return ThreadContext.getSubject();
	}
}
