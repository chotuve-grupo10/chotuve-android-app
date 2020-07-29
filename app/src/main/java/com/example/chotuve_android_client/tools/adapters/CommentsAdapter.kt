package com.example.chotuve_android_client.tools.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.databinding.RecyclerviewCommentsBinding
import com.example.chotuve_android_client.models.Comment

class CommentsAdapter(
    var comments : List<Comment>
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    val TAG = "CommentsAdapter"

    inner class CommentsViewHolder(
        val recyclerviewCommentsBinding : RecyclerviewCommentsBinding
    ) : RecyclerView.ViewHolder(recyclerviewCommentsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_comments,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.recyclerviewCommentsBinding.comment = comments[position]

    }

    override fun getItemCount() = comments.size

}