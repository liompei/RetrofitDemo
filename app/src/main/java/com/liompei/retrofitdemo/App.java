package com.liompei.retrofitdemo;

import android.app.Application;

import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * time : 2017/12/27 10:17
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Zx.initLog("Liompei", true);
        Zx.initToast(getApplicationContext(),true);
    }
}
