package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * @author：wangmingyuan
 * @create：2018/8/10 16:21
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OrderGoodsApiPathMatcher implements IApiPathPermissionMatcher {
	private Pattern detailsPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/orderGoods/{0}/details",WebConstant.REG_ID_MATCH));
	private Pattern orderGoodsPattern = Pattern.compile(LwxfStringUtils.format("/api/orderGoods(/{0})?",WebConstant.REG_ID_MATCH));
	private Pattern mdStatusPattern = Pattern.compile(LwxfStringUtils.format("/api/orderGoods/{0}/status/\\d{1,2}",WebConstant.REG_ID_MATCH));
	/**
	 * /api/orderGoods
	 * /api/orderGoods
	 * /api/orderGoods/{id}/status/{status}
	 * /api/users/0/orderGoods/{ogid}/details
	 * @param request
	 * @param action
	 * @param servletPath
	 * @param referer
	 * @return
	 */
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = detailsPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		String perm;
		// 会员中心的请求
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		matcher=mdStatusPattern.matcher(servletPath);
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			if(action.equals(WebConstant.REQUEST_ACTION_UPDATE)){
				perm = PermissionIndex.PERMISSION_ORDERGOODS_PREFIX.concat(String.valueOf(PermissionIndex.OrderGoodsPermssion.MD.getIndex()));
				if(ShiroUtil.isPermitted(perm)){
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}else{
				return WebConstant.STRING_EMPTY;
			}
			return WebConstant.STRING_404;
		}
		matcher=orderGoodsPattern.matcher(servletPath);
		if (matcher.matches())
		{
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			if(action.equals(WebConstant.REQUEST_ACTION_CREATE)){
				perm = PermissionIndex.PERMISSION_ORDERGOODS_PREFIX.concat(String.valueOf(PermissionIndex.OrderGoodsPermssion.ADD.getIndex()));
			}else if(action.equals(WebConstant.REQUEST_ACTION_UPDATE)){
				perm = PermissionIndex.PERMISSION_ORDERGOODS_PREFIX.concat(String.valueOf(PermissionIndex.OrderGoodsPermssion.MD.getIndex()));
			}else { // 删除
				perm = PermissionIndex.PERMISSION_ORDERGOODS_PREFIX.concat(String.valueOf(PermissionIndex.OrderGoodsPermssion.DELETE.getIndex()));
			}
			if(ShiroUtil.isPermitted(perm)){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		return null;
	}
}
