package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.model.Video
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.lang.Exception

// TODO: env var this
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";


class PlayVideoViewModel (private val video: Video) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        this.value = video.title
    }
    val text: LiveData<String> = _text
    private lateinit var storageReference: StorageReference

    val absolutPath = downLoadVideo(video)
    val _filePath = MutableLiveData<String>().apply {
        this.value = absolutPath
    }
    val filePath : LiveData<String> = _filePath

    init {
        Log.d("playvid", "Soy el PlayVideoViewModel y el video es este "

        + video.title + ". La URL es esta" + video.url)
    }


        // Se la estoy asignando a un videoView que no existe. Cómo hago que esto sea mutable?
        // Claro que esto no funcionó
//        videoView.setVideoPath(absolutPath)
//        _videoView.value = videoView

    private fun downLoadVideo(video: Video) : String {
        val firebaseInstance = FirebaseStorage.getInstance(APPSPOT_URL);
        storageReference = firebaseInstance.getReferenceFromUrl(video.url);
        val localFile = File.createTempFile("video", ".mp4")
        // Esta opcion es para descargar el video en la carpeta Downloads del celu
        //val localFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "video.mp4")
        val task = storageReference.getFile(localFile)
        task.addOnSuccessListener {
            // Local temp file has been created
            Log.d("playvid","Video downloaded!");
            Log.d("playvid","path: " + localFile.absolutePath)

        }.addOnFailureListener {
            // Handle any errors
            Log.e("playvid","Error downloading video");
        }
        return localFile.absolutePath
    }
}
