package com.example.chotuve_android_client.ui.userVideos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.VideoAdapter
import kotlinx.android.synthetic.main.activity_user_videos.*

class UserVideosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_videos)

        val userVideosViewModel = UserVideosViewModel()

        val userEmail : String? = intent.getStringExtra("user_email")
        val userFullName : String? = intent.getStringExtra("user_full_name")

        setActivityTitle(userEmail.toString())

        userVideosViewModel.getVideosFromSpecificUser(userEmail.toString())
        userVideosViewModel.videos_list.observe(this, Observer { videos->
            recyclerview_user_videos.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter =
                    VideoAdapter(
                        videos
                    )
            }
        })
    }

    fun setActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }


}