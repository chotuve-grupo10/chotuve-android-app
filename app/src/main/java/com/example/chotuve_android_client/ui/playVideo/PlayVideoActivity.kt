package com.example.chotuve_android_client.ui.playVideo

import android.annotation.SuppressLint
import android.widget.Button
import android.content.Context
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.View.OVER_SCROLL_IF_CONTENT_SCROLLS
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.tools.adapters.CommentsAdapter
import com.example.chotuve_android_client.tools.adapters.VideoAdapter
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    @SuppressLint("ClickableViewAccessibility")
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
            var videoView : VideoView = this.findViewById(R.id.videoView)
            val mediaController = MediaController(this)

            if (isLandScape()) {

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )

                supportActionBar!!.hide()
                videoView = this.findViewById(R.id.videoViewFullScreen)
                videoView.bringToFront()
                videoView.visibility = View.VISIBLE
            } else {
                val videoViewFullScreen = this.findViewById<VideoView>(R.id.videoViewFullScreen)
                videoViewFullScreen.visibility = View.INVISIBLE
            }

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
            playVideoViewModel.likes_number.observe( this, Observer {likes ->
                like_button.text = likes.toString()
            })

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
            playVideoViewModel.dislikes_number.observe( this, Observer { dislikes ->
                dislike_button.text = dislikes.toString()
            })

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

            playVideoViewModel.comments.observe(this, Observer { comments ->
                recyclerview_comments.also {
                    it.layoutManager = LinearLayoutManager(this)
//                    it.overScrollMode = OVER_SCROLL_IF_CONTENT_SCROLLS
//                    it.setHasFixedSize(true)
                    it.adapter =
                        CommentsAdapter(
                            comments
                        )
                }
            })

        val sendCommentButton : Button = this.findViewById<Button>(R.id.button_send_comment)
        val commentEditText : EditText = this.findViewById(R.id.comment_video_edit_text)
        sendCommentButton.setOnClickListener {button ->
            playVideoViewModel.sendComment(commentEditText.text.toString())
            commentEditText.text.clear()
            hideSoftKeyboard()
        }

//            val commentEditText : EditText = this.findViewById(R.id.comment_video_edit_text)
//            commentEditText.setOnTouchListener { editText, event ->
//                editText.performClick()
//                if (InputMethodService().isInputViewShown) {
//                    Log.d(TAG,"Abriste el teclado")
//                } else {
//                    Log.d(TAG,"Esta cerrado")
//                }
//            }

        }
    }

    fun hideSoftKeyboard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), 0)
    }

    fun setPlayVideoActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }

    private fun isLandScape(): Boolean {
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay
        val rotation = display.rotation
        return (rotation == Surface.ROTATION_90
                || rotation == Surface.ROTATION_270)
    }
}