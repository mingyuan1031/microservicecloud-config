package com.lwxf.newstore.webapp.baseservice.tsmanager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;

/**
 * 功能：手动处理事务拦截时的数据对象
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:35:18
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class TSManualData {
    private static final Logger logger = LoggerFactory.getLogger(TSManualData.class);
    private static final String LOGGER_MESSAGE_TEMPLATE = " ********* 正在设置非法的埋点事件，请检查代码：event = {}";
    /**
     * 当线程中存在WebConstant.TSMANAGER_MANUAL_ACTION_FLAG为key的TSManualData实体时,
     * beforeUpdate和afterUpdate 只会执行一次
     */
    private boolean preExecuted;//默认false,表示更新前事件未执行 (false 未处理 true已处理 )
    private boolean postExecuted;//默认false,表示更新后事件未执行 (false 未处理 true已处理 )
    // 默认false,表示该操作是否在提交前执行（因有些埋点数据是在执行过程中才生成的，如果第一个update触发此时获取，一般不需要处理）
    private boolean beforeCommitExecuted;
    private String event;//事件
    private Class clazz;//操作实体class
    /**
     * 与TSManagerEntity中的mapperParams保持一致
     */
    private Object params;//操作参数
    private Map<String, Object> datas = new HashMap();//扩展参数

    //构造
    private TSManualData() {

    }

    public static TSManualData newOne() {
        return new TSManualData();
    }

    public boolean getPreExecuted() {
        return preExecuted;
    }

    public void setPreExecuted(boolean preExecuted) {
        this.preExecuted = preExecuted;
    }

    public boolean getPostExecuted() {
        return postExecuted;
    }

    public void setPostExecuted(boolean postExecuted) {
        this.postExecuted = postExecuted;
    }

    public Class getClazz() {
        Assert.isTrue(clazz != null, "class 不能为空");
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public String getEvent() {
        Assert.isTrue(event != null, "event 不能为空");
        return event;
    }

    public void setEvent(String event) {
		// TODO：等非法的埋点事件处理完后，出现非法事件时要加断言 - 2017-10-27 任中山
        if(!SystemActivityEvent.validEvent(event)){
            logger.error(LOGGER_MESSAGE_TEMPLATE,event);
        }
        this.event = event;
    }

    public Object get(String key) {
        return datas.get(key);
    }

    public void put(String key, Object data) {
        if(datas.containsKey(key)){
            Assert.isTrue(false, "重复填加key" + key);
        }
        this.datas.put(key, data);
    }

    //为了处理批量邀请逻辑改变参数
    public void remove(String key) {
        this.datas.remove(key);
    }

    public boolean getBeforeCommitExecuted() {
        return beforeCommitExecuted;
    }

    public void setBeforeCommitExecuted(boolean beforeCommitExecuted) {
        this.beforeCommitExecuted = beforeCommitExecuted;
    }
}
