package com.example.chotuve_android_client.ui.userVideos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.VideoAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_user_videos.*

class UserVideosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_videos)

        val userVideosViewModel = UserVideosViewModel()

        val userEmail : String? = intent.getStringExtra("user_email")
        val userFullName : String? = intent.getStringExtra("user_full_name")

        setActivityTitle(userEmail.toString())
        val textView = findViewById<TextView>(R.id.textView)
        userVideosViewModel.text.observe(this, Observer { title ->
            val concat = title.toString() + userFullName.toString()
            textView.text = concat
        })

        userVideosViewModel.getUserProfilePicture(userEmail.toString())
        userVideosViewModel.URL.observe(this, Observer {
            if (it != "") {
                val profilePictureImageView = findViewById<CircleImageView>(R.id.profile_picture)
                Picasso
                    .get()
                    .load(it) // https://matthewjameskirk.co.uk/Images/video.jpg
                    .into(profilePictureImageView)
            }
        })

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

    fun setActivityTitle(title : String) {
        supportActionBar?.title = title
    }


}