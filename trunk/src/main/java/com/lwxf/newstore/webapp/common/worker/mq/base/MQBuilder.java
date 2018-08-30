package com.lwxf.newstore.webapp.common.worker.mq.base;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.SQLType;

/**
 * 功能：
 *      生成消息
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:14:28
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MQBuilder {

    /**
     * 用来查询数据
     */
    void doBefore(TSManualData tsManualData);

    /**
     * 提交查询数据
     * @param tsManagerEntity
     * @param sqlType
     */
    void doBefore(TSManagerEntity tsManagerEntity, SQLType sqlType);

    /**
     * 通过传入参数，生成消息实体对象
     * @param tsManagerEntity
     * @param sqlType
     * @return
     */
    Object build(TSManagerEntity tsManagerEntity, SQLType sqlType);


    /**
     * 传入参数，生成消息实体
     * @param tsManualData
     * @return
     */
    Object build(TSManualData tsManualData);

}
