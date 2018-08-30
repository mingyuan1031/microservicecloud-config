package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;

/**
 * 功能：快享权限验证
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-11 9:11
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class QuickShareApiPathMatcher implements IApiPathPermissionMatcher {
	/**
	 * /api/microblogs
	 * /api/microblogs/{blogId}
	 *
	 * /api/microblogs/files
	 * /api/microblogs/files/{fileId}
	 *
	 * /api/microblogs/{blogId}/comments
	 * /api/microblogs/{blogId}/comments/{commetId}
	 *
	 * /api/microblogs/{blogId}/praises
	 *
	 * /api/users/0/microblogs
	 */
	private Pattern memberPattern = Pattern.compile("/api/users/0/microblogs");
	private Pattern blogsPattern = Pattern.compile(LwxfStringUtils.format("/api/microblogs(/{0})?",WebConstant.REG_ID_MATCH));
	private Pattern commentAndPraisePattern = Pattern.compile(LwxfStringUtils.format("/api/microblogs/{0}/(comments(/{0})?|praises|files(/{0})?)",WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = memberPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		String perm;
		// 会员中心的请求
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}


		// 快享帖子的操作
		matcher = blogsPattern.matcher(servletPath);
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			if(action.equals(WebConstant.REQUEST_ACTION_CREATE)){
				perm = PermissionIndex.PERMISSION_QUICKSHARE_PREFIX.concat(String.valueOf(PermissionIndex.MicroblogPermission.ADD.getIndex()));
			}else if(action.equals(WebConstant.REQUEST_ACTION_UPDATE)){
				perm = PermissionIndex.PERMISSION_QUICKSHARE_PREFIX.concat(String.valueOf(PermissionIndex.MicroblogPermission.MD.getIndex()));
			}else { // 删除
				perm = PermissionIndex.PERMISSION_QUICKSHARE_PREFIX.concat(String.valueOf(PermissionIndex.MicroblogPermission.DELETE.getIndex()));
			}
			if(ShiroUtil.isPermitted(perm)){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}

		// 评论及点赞操作
		matcher = commentAndPraisePattern.matcher(servletPath);
		if(matcher.matches()){
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			int permIndex = this.getPermIndexForCommentPraiseFile(action,servletPath);
			if(permIndex == -1){
				return WebConstant.STRING_404;
			}
			if(ShiroUtil.isPermitted(PermissionIndex.PERMISSION_QUICKSHARE_PREFIX.concat(String.valueOf(permIndex)))){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		return null;
	}

	private int getPermIndexForCommentPraiseFile(String action,String servletPath){
		int resFlag ;
		if(servletPath.indexOf("/comments")>0){
			resFlag =0;
		}else if(servletPath.indexOf("/praises")>0){
			resFlag = 1;
		}else {
			resFlag = 2;
		}

		int permIndex;
		if(action.equals(WebConstant.REQUEST_ACTION_CREATE)){
			if(resFlag==0){
				permIndex = PermissionIndex.MicroblogPermission.ADD_COMMENT.getIndex();
			}else if(resFlag == 1){
				permIndex = PermissionIndex.MicroblogPermission.ADD_PRAISE.getIndex();
			}else{
				permIndex = PermissionIndex.MicroblogPermission.ADD_FILE.getIndex();
			}
		}else if(action.equals(WebConstant.REQUEST_ACTION_UPDATE)){
			if(resFlag==0){
				permIndex = PermissionIndex.MicroblogPermission.MD_COMMENT.getIndex();
			}else {
				permIndex = -1;
			}
		}else { // 删除
			if(resFlag==0){
				permIndex = PermissionIndex.MicroblogPermission.DELETE_COMMENT.getIndex();
			}else if(resFlag == 1){
				permIndex = PermissionIndex.MicroblogPermission.DELETE_PRAISE.getIndex();
			}else{
				permIndex = PermissionIndex.MicroblogPermission.DELETE_FILE.getIndex();
			}
		}
		return permIndex;
	}
}
