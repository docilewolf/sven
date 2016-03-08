package com.mrlu.sven.common;

/**
 * Created by stefan on 16-1-13.
 */
public class SvenException extends Exception {
    private static final long serialVersionUID = -3364740436550323540L;
    private Integer errCode;
    private String errDesc;

    public SvenException(Integer errCode, String errDesc) {
        super(String.valueOf(errCode));
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
}
