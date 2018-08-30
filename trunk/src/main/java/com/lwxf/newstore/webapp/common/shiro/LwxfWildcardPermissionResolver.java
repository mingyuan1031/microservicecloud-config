package com.lwxf.newstore.webapp.common.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:29:10
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfWildcardPermissionResolver extends WildcardPermissionResolver {
	@Override
	public Permission resolvePermission(String permissionString) {
		return new LwxfWildcarkPermission(permissionString);
	}
}
