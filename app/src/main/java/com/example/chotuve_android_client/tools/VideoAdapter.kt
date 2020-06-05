package com.example.chotuve_android_client.tools

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.VideoList


// Esto es tremendo. Para que suceda, el xml se tiene que llamar recyclerview_LOQUEQUIERAS
// La clase Kotlin esa te la genera sola
import com.example.chotuve_android_client.databinding.RecyclerviewVideoBinding
import com.example.chotuve_android_client.ui.playVideo.PlayVideoActivity


class VideoAdapter(
    val videos : VideoList
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
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.recyclerviewVideoBinding.video = videos[position]

        // Here I can add clickListeners to different elements in coso
//        holder.recyclerviewVideoBinding.videoTitle.setOnClickListener { view ->
//            val ad = AlertDialog.Builder(view.context)
//            ad.setMessage("You clicked on " + videos[position].title)
//            ad.show()
//        }
        holder.recyclerviewVideoBinding.root.setOnClickListener { view ->
            val intent: Intent = Intent(view.context, PlayVideoActivity::class.java)
            intent.putExtra("video_to_play", videos[position])
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount() =  videos.size

    // Acá va el layout row_video.. esa es la view
    inner class VideoViewHolder(
        val recyclerviewVideoBinding: RecyclerviewVideoBinding
    ) : RecyclerView.ViewHolder(recyclerviewVideoBinding.root)

}