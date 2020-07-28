package com.example.chotuve_android_client.ui.myUserProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.FriendsAdapter
import com.example.chotuve_android_client.tools.adapters.VideoAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user_profile.*

class VideosViewPagerFragment(val myUserProfileViewModel : MyUserProfileViewModel) : Fragment() {

    val TAG = "VideosViewPage"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_videos_view_profile, container, false)

        myUserProfileViewModel.videos.observe(viewLifecycleOwner, Observer { videos ->
            recyclerview_home_videos.also{
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter =
                    VideoAdapter(
                        videos
                    )
            }
        })
        return root
    }

}

