package com.lwxf.newstore.webapp.baseservice.tsmanager.base;

import javax.annotation.PostConstruct;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerContext;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:50:58
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class AbstractAutowireWorker implements EventListener {
    /**
     * 初始化进行注册
     */
    @PostConstruct
    protected void registerToContext() {
        TSManagerContext.instance.register(this);
    }

}
