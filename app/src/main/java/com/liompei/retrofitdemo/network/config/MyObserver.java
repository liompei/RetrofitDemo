package com.liompei.retrofitdemo.network.config;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.liompei.retrofitdemo.network.base.HttpResult;
import com.liompei.retrofitdemo.network.listener.HttpCallback;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liompei
 * time : 2017/12/27 10:26
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:
 */

public class MyObserver<T> implements Observer<HttpResult<T>> {

    private HttpCallback<T> mHttpCallback;
    private Disposable mDisposable;
    private HttpConfig mHttpConfig;
    private ProgressDialog mProgressDialog;

    //默认不显示progress
    public MyObserver(HttpCallback<T> httpCallback) {
        mHttpCallback = httpCallback;
        mHttpConfig = new HttpConfig();
    }

    public MyObserver(HttpCallback<T> httpCallback, HttpConfig httpConfig) {
        mHttpCallback = httpCallback;
        mHttpConfig = httpConfig;
        if (null != mHttpConfig.mContext && mHttpConfig.isShowLoading) {
            mProgressDialog = new ProgressDialog(mHttpConfig.mContext);
            mProgressDialog.setMessage(mHttpConfig.getContent());
            mProgressDialog.setCancelable(mHttpConfig.isCancelable);
            mProgressDialog.setCanceledOnTouchOutside(mHttpConfig.isCanceledOnTouchOutside);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!mDisposable.isDisposed()) {
                        mDisposable.dispose();  //释放资源
                    }
                    if (!(null == mHttpConfig.getOnCancelListener())) {
                        //点击事件
                        mHttpConfig.getOnCancelListener().onCancel(dialog);
                    }
                }
            });
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        mHttpCallback.onSubscribe(d);
        if (mHttpConfig.isShowLoading && mProgressDialog != null) {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        mHttpCallback.onNext(tHttpResult);
    }

    @Override
    public void onError(Throwable e) {
        if (null != mProgressDialog) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
        mHttpCallback.onError(e);
        mHttpCallback.onFinish();
    }

    @Override
    public void onComplete() {
        if (null != mProgressDialog) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
        mHttpCallback.onFinish();
    }
}
