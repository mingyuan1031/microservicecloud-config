package com.lwxf.newstore.webapp.controller.path;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.commons.collections.map.HashedMap;

import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.app.AppFacade;

/**
 * 功能：商城页面访问的路径Controller
 *
 * @author：zhangerpeng
 * @create：2018\7\24 0024 14:36
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
@Controller
public class MallController extends LoadBaseCfgAndSysCfgController {
    private static final String PAGE_MALL = "mall";
    @Resource(name = "appFacade")
    private AppFacade appFacade;

    @Override
    protected Map<String, Object> loadUserPreload(HttpServletRequest request) {
        String userId = WebUtils.getCurrUserId();
        if (null == userId) {
            return new HashMap<>();
        }
        // 返回预加载数据
        Map<String, Object> userPreloadData = (Map<String, Object>) AppBeanInjector.userFacade.findUserPreloadData(userId).getData();
        // 加载商城的预加载数据
        this.appFacade.loadMallPreloadData(userPreloadData);
        return userPreloadData;
    }

    /**
     * 商城首页
     *
     * @return
     */
    @GetMapping("/mall")
    public String goMallHome() {
        noCahce();
        return WebUtils.getPortalsPagePath(PAGE_MALL);
    }
}
