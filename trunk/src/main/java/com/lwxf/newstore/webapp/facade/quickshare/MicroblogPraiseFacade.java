package com.lwxf.newstore.webapp.facade.quickshare;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/6 14:25
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MicroblogPraiseFacade extends BaseFacade {

	RequestResult addMicroblogPraise(String microblogId);

	RequestResult deleteMicroblogPraises(String microblogId);
}
