package com.example.chotuve_android_client.services

import android.util.Log
import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.LoginResponse
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class LoginService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val loginService = retrofit.create(DefaultApi::class.java)

    fun login(
        email: String,
        password: String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: LoginResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(loginService.apiLoginPost(UserLogin(email, password))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

    fun login(
        firebaseToken: String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: LoginResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        Log.d("LoginService", "A punto de hacer login con firebase token $firebaseToken")
        disposable?.add(loginService.apiLoginWithFirebasePost(firebaseToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }
}