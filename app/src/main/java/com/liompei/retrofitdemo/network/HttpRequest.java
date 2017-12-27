package com.liompei.retrofitdemo.network;

import com.liompei.retrofitdemo.network.base.HttpResult;
import com.liompei.retrofitdemo.network.base.UserBean;
import com.liompei.retrofitdemo.network.config.HttpConfig;
import com.liompei.retrofitdemo.network.config.MyObserver;
import com.liompei.retrofitdemo.network.listener.HttpCallback;
import com.liompei.zxlog.Zx;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liompei
 * time : 2017/12/27 10:44
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:请求
 */

public class HttpRequest {

    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;
    private HttpService mHttpService;

    private static HttpRequest instance;

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                instance = new HttpRequest();
            }
        }
        return instance;
    }

    //日志打印
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Zx.i(message);
        }
    });

    //默认方法
    private HttpRequest() {

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //手动创建一个OkHttpClient并设置超时时间
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mHttpService = mRetrofit.create(HttpService.class);
    }


    private void toSubscribe(RxAppCompatActivity activity, Observable observable, MyObserver observer) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<HttpResult>() {
                    @Override
                    public boolean test(HttpResult httpResult) throws Exception {
                        if (!httpResult.isSuccess()) {
                            throw new Exception(httpResult.getMessage() + ",错误码: " + httpResult.getCode());
                        }
                        return true;
                    }
                })
                .subscribe(observer);
    }


    //****************请求*********************
    public void getLogin(RxAppCompatActivity activity, String username, String password, HttpCallback<UserBean> httpCallback) {
        Observable<HttpResult<UserBean>> observable = mHttpService.getLogin(username, password);
        observable.subscribeOn(Schedulers.io());
        observable.unsubscribeOn(Schedulers.io());
        observable.compose(RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY));
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.filter(new Predicate<HttpResult<UserBean>>() {
            @Override
            public boolean test(HttpResult<UserBean> httpResult) throws Exception {
                if (!httpResult.isSuccess()) {
                    throw new Exception(httpResult.getMessage() + ",错误码: " + httpResult.getCode());
                }
                return true;
            }
        });
        observable.subscribe(new MyObserver<UserBean>(httpCallback));
    }

    public void postLogin(RxAppCompatActivity activity,String username,String password,HttpCallback<UserBean> httpCallback){
        Observable observable=mHttpService.postLogin(username, password);
        toSubscribe(activity,observable,new MyObserver(httpCallback));
    }

    public void upload(RxAppCompatActivity activity, String id, File file,HttpCallback<UserBean> httpCallback){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), id);

        Observable observable = mHttpService.upload(filePart, idBody);
        toSubscribe(activity, observable, new MyObserver(httpCallback, new HttpConfig().showLoading(activity)));
    }

}
