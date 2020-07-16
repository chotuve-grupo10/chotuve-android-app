package com.example.chotuve_android_client.tools

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitObject {

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    val retrofit: Retrofit = Retrofit.Builder()
        //TODO sacar URL hardcoded (ver si se puede pasar a gradle profiles)
        .baseUrl("http://chotuve-app-server-dev.herokuapp.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}