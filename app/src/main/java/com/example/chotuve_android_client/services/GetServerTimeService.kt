package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.ServerTime
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class GetServerTimeService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val getServerTime = retrofit.create(DefaultApi::class.java)

    fun getServerTime(
        disposable: CompositeDisposable?,
        onSuccess: (users: ServerTime?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(getServerTime.apiTimeGet()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}