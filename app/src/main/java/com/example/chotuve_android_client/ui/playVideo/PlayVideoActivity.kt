package com.example.chotuve_android_client.ui.playVideo

import android.widget.Button
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.TokenHolder
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        val TAG = "PlayVideoAct"
        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        if (video != null) {
            setPlayVideoActivityTitle(video.title.toString())

            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

            // prepare videoView
            val videoView : VideoView = this.findViewById(R.id.videoView)
            val mediaController = MediaController(this)

            // bind fileUrl
            Log.d(TAG, "Soy la PlayVideoActivity y estoy bindeando el filePath")
            playVideoViewModel.url.observe( this, Observer { url ->
                // Lo de abajo es la uri para un video local. Por si no funca Firebase
                // "android.resource://"+getPackageName()+"/"+R.raw.rally
                videoView.setVideoURI(Uri.parse(url))
                videoView.setMediaController(mediaController)
                videoView.requestFocus()
                videoView.start()
            })

            val like_button : Button = this.findViewById<Button>(R.id.button_like)
            playVideoViewModel.liked_video.observe( this, Observer { liked ->
                if (liked) {
                    like_button.setBackgroundColor(ContextCompat.getColor(this, R.color.like_green))
                    like_button.setOnClickListener {
                        playVideoViewModel.deleteLikeVideo()
                    }
                } else {
                    like_button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    like_button.setOnClickListener {
                        playVideoViewModel.likeVideo()
                    }
                }
                Log.d(TAG, "Liked es ${liked}")
            })

            val dislike_button : Button = this.findViewById<Button>(R.id.button_dislike)
            playVideoViewModel.disliked_video.observe( this, Observer { disliked ->
                if(disliked) {
                    dislike_button.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red))
                    dislike_button.setOnClickListener {
                        playVideoViewModel.deleteDislikeVideo()
                    }
                } else {
                    dislike_button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    dislike_button.setOnClickListener {
                        playVideoViewModel.dislikeVideo()
                    }
                }
            })

        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {

            val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
            val intent: Intent = Intent(this, PlayVideoFullScreenActivity::class.java)
            intent.putExtra("video_to_play", video)
            intent.putExtra("time", videoView.currentPosition)
            this.startActivity(intent)
            finish()
        }
    }

    fun setPlayVideoActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }
}