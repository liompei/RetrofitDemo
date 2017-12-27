package com.liompei.retrofitdemo.network.listener;

import com.liompei.retrofitdemo.network.base.HttpResult;
import com.liompei.zxlog.Zx;

import io.reactivex.disposables.Disposable;

/**
 * Created by Liompei
 * time : 2017/12/27 10:08
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:
 */

public abstract class HttpCallback<T> {

    public void onSubscribe(Disposable d) {

    }

    public abstract void onNext(HttpResult<T> httpResult);

    public void onError(Throwable e) {
        Zx.d(e.getMessage());
        Zx.show(e.getMessage());
    }

    public void onFinish() {

    }

}
