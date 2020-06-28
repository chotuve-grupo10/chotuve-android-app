package com.example.chotuve_android_client.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.UserAdapter
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userProfileViewModel =
            ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val textView: TextView = root.findViewById(R.id.textView)
        userProfileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        userProfileViewModel.getFriendsFromServer()
        userProfileViewModel.friends.observe(viewLifecycleOwner, Observer { friends ->
            recyclerview_user_profile_friends.also {
                it.layoutManager = LinearLayoutManager(requireContext())
//                it.setHasFixedSize(true)
                it.adapter = UserAdapter(friends)
            }

        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}