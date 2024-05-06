package com.mycompany.app.exception;

public enum ExpMsg {

    TOKEN_PARSE_FAILURE("Token解析失败"),
    TOKEN_GENERATION_FAILURE("Token生成失败"),
    JWK_PARSE_FAILURE("密钥文件解析失败");

    private String expMsg;

    ExpMsg(String expMsg) {
        this.expMsg = expMsg;
    }

    public String getExpMsg() {
        return this.expMsg;
    }

}
