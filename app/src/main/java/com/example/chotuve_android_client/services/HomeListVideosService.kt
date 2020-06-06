package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class HomeListVideosService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val homeListVideosService = retrofit.create(DefaultApi::class.java)

    fun listVideos(
        disposable: CompositeDisposable?,
        onSuccess: (listVideos: VideoList?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(homeListVideosService.apiListVideosGet()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(onSuccess, onError))
    }

}