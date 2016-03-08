package com.mrlu.sven.util;

/**
 * Created by stefan on 16-1-13.
 */
public enum AccountTypeEnum {
    SADMIN(1), //管理员
    USER(2); //普通用户

    private int value;
    AccountTypeEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
