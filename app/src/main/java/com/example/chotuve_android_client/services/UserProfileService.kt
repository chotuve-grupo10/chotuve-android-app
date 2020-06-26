package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UserProfileService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val userProfileService = retrofit.create(DefaultApi::class.java)

    fun listFriends(
        userId : String?,
        disposable: CompositeDisposable?,
        onSuccess: (friends: FriendsInformationList?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(userProfileService.apiUsersUserEmailInformationGet(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }
}