package com.base.vo;

/**
 * TODO Ajax 结果集封装
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/12 16:08
 * @copyright XXXX Copyright (c) 2019
 */
public class AjaxResult {
    private int retcode = 1;
    private String retmsg = "操作成功";
    private Object data;
    private int handleNumber = 1;

    public AjaxResult(int retcode, String retmsg, Object data) {
        this.retcode = retcode;
        this.retmsg = retmsg;
        this.data = data;
    }

    public AjaxResult(int retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    public AjaxResult(Object data) {
        this.retmsg = "查询成功";
        this.data = data;
    }

    public AjaxResult(int retcode) {
        this.retcode = retcode;
        this.retmsg = "操作失败";
    }

    public AjaxResult(String retmsg) {
        this.retcode = 0;
        this.retmsg = retmsg;
    }

    public AjaxResult() {
    }

    public int getRetcode() {
        return this.retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getHandleNumber() {
        return this.handleNumber;
    }

    public void setHandleNumber(int handleNumber) {
        this.handleNumber = handleNumber;
    }

    public String toString() {
        return "AjaxResult [retcode=" + this.retcode + ", retmsg=" + this.retmsg + ", data=" + this.data + "]";
    }
}
