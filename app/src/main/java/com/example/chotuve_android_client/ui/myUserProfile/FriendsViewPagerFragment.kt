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
import kotlinx.android.synthetic.main.fragment_user_profile.*

class FriendsViewPagerFragment(val myUserProfileViewModel : MyUserProfileViewModel) : Fragment() {

    val TAG = "FriendsViewPage"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_friends_view_profile, container, false)

        myUserProfileViewModel.friends.observe(viewLifecycleOwner, Observer { friends ->
            recyclerview_user_profile_friends.also {
                it.layoutManager = LinearLayoutManager(requireContext())
//                it.setHasFixedSize(true)
                it.adapter =
                    FriendsAdapter(
                        friends
                    )
            }
        })
        return root
    }

}