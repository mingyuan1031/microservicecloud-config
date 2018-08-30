package com.lwxf.newstore.webapp.common.worker.activity.base;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-20 10:50
 * @desc
 **/

public class ClerkResEntity extends ResEntity{
    private Integer role;
    private Byte state;


    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}

