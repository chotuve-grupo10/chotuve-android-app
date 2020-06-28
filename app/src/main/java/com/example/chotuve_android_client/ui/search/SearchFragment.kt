package com.example.chotuve_android_client.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val textView: TextView = root.findViewById(R.id.text_search)
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val goAndSearchUsers : Button = root.findViewById<Button>(R.id.button_search_users)
        goAndSearchUsers.setOnClickListener {
            val searchUsersBar = root.findViewById<TextInputEditText>(R.id.search_users_bar)
            searchViewModel.updateText(searchUsersBar.text)
            searchViewModel.getUsersWithFilter(searchUsersBar.text.toString())
        }

        return root
    }
}
