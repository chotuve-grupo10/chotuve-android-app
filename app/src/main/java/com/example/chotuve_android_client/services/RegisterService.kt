package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.RegisterResponse
import com.example.chotuve_android_client.models.UserRegister
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class RegisterService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val registerService = retrofit.create(DefaultApi::class.java)

    fun register(
        fullName : String,
        phoneNumber : String,
        email : String,
        password : String,
        profilePicture : String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: RegisterResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(registerService.apiRegisterPost(UserRegister(email, fullName, password, phoneNumber, profilePicture))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }
}