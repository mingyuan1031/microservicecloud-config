package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import java.util.Date;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.enums.MQEventEnum;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 功能：
 *      MQ消息实体
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:48:43
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class MessageEntity<T>{
    private String id;//消息id

    private String companyId;

    /**
     * 消息类型,表示消息要绑定的routingkey前缀
     * 如项目消息则为MessageScope.PRJ
     */
    private MessageScope scope;

    /**
     * 消息类型对应id,
     * 如果项目消息,此值则应为项目id
     * 配置消息类型，生成对应的routingkey
     */
    private String scopeId;

    private String event;

	@JsonProperty(value = "X-TAG")
	private String X_TAG;//前端传的uuid标识（用于判断是否是当前的操作人）

    private T data;//消息数据

    private Date created;//消息创建时间

    private Boolean notNull = true;//任务新增、任务恢复返回值需要不排除null，默认为true(即排除null)  yyh 2017.07.29


    /**
     * 实例化消息
     * @return
     */
    public static <T> MessageEntity newOne(MessageScope scope, String scopeId, MQEventEnum event, T messageInfo) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(AppBeanInjector.idGererateFactory.nextStringId());
        messageEntity.setEvent(event == null ? null : event.getValue());
        messageEntity.setScope(scope);
        messageEntity.setScopeId(scopeId);
        messageEntity.setData(messageInfo);
        messageEntity.setX_TAG(WebUtils.getRabbitMQUserTag());
        if(!AppBeanInjector.configuration.isOnFEPublic()){
            messageEntity.setCreated(DateUtil.getSystemDate());
        }
        return messageEntity;
    }

    /**
     * 实例化消息
     * @return
     */
    public static MessageEntity newOne(MessageScope scope, String scopeId){
        return newOne(scope, scopeId, null, null);
    }

    /**
     * 实例化消息
     * @return
     */
    public static MessageEntity newOne(MessageScope scope, String scopeId, MQEventEnum event) {
        return newOne(scope, scopeId, event, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public MessageScope getScope() {
        return scope;
    }

    public void setScope(MessageScope scope) {
        this.scope = scope;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

	@JsonIgnore
	public String getX_TAG() {
        return X_TAG;
    }

	public void setX_TAG(String X_TAG) {
        this.X_TAG = X_TAG;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ",companyId=" + companyId +
                ", scope=" + scope +
                ", scopeId=" + scopeId +
                ", event='" + event + '\'' +
                ", X_TAG='" + X_TAG + '\'' +
                ", data=" + data +
                ", created=" + created +
                '}';
    }
}
