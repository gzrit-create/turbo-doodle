package com.accountBook.accountBook.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(){}
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static<T> Result<T> success(T data){
        return new Result<>(200,"success",data);
    }

    public static<T> Result<T> fail(String msg){
        return new Result<>(500,msg,null);
    }

    public static Result<Void> success(){
        return success(null);
    }
}
