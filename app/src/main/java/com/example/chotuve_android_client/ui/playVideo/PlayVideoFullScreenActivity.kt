package com.example.chotuve_android_client.ui.playVideo

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.Video
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoFullScreenActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video_full_screen)
        val TAG = "PlayVideoActFS"
        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        val timeStamp : Int? = intent.getIntExtra("time", 0)
        if (video != null) {
            setPlayVideoActivityTitle(video.title.toString())

            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

            // prepare videoView
            var videoView : VideoView = this.findViewById(R.id.videoViewFullScreen)
            val mediaController = MediaController(this)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            supportActionBar!!.hide()

            // bind fileUrl
            Log.d(TAG, "Video ${video.title}. Bindeando el filePath en este timeStamp ${timeStamp}")
            playVideoViewModel.url.observe( this, Observer { url ->
                // Lo de abajo es la uri para un video local. Por si no funca Firebase
                // "android.resource://"+getPackageName()+"/"+R.raw.rally
                videoView.setVideoURI(Uri.parse(url))
                videoView.setMediaController(mediaController)
                videoView.requestFocus()
                if (timeStamp != null) {
                    videoView.seekTo(timeStamp)
                }
                videoView.start()
                Log.d(TAG, "Empezó el video y ésta es su posición ${videoView.currentPosition}")
            })
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            val videoView : VideoView = this.findViewById(R.id.videoViewFullScreen)
            videoView.pause()

            val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
            val intent: Intent = Intent(this, PlayVideoActivity::class.java)
            intent.putExtra("video_to_play", video)
            intent.putExtra("time", videoView.currentPosition)
            startActivity(intent)
            this.finish()
        }
    }

    fun setPlayVideoActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }
}