package com.lwxf.newstore.webapp.facade.clerk;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

import java.util.List;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-03 18:37
 * @desc
 **/
public interface ClerkFacade extends BaseFacade {
    /**
     * 修改role为普通会员
     * @param id
     * @return
     */
    RequestResult updateUserState(String id);

    /**
     * 获取所有的未禁用的角色非普通会员的集合
     * @param
     * @return
     */
    RequestResult findClerkListByStateAndRole();

    /**
     * 获取所有的未禁用的角色非普通会员的集合
     * @return
     */
    RequestResult findAllClerks();

    /**
     * 如果传递过来的角色值是店长，则修改其他的所有店长为店员，如果是修改店员，则将其改为店员
     * @param id
     * @return
     */
    RequestResult updateUser(String id,Integer roleValue);
}
