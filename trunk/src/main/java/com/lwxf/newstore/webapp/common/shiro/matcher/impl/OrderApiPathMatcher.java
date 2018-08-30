package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * @author：wangmingyuan
 * @create：2018/8/8 10:53
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OrderApiPathMatcher implements IApiPathPermissionMatcher {

	/**
	 * /api/orders
	 * /api/orders/purchase
	 * /api/orders/{oid}/status/{status}
	 * /api/orders/{id}/details
	 * /api/orders/{orderId}/mppay
	 * /api/orders/{orderId}/paidrecords
	 * /api/users/0/orders
	 */
	private Pattern memberPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/orders(/{0})?", WebConstant.REG_ID_MATCH));
	private Pattern purchasePattern = Pattern.compile(LwxfStringUtils.format("/api/orders/purchase", WebConstant.REG_ID_MATCH));
	private Pattern orderPattern = Pattern.compile(LwxfStringUtils.format("/api/orders(/{0})?", WebConstant.REG_ID_MATCH));
	private Pattern detailsPattern = Pattern.compile(LwxfStringUtils.format("/api/orders(/{0}(/(status/\\d{1,2}|details|mppay|paidrecords))?)?", WebConstant.REG_ID_MATCH));

	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {

		Matcher matcher = memberPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		// 会员中心的请求
		if (matcher.matches()) {
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		matcher = purchasePattern.matcher(servletPath);
		if (matcher.matches()) {
			if (action.equals(WebConstant.REQUEST_ACTION_CREATE)) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
		}

		int currUserRole = WebUtils.getCurrUser().getRole().intValue();
		//订单的操作
		matcher = orderPattern.matcher(servletPath);
		if (matcher.matches()) {
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
			if (currUserRole == UserRole.SHOPKEEPER.getValue()) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
			String perm;
			if (action.equals(WebConstant.REQUEST_ACTION_CREATE)) {
				return ShiroUtil.WILDCARD_TOKEN;
			} else if (action.equals(WebConstant.REQUEST_ACTION_UPDATE)) {
				if (WebUtils.getCurrUser().getRole() == UserRole.MEMBER.getValue()) {
					perm = PermissionIndex.PERMISSION_ORDER_PREFIX.concat(String.valueOf(PermissionIndex.OrderPermission.MD.getIndex()));
				} else {
					return WebConstant.STRING_404;
				}

			} else {
				perm = PermissionIndex.PERMISSION_ORDER_PREFIX.concat(String.valueOf(PermissionIndex.OrderPermission.DELETE.getIndex()));
			}
			if (ShiroUtil.isPermitted(perm)) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		//订单的二级资源操作
		matcher = detailsPattern.matcher(servletPath);
		if (matcher.matches()) {
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}

			if (currUserRole == UserRole.SHOPKEEPER.getValue()) {
				return ShiroUtil.WILDCARD_TOKEN;
			}

			int permIndex = this.getPermIndexForCommentPraiseFile(action, servletPath);
			if (permIndex == -1) {
				return WebConstant.STRING_404;
			}
			if (ShiroUtil.isPermitted(PermissionIndex.PERMISSION_ORDER_PREFIX.concat(String.valueOf(permIndex)))) {
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}

		return null;

	}

	private int getPermIndexForCommentPraiseFile(String action, String servletPath) {
		int resFlag;
		if (servletPath.indexOf("/status") > 0) {
			resFlag = 0;
		} else if (servletPath.indexOf("/details") > 0) {
			resFlag = 1;
		} else if (servletPath.indexOf("/mppay") > 0) {
			resFlag = 2;
		} else {
			//paidrecords
			resFlag = 3;
		}

		int permIndex;
		if (action.equals(WebConstant.REQUEST_ACTION_CREATE)) {
			if (resFlag == 2) {
				permIndex = PermissionIndex.OrderPermission.MEMBER_PAY.getIndex();
			} else if (resFlag == 3) {
				permIndex = PermissionIndex.OrderPermission.ADD_PAIDRECORDS.getIndex();
			} else {
				permIndex = -1;
			}
		} else if (action.equals(WebConstant.REQUEST_ACTION_UPDATE)) {
			if (resFlag == 0) {
				permIndex = PermissionIndex.OrderPermission.MD.getIndex();
			} else {
				permIndex = -1;
			}
		} else { // 删除
			permIndex = -1;
		}
		return permIndex;
	}
}
