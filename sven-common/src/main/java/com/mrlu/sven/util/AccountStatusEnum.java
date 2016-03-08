package com.mrlu.sven.util;

/**
 * Created by stefan on 16-1-13.
 */
public enum AccountStatusEnum {
    NOTAUTH(1), //未认证
    AUTHED(2), //已认证
    BLOCK(3); //黑名单

    private int value;
    AccountStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
