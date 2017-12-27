package com.liompei.retrofitdemo.network.config;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Liompei
 * time : 2017/12/27 9:59
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:
 */

public class HttpConfig {

    public static int HTTP_TIME_OUT=3000;

    public static String BASE_URL="http://192.168.31.144:8080/gangup/";

    public boolean isShowLoading = false;  //是否显示加载动画

    public boolean isCancelable = true;  //是否允许取消,默认允许

    public boolean isCanceledOnTouchOutside = false;  //是否允许点击屏幕取消,默认不允许

    public String mContent="请稍后...";  //progress显示的文字

    public DialogInterface.OnCancelListener mOnCancelListener;  //取消事件

    public Context mContext;

    //使用默认配置项
    public HttpConfig() {
    }

    //显示progress配置项
    public HttpConfig showLoading(Context context){
        mContext=context;
        isShowLoading=true;
        return this;
    }

    public boolean isShowLoading() {
        return isShowLoading;
    }

    public void setShowLoading(boolean showLoading) {
        isShowLoading = showLoading;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public boolean isCanceledOnTouchOutside() {
        return isCanceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public DialogInterface.OnCancelListener getOnCancelListener() {
        return mOnCancelListener;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

}
