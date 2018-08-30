package com.lwxf.newstore.webapp.facade.scheme;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 14:57
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SchemePraiseFacade extends BaseFacade {

	RequestResult add(String schemeId);

	RequestResult deleteBySchemeIdAndCreator(String schemeId);
}
