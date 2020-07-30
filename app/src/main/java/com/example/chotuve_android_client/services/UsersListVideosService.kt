package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.tools.RetrofitObject
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UsersListVideosService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val usersListVideosService = retrofit.create(DefaultApi::class.java)

    fun listVideos(
        user_email : String,
        token : String,
        disposable: CompositeDisposable?,
        onSuccess: (listVideos: VideoList?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(usersListVideosService.apiUsersVideosGet(token, user_email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}