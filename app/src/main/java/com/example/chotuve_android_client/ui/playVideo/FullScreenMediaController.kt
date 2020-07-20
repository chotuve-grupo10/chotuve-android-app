package com.example.chotuve_android_client.ui.playVideo

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import com.example.chotuve_android_client.models.VideoListInner


class FullScreenMediaController(
    context: Context?,
    val video: VideoListInner
) :
    MediaController(context) {
    private var fullScreen: ImageButton? = null
    private var isFullScreen: String? = null
    override fun setAnchorView(view: View) {
        super.setAnchorView(view)

        //image button for full screen to be added to media controller
        fullScreen = ImageButton(super.getContext())
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.RIGHT
        params.rightMargin = 80
        addView(fullScreen, params)

        //fullscreen indicator from intent
        /*isFullScreen = (context as Activity).intent.getStringExtra("fullScreenInd")
        if ("y" == isFullScreen) {
            fullScreen!!.setImageResource(R.drawable.ic_fullscreen_exit)
        } else {
            fullScreen!!.setImageResource(R.drawable.ic_fullscreen)
        }*/

        //add listener to image button to handle full screen and exit full screen events
        fullScreen!!.setOnClickListener {view ->
            val intent = Intent(view.context, PlayVideoActivity::class.java)
            intent.putExtra("video_to_play", video)
            if ("y" == isFullScreen) {
                intent.putExtra("fullScreenInd", "")
            } else {
                intent.putExtra("fullScreenInd", "y")
            }
            view.context.startActivity(intent)
        }
    }
}
