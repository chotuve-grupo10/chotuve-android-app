package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class DislikeVideoService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val dislikeVideoService = retrofit.create(DefaultApi::class.java)

    fun dislikeVideo(
        video_id : String,
        user_token : String,
        disposable: CompositeDisposable?,
        onSuccess: (listVideos: Video?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(dislikeVideoService.apiVideosVideoIdDislikesPost(video_id, user_token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}