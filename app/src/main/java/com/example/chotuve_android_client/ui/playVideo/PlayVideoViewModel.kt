package com.example.chotuve_android_client.ui.playVideo

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.models.Comment
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.services.DeleteDislikeVideoService
import com.example.chotuve_android_client.services.DeleteLikeVideoService
import com.example.chotuve_android_client.services.DislikeVideoService
import com.example.chotuve_android_client.services.LikeVideoService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.tools.error_handlers.ServerMessageHttpExceptionHandler
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class PlayVideoViewModel (private var video: Video) : ViewModel() {

    private val _title = MutableLiveData<String>().apply { this.value = video.title }
    val title: LiveData<String> = _title

    private val _url = MutableLiveData<String>().apply { this.value = video.url }
    val url: LiveData<String> = _url

    private val _liked_video = MutableLiveData<Boolean>().apply {
        this.value = video.likes!!.contains(TokenHolder.username)
    }
    val liked_video : LiveData<Boolean> = _liked_video

    private val _likes_number = MutableLiveData<Int>().apply {
        this.value = video.likes!!.size
    }
    val likes_number : LiveData<Int> = _likes_number

    private val _disliked_video = MutableLiveData<Boolean>().apply {
        this.value = video.dislikes!!.contains(TokenHolder.username)
    }
    val disliked_video : LiveData<Boolean> = _disliked_video

    private val _dislikes_number = MutableLiveData<Int>().apply {
        this.value = video.dislikes!!.size
    }
    val dislikes_number : LiveData<Int> = _dislikes_number

    private val _comments = MutableLiveData<List<Comment>>().apply {
        this.value = video.comments
    }
    val comments : LiveData<List<Comment>>
        get() = _comments

    val TAG = "PlayVideoVM"
    init {
        Log.d(TAG, "Soy el PlayVideoViewModel y el video es este "

        + video.title + ". La URL es esta " + video.url + ". Estos son los likes ${video.likes}")
    }

    fun updateLikesAndDislikes() {
        _liked_video.value = video.likes!!.contains(TokenHolder.username)
        _likes_number.value = video.likes!!.size
        _disliked_video.value = video.dislikes!!.contains(TokenHolder.username)
        _dislikes_number.value = video.dislikes!!.size
    }

    fun likeVideo() {
         LikeVideoService()
            .likeVideo(
                video.Id.toString(),
                TokenHolder.appServerToken,
                CompositeDisposable(),
                {
                    if (it != null) {
                        video = it
                        updateLikesAndDislikes()
                        Log.d(TAG, "Liked video and received it back ${video.title}")
                    }
                }, {
                    it.printStackTrace()
                    Log.d(TAG, "Error liking video: ${it.localizedMessage}")
//                    if(it is HttpException){
//                        val error = ServerMessageHttpExceptionHandler(it, responseKey)
//                        val ad = AlertDialog.Builder(view.context)
//                        ad.setMessage(error.message)
//                        ad.show()
//                    }
                }
            )
    }

    fun deleteLikeVideo() {
        DeleteLikeVideoService()
            .deleteLikeVideo(
                video.Id.toString(),
                TokenHolder.appServerToken,
                CompositeDisposable(),
                {
                    if (it != null) {
                        video = it
                        updateLikesAndDislikes()
                        Log.d(TAG, "Deleted like to video and received it back ${video.title}")
                    }
                },
                {
                    it.printStackTrace()
                    Log.d(TAG, "Error deleting like from video: ${it.localizedMessage}")
                }
            )
    }

    fun dislikeVideo() {
        DislikeVideoService()
            .dislikeVideo(
                video.Id.toString(),
                TokenHolder.appServerToken,
                CompositeDisposable(),
                {
                    if (it != null) {
                        video = it
                        updateLikesAndDislikes()
                        Log.d(TAG, "disliked video and received it back ${video.title}")
                    }
                },
                {
                    it.printStackTrace()
                    Log.d(TAG, "Error disliking video: ${it.localizedMessage}")
                }
            )
    }

    fun deleteDislikeVideo() {
        DeleteDislikeVideoService()
            .deleteDislikeVideo(
                video.Id.toString(),
                TokenHolder.appServerToken,
                CompositeDisposable(),
                {
                    if (it != null) {
                        video = it
                        updateLikesAndDislikes()
                        Log.d(TAG, "Deleted dislike to video and received it back ${video.title}")
                    }
                },
                {
                    it.printStackTrace()
                    Log.d(TAG, "Error deleting dislike from video: ${it.localizedMessage}")
                }
            )
    }

    fun sendComment(textComment : String?) {
        if (textComment == "") {
            return
        }
        Log.d(TAG, "Comentario es ${textComment}")
//        CommentVideoService()
//            .commentVideo(
//                video.Id.toString(),
//                textComment,
//                TokenHolder.appServerToken,
//                CompositeDisposable(),
//                {
//                    if (it != null) {
//                        video = it
//                        updateLikesAndDislikes()
//                        Log.d(TAG, "Deleted dislike to video and received it back ${video.title}")
//                    }
//                },
//                {
//                    it.printStackTrace()
//                    Log.d(TAG, "Error deleting dislike from video: ${it.localizedMessage}")
//                }
//            )
    }


}
