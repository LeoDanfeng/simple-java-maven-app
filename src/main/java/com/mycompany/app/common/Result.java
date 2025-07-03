package com.mycompany.app.common;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    private Result() {
    }


    public static class ResultBuild<T> {
        private final Result<T> result;

        public ResultBuild() {
            result = new Result<>();
        }

        public ResultBuild<T> code(String code) {
            result.setCode(code);
            return this;
        }

        public ResultBuild<T> msg(String msg) {
            result.setMsg(msg);
            return this;
        }

        public ResultBuild<T> data(T data) {
            result.setData(data);
            return this;
        }

        public Result<T> build() {
            return this.result;
        }
    }

    public static <T> Result<T> ok() {
        return Result.<T>builder().code("200").build();
    }

    public static <T> Result<T> error(String msg) {
        return Result.<T>builder().code("500").msg(msg).build();
    }

    public static <T> ResultBuild<T> builder() {
        return new ResultBuild<>();
    }
}
