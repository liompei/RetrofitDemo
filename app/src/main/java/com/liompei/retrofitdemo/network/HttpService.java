package com.liompei.retrofitdemo.network;

import com.liompei.retrofitdemo.network.base.HttpResult;
import com.liompei.retrofitdemo.network.base.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Liompei
 * time : 2017/12/27 10:29
 * 1137694912@qq.com
 * https://github.com/liompei
 * remark:模拟接口
 */

public interface HttpService {

    @GET("login")
    Observable<HttpResult<UserBean>> getLogin(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("login")
    Observable<HttpResult<UserBean>> postLogin(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("upload")
    Observable<HttpResult<UserBean>> upload(@Part MultipartBody.Part file,
                                            @Part("id")RequestBody id);

}
