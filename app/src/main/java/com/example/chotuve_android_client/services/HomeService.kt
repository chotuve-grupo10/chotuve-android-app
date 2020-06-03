package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.PingResponse
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

class HomeService {

//    private val retrofit : Retrofit = RetrofitObject.retrofit
//    private val homeService = retrofit.create(DefaultApi::class.java)
//
//    fun homeServer(
//        disposable: CompositeDisposable?,
//       onSuccess: (serverStatus: PingResponse?) -> Unit,
//       onError: (throwable: Throwable) -> Unit
//    ) {
//        disposable?.add(homeService.apiHomeGet()
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())
//        .subscribe(onSuccess, onError))
//    }

}