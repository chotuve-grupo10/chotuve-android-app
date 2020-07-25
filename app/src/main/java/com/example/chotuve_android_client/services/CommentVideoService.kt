package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.Comment
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CommentVideoService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val commentVideoService = retrofit.create(DefaultApi::class.java)

    fun commentVideo(
        video_id : String,
        user_token : String,
        comment : Comment,
        disposable: CompositeDisposable?,
        onSuccess: (listVideos: Video?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        disposable?.add(commentVideoService.apiVideosVideoIdCommentsPost(video_id, user_token, comment)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }
}