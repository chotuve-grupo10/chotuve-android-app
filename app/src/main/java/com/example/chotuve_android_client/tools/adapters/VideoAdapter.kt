package com.example.chotuve_android_client.tools.adapters

import android.content.Intent
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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_video.view.*


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
//        holder.itemView.thumbnail.setImageDrawable()
        holder.recyclerviewVideoBinding.video = videos[position]

        val img = holder.recyclerviewVideoBinding.thumbnail
        // No funcionó con algunos links
        Picasso
            .get()
            .load("https://www.getyoutubevideothumbnail.com/Images/Icons/7.png") // https://matthewjameskirk.co.uk/Images/video.jpg
            .into(img)



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