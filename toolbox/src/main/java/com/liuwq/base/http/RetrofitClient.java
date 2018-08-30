package com.liuwq.base.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述: 作者: su 日期: 2017/10/30 16:46
 */
public abstract class RetrofitClient {
    protected static OkHttpClient.Builder sDefOkHttpBuilder = getDefOkHttpClientBuilder(true);
    protected static Converter.Factory sConverterFactory = GsonConverterFactory.create();
    protected static CallAdapter.Factory sCallAdapterFactory = RxJava2CallAdapterFactory.create();

    protected static OkHttpClient.Builder getDefOkHttpClientBuilder(boolean logging) {
        OkHttpClient.Builder clientBuilder =
                new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS);

        if (logging) {
            // 请求、返回日志打印
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }

        return clientBuilder;
    }
}
