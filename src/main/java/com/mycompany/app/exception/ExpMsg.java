package com.mycompany.app.exception;

public enum ExpMsg {

    TOKEN_FAILURE("TOKEN 解析失败");

    private String expMsg;

    ExpMsg(String expMsg) {
        this.expMsg = expMsg;
    }

    public String getExpMsg() {
        return this.expMsg;
    }

}
