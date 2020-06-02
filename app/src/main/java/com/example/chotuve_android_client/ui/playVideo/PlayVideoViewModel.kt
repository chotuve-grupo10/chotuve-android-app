package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.model.Video
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.lang.Exception

// TODO: env var this
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";

class PlayVideoViewModel (private val video: Video) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = video.title
    }
    val text: LiveData<String> = _text

    private lateinit var storageReference: StorageReference

    // Acá haré algo con el video.
    init {
        Log.d("playvid", "Soy el PlayVideoViewModel y el video es este " + video.title)

        // Hay que ordenar tdodo esto en la mente
//        private val videoView = VideoView()
//        val uri = Uri.parse(video.url)
//        _videoView.setVideoUri(uri)
//        // setear visibility de una progressBar acá?

        downLoadVideo(video)
    }

    private fun downLoadVideo(video: Video){
        val firebaseInstance = FirebaseStorage.getInstance(APPSPOT_URL);
        storageReference = firebaseInstance.getReferenceFromUrl(video.url);

        Log.d("playvid","URL is:" + video.url);

        val localFile = File.createTempFile("video", ".mp4")

        // Esta opcion es para descargar el video en la carpeta Downloads del celu
        //val localFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "video.mp4")

        storageReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            Log.d("playvid","Video downloaded!");
            Log.d("playvid","path: " + localFile.absolutePath);

        }.addOnFailureListener {
            // Handle any errors
            Log.e("playvid","Error downloading video");
        }
    }
}
