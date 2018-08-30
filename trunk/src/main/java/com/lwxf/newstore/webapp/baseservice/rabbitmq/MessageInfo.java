package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *      MQ消息数据
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:48:43
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class MessageInfo{

    private List<Object> add = new ArrayList();// 新增数组成员

    private List<Object> update = new ArrayList();// 修改数组成员

    private List<Object> delete = new ArrayList();// 删除数组成员

    private Object extra;//扩展

    public List<Object> getAdd() {
        return add;
    }

    public void setAdd(List<Object> add) {
        this.add = add;
    }

    public List<Object> getUpdate() {
        return update;
    }

    public void setUpdate(List<Object> update) {
        this.update = update;
    }

    public List<Object> getDelete() {
        return delete;
    }

    public void setDelete(List<Object> delete) {
        this.delete = delete;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "add=" + add +
                ", update=" + update +
                ", delete=" + delete +
                ", extra=" + extra +
                '}';
    }
}
