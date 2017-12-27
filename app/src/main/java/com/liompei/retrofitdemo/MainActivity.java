package com.liompei.retrofitdemo;

import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HttpRequest.getInstance().getLogin(this, "", "", new HttpCallback<UserBean>() {
//            @Override
//            public void onNext(HttpResult<UserBean> httpResult) {
//
//            }
//        });
    }
}
