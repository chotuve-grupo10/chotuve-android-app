package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.BasicServerResponse
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class AddTokenService {
    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val addTokenService = retrofit.create(DefaultApi::class.java)

    fun addTokenToUser(
        user_email : String,
        token : String,
        disposable: CompositeDisposable?,
        onSuccess: (users: BasicServerResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(addTokenService.apiUsersUserEmailNotificationsTokenPut(user_email, token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))

    }
}