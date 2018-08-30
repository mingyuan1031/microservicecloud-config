package com.lwxf.newstore.webapp.facade.order;

import org.bouncycastle.cert.ocsp.Req;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/23 9:51
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface WeiXinPayFacade extends BaseFacade {
	RequestResult mpPay(String oId);
	RequestResult closeOrder(String orderId);
	RequestResult createPaidRecord(String orderId);
}
