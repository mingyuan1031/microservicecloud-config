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
 * 功能：商品操作（含商品评论）操作权限处理
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-08 11:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsApiPathMatcher implements IApiPathPermissionMatcher {
	/*
	 * 商品api
	 * /api/goods
	 * /api/goods/home
	 * /api/goods/{id}
	 * /api/goods/{id}/info
	 * /api/goods/{id}/contents
	 *
	 * 商品图片
	 * /api/goods/{id}/files
	 * /api/goods/{goodsId}/goodsshows
	 * /api/goods/{goodsId}/goodsshows/files  // 上线轮播图
	 * /api/goods/{goodsId}/goodsshows/{id}	  // 设为主图
	 * /api/goods/{goodsId}/goodsshows/{id}	  // 删除展示图
	 *
	 * // 商品的标签设置
	 * /api/goods/{goodsId}/goodstags/{tagId} // 添加、删除
	 *
	 * 报价
	 * /api/goods/{goodsId}/goodsextends
	 * /api/goods/{goodsId}/goodsextends/{id}
	 * /api/goods/{goodsId}/goodsextends/{goodsExtendId}/files
	 *
	 * 商品评论
	 * /api/goodscomments	// 查询、添加（评论和回复）
	 * /api/goodscomments/{id}  // 设置商品评论的是否显示、删除
	 * /api/goodscomments/{id}/files  // 添加图片
	 */
	private String brandsAndTagsAndgoodstypesRegTemplate = "/api/(brands(/{0}(/files)?)?|tags(/{0})?|goodstypes(/{0}(/specs(/{0}(/options(/{0})?)?)?)?)?)";
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
