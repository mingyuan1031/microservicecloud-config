package com.lwxf.newstore.webapp.common.shiro;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.userService;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:13:44
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
/**
 * 用户权限信息存储在缓存中，以userID+orgId 为key存储并设置过期时间。 当用户注销时，并不清理相关缓存。
 * 这样做的原因：
 * 	1. 清理缓存需要记住缓存了哪些
 * 	2. 避免在用户多点同时操作或者注销再登录造成的数据库压力
 */
public class LwxfShiroRealm extends AuthorizingRealm {
	public static final String HASH_ALGORITHM = "SHA-256";
	public static final int HASH_INTERATIONS = 1024;
	private static String KEY_TEMPLATE = "{0}_{1}";
	private static String session_perms = "o_perms_session";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String userId = (String) principalCollection.getPrimaryPrincipal();
		Collection<String> permissions = AppBeanInjector.userFacade.getUserHasPermissionsById(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
		return info;
	}

	@Override
	@Transactional(readOnly = true)
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String userId = token.getUsername();
		User user = userService.findById(userId);

		if (user == null) {
			return null;
		}
		UserExtra extra = AppBeanInjector.userFacade.findUserExtraByUserId(user.getId());
		String password = new SimpleHash(HASH_ALGORITHM, token.getPassword(), extra.getSalt(), HASH_INTERATIONS)
				.toBase64();
		return new SimpleAuthenticationInfo(user.getId(), ByteSource.Util.bytes(Base64.decode(password)),
				ByteSource.Util.bytes(extra.getSalt()), getName());
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.自定义角色解释器
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 清除用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String userId) {
		clearCachedAuthorizationInfo(this.createPrincipalCollection(userId));
	}

	public PrincipalCollection createPrincipalCollection(String userId) {
		return new SimplePrincipalCollection(userId, getName());
	}

	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		return  new StringBuilder().append(principals.getPrimaryPrincipal().toString()).toString();
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.onLogout(principals);
	}

	/*@Override
	protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
		Collection<Permission> perms = getPermissions(info);
		if (perms != null && !perms.isEmpty()) {
			for (Permission perm : perms) {
				if (perm.implies(permission)) {
					return true;
				}
			}
		}
		return false;
	}*/
}
