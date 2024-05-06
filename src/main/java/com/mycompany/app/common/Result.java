package com.mycompany.app.common;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    private Result() {
    }

    public Result(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, null);
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static class ResultBuild<T> {
        private Result<T> result = new Result();

        public ResultBuild errorCode(String code) {
            result.setCode(code);
            return this;
        }

        public ResultBuild errorMsg(String msg) {
            result.setMsg(msg);
            return this;
        }

        public ResultBuild data(T data) {
            result.setData(data);
            return this;
        }

        public Result<T> build() {
            return this.result;
        }
    }
}
