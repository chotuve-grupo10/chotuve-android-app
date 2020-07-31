package com.example.chotuve_android_client.tools.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chotuve_android_client.ui.userProfile.FriendsViewPagerFragment
import com.example.chotuve_android_client.ui.userProfile.MyUserProfileViewModel
import com.example.chotuve_android_client.ui.userProfile.VideosViewPagerFragment

class PageAdapter(manager : FragmentManager, val myUserProfileViewModel: MyUserProfileViewModel) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> (return VideosViewPagerFragment(
                myUserProfileViewModel
            ))
            1 -> (return FriendsViewPagerFragment(
                myUserProfileViewModel
            ))
            else -> (return VideosViewPagerFragment(
                myUserProfileViewModel
            ))
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> (return  "Mis videos")
            1 -> (return "Mis amigos")
            else -> (return "Mis videos")
        }
    }

    override fun getCount(): Int = 2

}