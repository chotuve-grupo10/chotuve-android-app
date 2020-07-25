package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.*
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PasswordRecoveryService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val passwordRecoveryService = retrofit.create(DefaultApi::class.java)

    fun sendTokenForPasswordRecovery(
        email: String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: ForgotPasswordSuccessfulResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(passwordRecoveryService.apiUsersUserEmailResetPasswordTokenPost(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

    fun newPassword(
        email: String,
        new_password : String,
        token : String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: ResetPasswordSuccessfulResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(passwordRecoveryService.apiUsersUserEmailPasswordPut(email, ResetPasswordBody(new_password, token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }


}