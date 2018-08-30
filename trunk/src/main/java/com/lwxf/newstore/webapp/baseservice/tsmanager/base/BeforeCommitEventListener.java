package com.lwxf.newstore.webapp.baseservice.tsmanager.base;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;

import java.util.List;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:41:50
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BeforeCommitEventListener extends EventListener{

    /**
     * 提交前(只执行一次)
     */
    void beforeCommit(List<TSManagerEntity> tsManagerEntitys);

}
