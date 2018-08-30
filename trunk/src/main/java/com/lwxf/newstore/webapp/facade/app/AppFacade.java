package com.lwxf.newstore.webapp.facade.app;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

import java.util.Map;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\7\24 0024 10:15
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public interface AppFacade extends BaseFacade {
    void loadMallPreloadData(Map<String,Object> preload);


    /**
     * 加载预加载数据，工前端本地测试调用
     * @param page：预加载数据所在的页面，目前的路径有
     *          1. 商城：/mall
     *          2. 预约：/reservation
     *          3. 快享：/quickshare
     *          4. 管理后台：/admin
     *          5. 登陆页面：/login
     * @return
     */
    RequestResult loadPreloadDatas(String page);
}
