package com.lwxf.newstore.webapp.facade.sys;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/24 10:16
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */

public interface SystemActivityFacade extends BaseFacade {
	//分页查询所有日志
	RequestResult findActivity(Integer pageNum,Integer pageSize);
	//批量删除日志
	RequestResult deleteActivityByIds(String[] ids);
}
