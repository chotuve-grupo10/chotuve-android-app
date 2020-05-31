package com.example.chotuve_android_client.tools

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitObject {

    val retrofit: Retrofit = Retrofit.Builder()
        //TODO sacar URL hardcoded (ver si se puede pasar a gradle profiles)
        .baseUrl("https://chotuve-app-server-production.herokuapp.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}