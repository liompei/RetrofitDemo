package com.liompei.retrofitdemo.network.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Liompei
 * time : 2017/12/27 10:10
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:基础解析类
 */

public class HttpResult<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return code==200;
    }

    @Override
    public String toString() {

        return "code: " + code + "\nmessage: " + message + "\nresult: " + result;
    }
}
