package com.example.chotuve_android_client.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.Video
import kotlinx.android.synthetic.main.recyclerview_video.view.*

// Esto es tremendo. Para que suceda, el xml se tiene que llamar recyclerview_LOQUEQUIERAS
// La clase Kotlin esa te la genera sola
import com.example.chotuve_android_client.databinding.RecyclerviewVideoBinding


class VideoAdapter(
    val videos : List<Video>
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        // Este crea el VideoViewHolder
        return VideoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_video,
                parent,
                false
            )
        )
//        return VideoViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.recyclerview_video, parent, false)
//        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.recyclerviewVideoBinding.video = videos[position]
//        val video = videos[position]
//        holder.view.video_url.text = video.url

    }

    override fun getItemCount() =  videos.size

    // Acá va el layout row_video.. esa es la view
    inner class VideoViewHolder(
        val recyclerviewVideoBinding: RecyclerviewVideoBinding
    ) : RecyclerView.ViewHolder(recyclerviewVideoBinding.root)

}