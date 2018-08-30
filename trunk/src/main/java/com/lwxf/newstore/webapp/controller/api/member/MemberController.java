package com.lwxf.newstore.webapp.controller.api.member;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.user.UserFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/26 11:24
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/members", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	@GetMapping
	public RequestResult getUsers(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
								  @RequestParam(required = false,defaultValue = "50") Integer pageSize,
								  @RequestParam(required = false)Integer state, @RequestParam(required = false) String keywords){
		return this.userFacade.findUserByFilter(pageNum,pageSize,this.createParamsForFindList(state,keywords));
	}
	@PutMapping("/{uid}/state/{state}")
	public  RequestResult updateUserState(@PathVariable("uid") String uid,@PathVariable("state") Integer state)
	{
		if (!UserState.contains(state.byteValue())||StringUtils.isBlank(uid))
		{
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ILLEGAL_ARGUMENT_00005,"参数错误");
		}
		MapContext update=MapContext.newOne();
		update.put(WebConstant.KEY_ENTITY_STATE,state);
		return  this.userFacade.updateUserState(uid,update);
	}

	private MapContext createParamsForFindList(Integer state,String keywords){
		MapContext params = MapContext.newOne();

		if(LwxfStringUtils.isNotBlank(keywords)){
			params.put(WebConstant.KEY_ENTITY_KEYWORDS,keywords);
		}
		if(state != null){
			params.put(WebConstant.KEY_ENTITY_STATE,state);
		}
		return params;
	}
}
