package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.RequestFriendshipResponse
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class RequestFriendshipService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val requestFriendshipService = retrofit.create(DefaultApi::class.java)

    fun requestFriendship(
        user_email : String,
        new_friends_email : String,
        disposable: CompositeDisposable?,
        onSuccess: (users: RequestFriendshipResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(requestFriendshipService.apiUsersUserEmailFriendsNewFriendsEmailPost(user_email, new_friends_email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}