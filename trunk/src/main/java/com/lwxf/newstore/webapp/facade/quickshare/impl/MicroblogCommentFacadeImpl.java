package com.lwxf.newstore.webapp.facade.quickshare.impl;

import sun.rmi.runtime.Log;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.annotation.Id;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogCommentService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogCommentFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/9 9:46
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component(value = "microblogCommentFacade")
public class MicroblogCommentFacadeImpl extends BaseFacadeImpl implements MicroblogCommentFacade  {
	@Resource(name = "microblogCommentService")
	private MicroblogCommentService microblogCommentService;
	@Resource(name = "microblogService")
	private MicroblogService microblogService;
	@Resource(name ="userService" )
	private UserService userService;

	/**
	 * 添加评论
	 * @param microblogComment
	 * @param blogId
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult addMicroblogComment(MicroblogComment microblogComment, String blogId) {
		microblogComment.setMicroblogId(blogId);
		microblogComment.setCreated(DateUtil.getSystemDate());
		microblogComment.setCreator(WebUtils.getCurrUserId());
		microblogComment.setDisabled(true);
		RequestResult result = microblogComment.validateFields();
		if(null != result){
			return result;
		}
		this.microblogCommentService.add(microblogComment);
		return ResultFactory.generateRequestResult(microblogComment);
	}


	/**
	 * 删除快享帖子评论
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult delMicroblogCommentById(String id, String commentId) {
		User user = WebUtils.getCurrUser();
		MicroblogComment comment = this.microblogCommentService.findById(commentId);
		if(null == comment){
			return ResultFactory.generateSuccessResult();
		}
		if(!comment.getMicroblogId().equals(id)){
			return ResultFactory.generateResNotFoundResult();
		}
		if(user.getRole().intValue() == UserRole.MEMBER.getValue()){
			if(!comment.getCreator().equals(WebUtils.getCurrUserId())){
				 return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
			}
		}
		this.microblogCommentService.updateByParentId(commentId);
		this.microblogCommentService.deleteById(commentId);
		return ResultFactory.generateSuccessResult();
	}
}
