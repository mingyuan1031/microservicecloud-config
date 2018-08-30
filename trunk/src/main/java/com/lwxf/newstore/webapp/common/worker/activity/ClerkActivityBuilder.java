package com.lwxf.newstore.webapp.common.worker.activity;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.ClerkResEntity;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-20 10:25
 * @desc 店员管理的日志
 **/
@Component
public class ClerkActivityBuilder extends BaseActivityBuilder{
    private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
    @Resource
    private UserService userService;


    @Override
    public void registerToWorker() {
        this.systemActivityWorker.register(User.class, this);
    }
    @Override
    public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
        SystemActivity systemActivity = newSystemActivityInstance();
        Object params = tsManagerEntity.getMapperParams();
        ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
        ClerkResEntity resEntity = new ClerkResEntity();
        Map<String, Object> attrMap;
        User user;
        String userId = (String)WebUtils.getDataFromRequestMap("userId");
        String id;
        switch (sqlType) {
            case INSERT:
                user = (User) params;
                systemActivity.setEvent(SystemActivityEvent.ClERK_CREATE.getValue());
                systemActivity.setR1(user.getId());
                resEntity.setName(user.getName());
                resEntity.setRole(user.getRole());
                resEntity.setState(user.getState());
                //判断存的有没有用户id
                if(userId!=null){
                    systemActivity.setCreator(userId);
                }else{
                    systemActivity.setCreator(user.getId());
                }
                break;
            case UPDATE:
                Map<String, Object> paramsMap = (Map) params;
                id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
                if(null == id){
                    return null;
                }
                user = this.userService.findById(id);
                systemActivity.setR1(id);
                resEntity = new ClerkResEntity();
                resEntity.setName(user.getName());
                Object role = paramsMap.get("role");
                Object state = paramsMap.get("state");
                if (role != null) {
                    systemActivity.setEvent(SystemActivityEvent.CLERK_ROLE_MD.getValue());
                    Integer roleValue = Integer.valueOf(role.toString());
                    attrMap = new HashMap<>();
                    attrMap.put(WebConstant.KEY_ENTITY_ROLE,roleValue);
                    activityInfoEntity.setAttr(attrMap);
                    //判断存的有没有用户id
                    if(userId!=null){
                        systemActivity.setCreator(userId);
                    }else{
                        systemActivity.setCreator(user.getId());
                    }
                } else if (state!=null){
                    systemActivity.setEvent(SystemActivityEvent.USER_STATE_MD.getValue());
                    Integer stateValue = Integer.valueOf(state.toString());
                    attrMap = new HashMap<>();
                    attrMap.put(WebConstant.KEY_ENTITY_ROLE,stateValue);
                    activityInfoEntity.setAttr(attrMap);
                    //判断存的有没有用户id
                    if(userId!=null){
                        systemActivity.setCreator(userId);
                    }else{
                        systemActivity.setCreator(user.getId());
                    }
                }
                else{
                    systemActivity.setEvent(SystemActivityEvent.CLERK_ROLE_MD.getValue());
                    attrMap = new HashMap<>();
                    paramsMap.forEach((key,value) ->{
                        if(key.equals(WebConstant.KEY_ENTITY_ID)){
                            return;
                        }
                        attrMap.put(key,value);
                    });
                    activityInfoEntity.setAttr(attrMap);
                    //判断存的有没有用户id
                    if(userId!=null){
                        systemActivity.setCreator(userId);
                    }else{
                        systemActivity.setCreator(user.getId());
                    }
                }
                break;
            default:
                return null;
        }
        activityInfoEntity.setRes(resEntity);
        systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
        return systemActivity;
    }
}

