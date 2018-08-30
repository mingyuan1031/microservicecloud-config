package com.lwxf.newstore.webapp.facade.app.impl;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.advertising.AdvertisingService;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.bizservice.config.StoreHomeNavService;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.bizservice.goods.*;
import com.lwxf.newstore.webapp.bizservice.reservation.ReservationService;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.app.AppFacade;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\7\24 0024 10:16
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
@Component("appFacade")
public class AppFacadeImpl extends BaseFacadeImpl implements AppFacade {
    @Resource(name = "brandService")
    private BrandService brandService;
    @Resource(name = "tagService")
    private TagService tagService;
    @Resource(name = "goodsTypeService")
    private GoodsTypeService goodsTypeService;
    @Resource(name = "goodsSpecService")
    private GoodsSpecService goodsSpecService;
    @Resource(name = "specOptionService")
    private SpecOptionService specOptionService;
    @Resource(name = "storeConfigService")
    private StoreConfigService storeConfigService;
    @Resource(name = "systemConfigService")
    private SystemConfigService systemConfigService;
    @Resource(name = "storeHomeNavService")
    private StoreHomeNavService storeHomeNavService;
    @Resource(name = "advertisingService")
    private AdvertisingService advertisingService;
    @Resource(name = "reservationService")
    private ReservationService reservationService;

    @Override
    public void loadMallPreloadData(Map<String, Object> preload) {
       // 品牌
        preload.put("brands",this.brandService.findAll());
       // 标签
        preload.put("tags",this.tagService.findAll());
       // 分类 规格 规格选项
        MapContext mapContext =MapContext.newOne();
        mapContext.put("goodsType",this.goodsTypeService.findall());
        mapContext.put("typeSpec",this.goodsSpecService.findAll());
        mapContext.put("specOption",this.specOptionService.findAll());
        preload.put("types",mapContext);
        //首页导航
        preload.put("storeHomeNavs",this.storeHomeNavService.findHomeNavDatas());
        //广告位
        preload.put("advertising",this.advertisingService.findAll());
        preload.put("reservations",this.reservationService.findAllAmount());
    }

    @Override
    public RequestResult loadPreloadDatas(String page) {
        Map<String, Object> prelaodDatas = new HashMap<>();
        // 加载基础配置
        Map<String, Object> appcfg = AppBeanInjector.csrfService.getAppCfg(WebUtils.getHttpServletRequest());
        prelaodDatas.put("appcfg", appcfg);
        AppBeanInjector.configuration.getSystemConfigJson(appcfg);
        Map<String, Object> prelaod =  (Map<String, Object>)AppBeanInjector.userFacade.findUserPreloadData(WebUtils.getCurrUserId()).getData();
        if (null != prelaod && prelaod.size() > 0) {
            prelaodDatas.put("preload", prelaod);
        }

        switch (page){
            case "admin":
			case "mall":
				// 加载商城的预加载数据
				this.loadMallPreloadData(prelaod);
            default:
        }

        return ResultFactory.generateRequestResult(prelaodDatas);
    }
}
