package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.AcceptFriend
import com.example.chotuve_android_client.models.BasicServerResponse
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class RespondFriendshipService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val respondFriendshipService = retrofit.create(DefaultApi::class.java)

    fun respondFriendshipRequest(
        user_email : String,
        new_friends_email : String,
        responseBody : AcceptFriend,
        disposable: CompositeDisposable?,
        onSuccess: (users: BasicServerResponse?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(respondFriendshipService.apiUsersUserEmailFriendsNewFriendsEmailPatch
                (user_email,
                 new_friends_email,
                 responseBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}