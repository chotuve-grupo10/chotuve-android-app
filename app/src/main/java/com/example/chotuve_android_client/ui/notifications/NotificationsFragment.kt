package com.example.chotuve_android_client.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.RequestsAdapter
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.title.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        notificationsViewModel.getRequestsFromServer()
        notificationsViewModel.users_requesting.observe(viewLifecycleOwner, Observer { users_requesting ->
            recyclerview_notifications.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter =
                    RequestsAdapter(
                        users_requesting
                    )
            }

        })

        return root
    }
}
