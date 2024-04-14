package com.mycompany.app;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result() {}

    public Result(String errorCode, String errorMsg) {
        this(errorCode,errorMsg,null);
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static class ResultBuild<T> {
        private Result<T> expResult = new Result();

        public ResultBuild errorCode(String code) {
            expResult.setCode(code);
            return this;
        }

        public ResultBuild errorMsg(String msg) {
            expResult.setMsg(msg);
            return this;
        }

        public ResultBuild data(T data) {
            expResult.setData(data);
            return this;
        }

        public Result<T> build() {
            return this.expResult;
        }
    }
}
