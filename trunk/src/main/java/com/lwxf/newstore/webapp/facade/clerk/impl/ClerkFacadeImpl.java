package com.lwxf.newstore.webapp.facade.clerk.impl;

import javax.annotation.Resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.clerk.ClerkFacade;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-03 18:39
 * @desc
 **/
@Component("clerkFacade")
public class ClerkFacadeImpl implements ClerkFacade {
    private static final Logger logger = LoggerFactory.getLogger(ClerkFacadeImpl.class);
    @Resource
    private UserService userService;
    @Resource
    private UserThirdInfoService userThirdInfoService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult updateUserState(String id) {
        int role = UserRole.MEMBER.getValue();
        User user = this.userService.findById(id);
        if (user==null){
            return ResultFactory.generateResNotFoundResult();
        }
        //如果是店主删除，则返回
        if(user.getRole().equals(UserRole.SHOPKEEPER.getValue())){
           return  ResultFactory.generateRequestResult(new String("店主不能删除"));
        }
        MapContext update = MapContext.newOne();
        update.put(WebConstant.KEY_ENTITY_ROLE,role);
        update.put(WebConstant.KEY_ENTITY_ID,id);
        this.userService.updateByMapContext(update);
        user.setRole(role);
        return ResultFactory.generateRequestResult(user);
    }

    @Override
    public RequestResult findClerkListByStateAndRole() {
        MapContext mapContext = MapContext.newOne();
        return ResultFactory.generateRequestResult(this.userService.findClerkListByStateAndRole(mapContext));
    }
    public RequestResult findAllClerks() {
        return ResultFactory.generateRequestResult(this.userThirdInfoService.findAllClerks());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult updateUser(String id,Integer roleValue) {
        //如果是店主的话，不做处理
        if (roleValue.equals(UserRole.SHOPKEEPER.getValue())){
            return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,"您没有设置店主的权限");
        }
        User updateUser = this.userService.findById(id);
        if (updateUser==null){
            return ResultFactory.generateResNotFoundResult();
        }

        if (updateUser.getRole().equals(UserRole.SHOPKEEPER.getValue())){
            return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,"您没有设置店主的权限");
        }

        MapContext old = MapContext.newOne();
        //如果是店长的话，先将其他的店长修改为店员
        if (roleValue.equals(UserRole.MANAGER.getValue())){
            //查到所有的店员包括店长店主的列表
            MapContext mapContext = MapContext.newOne();
            mapContext.put(WebConstant.KEY_ENTITY_ROLE,UserRole.MANAGER.getValue());
            mapContext.put(WebConstant.KEY_ENTITY_STATE,UserState.ENABLED.getValue());
            List<User> list = this.userService.findClerkListByStateAndRole(mapContext);
            if (list.size()!=0){
                for (User user:list) {
                    //如果是店长得话就改成店员
                    if (user.getRole().equals(UserRole.MANAGER.getValue())){
                        old.put(WebConstant.KEY_ENTITY_ROLE,UserRole.CLERK.getValue());
                        old.put(WebConstant.KEY_ENTITY_ID,user.getId());
                        this.userService.updateByMapContext(old);
                    }
                }
            }
        }
        MapContext update = MapContext.newOne();
        update.put(WebConstant.KEY_ENTITY_ROLE,roleValue);
        update.put(WebConstant.KEY_ENTITY_ID,id);
        this.userService.updateByMapContext(update);
        return ResultFactory.generateRequestResult(old);
    }

}

