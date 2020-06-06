package com.example.chotuve_android_client.services

import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.RetrofitObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UploadVideoService {

    private val retrofit : Retrofit = RetrofitObject.retrofit
    private val uploadVideoService = retrofit.create(DefaultApi::class.java)

    fun uploadVideo(
        url : String,
        disposable: CompositeDisposable?,
        onSuccess: (serverStatus: UploadVideoResult?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        val video : Video = Video (
            "Título hardcodeado",
            url,
            "usuario_hardcodeado"
            )
        disposable?.add(uploadVideoService.apiUploadVideoPost(video)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(onSuccess, onError))
    }

}