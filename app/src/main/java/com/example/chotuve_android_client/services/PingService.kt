package com.example.chotuve_android_client.services

import android.util.Log
import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.PingResponse
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PingService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val pingService = retrofit.create(DefaultApi::class.java)

    fun pingServer(
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: PingResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(pingService.apiPingGet()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }
}