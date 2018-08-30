package com.lwxf.newstore.webapp.baseservice.tsmanager.base;

import java.util.List;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:42:18
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface AfterCommitEventListener extends EventListener {

    /**
     * 提交后(只执行一次)
     * @param tsManagerEntitys
     */
    void afterCommit(List<TSManagerEntity> tsManagerEntitys);
}
