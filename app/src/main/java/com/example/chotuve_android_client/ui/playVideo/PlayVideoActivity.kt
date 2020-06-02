package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.VideoView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.Video
import com.example.chotuve_android_client.ui.home.HomeViewModel
import java.io.File
import java.io.FileNotFoundException

class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        if (video != null) {
            // Log.d("playvid", "Soy la PlayVideoActivity y el video es este " + video.title)
            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

            val textView: TextView = this.findViewById(R.id.playVideoActivityTitle)
            playVideoViewModel.text.observe(this,   Observer {
                textView.text = it
            })
            val videoView : VideoView = this.findViewById(R.id.videoView)
             Log.d("playvid", "Soy la PlayVideoActivity y estoy bindeando el filePath")
            playVideoViewModel.filePath.observe ( this, Observer<String> {
                try{
                    var file : File = File(it)
                    if(file.exists()) {
                        Log.d("playvid", "OTRA VEZ soy la PlayVideoActivity y estoy bindeando el filePath")
                        videoView.setVideoURI(Uri.parse(video.url))
                        videoView.start()
                    }
                } catch(e: FileNotFoundException)
                {
                    Log.d("playvid", "ESTA VEZ soy la PlayVideoActivity y el bind del filePath fallo")
                }
            })
//
//            val filePath : String = playVideoViewModel.getFilePath()
//            videoView.setVideoPath(filePath)
////            playVideoViewModel.videoView.observe()
        }
    }

}