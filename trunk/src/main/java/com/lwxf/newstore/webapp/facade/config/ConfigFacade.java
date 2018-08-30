package com.lwxf.newstore.webapp.facade.config;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-15 16:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface ConfigFacade extends BaseFacade {
	RequestResult putSystemConfig(String cfgId, MapContext update);

	RequestResult putStoreConfig(String cfgId,MapContext update);

	RequestResult findConfigData();

	RequestResult uploadStorecfgImage(String cfgId, MultipartFile file, Integer type);

	/**
	 * 降序排列图标导航
	 * @return
	 */
	RequestResult findHomeNavDatas();

	/**
	 * 首页图标的图片修改
	 * @param cfgId
	 * @param file
	 * @return
	 */
	RequestResult putHomeNav(String cfgId, MultipartFile file);

	RequestResult updateMapContext(String navId, MapContext mapContext);

//	RequestResult postHomeNavImage(MultipartFile file, StoreHomeNav storeHomeNav);


}
