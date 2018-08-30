package com.lwxf.newstore.webapp.facade.order;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/30 9:25
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface LogisticsFacade extends BaseFacade {
	RequestResult createLogistics(Logistics logistics,String[] ids,String orderId);
	RequestResult deleteLogistics(String id);
	RequestResult updateLogistics(String id,MapContext context);
	RequestResult findLogisticsById(String id);
}
