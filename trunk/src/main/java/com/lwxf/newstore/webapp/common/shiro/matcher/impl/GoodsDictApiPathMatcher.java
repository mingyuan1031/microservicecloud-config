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
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：商品字典操作权限处理
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-08 11:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsDictApiPathMatcher implements IApiPathPermissionMatcher {
	/*
	 * 品牌api
	 * /api/brands/{id}/files
	 * /api/brands
	 * /api/brands/{id}
	 * /api/brands
	 *
	 * 标签api
	 * /api/tags // 添加、查询列表
	 * /api/tags/{tagId} // 修改、删除、查询
	 *
	 * 商品分类api
	 * /api/goodstypes
	 * /api/goodstypes/{id}
	 * /api/goodstypes/all
	 *
	 * 商品规格api
	 * /api/goodstype/{goodsTypeId}/specs
	 * /api/goodstype/{goodsTypeId}/specs/{id}
	 *
	 * 商品规格选项api
	 * /api/goodstype/{goodsTypeId}/spec/{specId}/options
	 * /api/goodstype/{goodsTypeId}/spec/{specId}/options/{id}
	 *
	 */
	private String brandsAndTagsAndgoodstypesRegTemplate = "/api/(brands(/{0}(/files)?)?|tags(/{0})?|goodstypes(/({0}(/specs(/{0}(/options(/{0})?)?)?)?|all|options))?)";
	private Pattern brandsAndTagsAndgoodstypesApiPattern = Pattern.compile(LwxfStringUtils.format(brandsAndTagsAndgoodstypesRegTemplate,WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		boolean isRead = WebConstant.REQUEST_ACTION_READ.equals(action);

		// 基础字典权限处理
		Matcher matcher = brandsAndTagsAndgoodstypesApiPattern.matcher(servletPath);
		User currUser = WebUtils.getCurrUser();
		int userRole = currUser.getRole().intValue();
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			// 普通会员没有权限
			if(userRole == UserRole.MEMBER.getValue()){
				return WebConstant.STRING_404;
			}
			// 店主具有全权
			if(userRole == UserRole.SHOPKEEPER.getValue()){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			String perm;
			if(WebConstant.REQUEST_ACTION_CREATE.equals(action)){
				perm = PermissionIndex.PERMISSION_GOODS_DICT_PREFIX.concat(String.valueOf(PermissionIndex.GoodsDictPermission.ADD.getIndex()));
			}else if(WebConstant.REQUEST_ACTION_UPDATE.equals(action)){
				perm = PermissionIndex.PERMISSION_GOODS_DICT_PREFIX.concat(String.valueOf(PermissionIndex.GoodsDictPermission.MD.getIndex()));
			}else{
				perm = PermissionIndex.PERMISSION_GOODS_DICT_PREFIX.concat(String.valueOf(PermissionIndex.GoodsDictPermission.DELETE.getIndex()));
			}
			if(ShiroUtil.isPermitted(perm)){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_EMPTY;
		}
		return null;
	}
}
