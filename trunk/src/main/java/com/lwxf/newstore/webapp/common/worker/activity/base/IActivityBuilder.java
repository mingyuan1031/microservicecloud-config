package com.lwxf.newstore.webapp.common.worker.activity.base;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.SQLType;

/**
 * 功能：
 *      生成组织活动日志
 * @author：zhaozhenyi(zhenyi.zhao@ihydt.com)
 * @create：2017-05-15 10:14:28
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
public interface IActivityBuilder {

    /**
     * 通过传入参数，生成日志实体对象
     * @param tsManagerEntity
     * @param sqlType
     * @return
     */
    Object build(TSManagerEntity tsManagerEntity, SQLType sqlType);

    /**
     * 当线程中存在埋点数据时调用此方法
     * 生成日志实体对象，
     * @return
     */
    Object build(TSManualData tsManualData);
}
