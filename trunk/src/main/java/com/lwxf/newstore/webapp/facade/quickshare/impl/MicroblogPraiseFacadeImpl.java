package com.lwxf.newstore.webapp.facade.quickshare.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogCommentService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogPraiseService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogPraiseFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/6 14:26
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("microblogPraiseFacade")
public class MicroblogPraiseFacadeImpl extends BaseFacadeImpl implements MicroblogPraiseFacade {

	@Resource(name = "microblogService")
	private MicroblogService microblogService;
	@Resource(name = "microblogPraiseService")
	private MicroblogPraiseService microblogPraiseService;
	@Resource(name = "userService")
	private UserService userService;

	@Override
	@Transactional
	public RequestResult addMicroblogPraise(String microblogId) {
		if(this.microblogService.findById(microblogId).getStatus() == 0){
			return ResultFactory.generateResNotFoundResult();
		}
		MicroblogPraise microblogPraise = new MicroblogPraise();
		microblogPraise.setMicroblogId(microblogId);
		microblogPraise.setCreated(DateUtil.getSystemDate());
		microblogPraise.setMemberId(WebUtils.getCurrUserId());
		this.microblogPraiseService.add(microblogPraise);
		return ResultFactory.generateRequestResult(microblogPraise);
	}

	@Override
	@Transactional
	public RequestResult deleteMicroblogPraises(String microblogId) {
		this.microblogPraiseService.deleteByMicroblog(microblogId);
		return ResultFactory.generateSuccessResult();
	}
}
