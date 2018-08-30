package com.lwxf.newstore.webapp.baseservice.tsmanager.base;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.common.enums.SQLType;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:41:02
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface AfterUpdateEventListener extends EventListener {

    /**
     * 更新后(会执行多次)
     * @param tsManagerEntity
     * @param sqlType
     */
    void afterUpdate(TSManagerEntity tsManagerEntity, SQLType sqlType);
}
