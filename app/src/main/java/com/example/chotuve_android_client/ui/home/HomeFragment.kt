package com.example.chotuve_android_client.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.VideoAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var factory : HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private var myCompositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCompositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        factory = HomeViewModelFactory()
        homeViewModel =
                ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        homeViewModel.getVideosFromServer()

        homeViewModel.videos.observe(viewLifecycleOwner, Observer { videos ->
            recyclerview_home_videos.also{
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter =
                    VideoAdapter(
                        videos
                    )
            }
        })

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getVideosFromServer()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeTextView = view.findViewById<TextView>(R.id.text_home)
        homeViewModel.textHome.observe(viewLifecycleOwner, Observer {
            homeTextView.text = it
        })

        view.findViewById<Button>(R.id.ping_button).setOnClickListener {
            homeViewModel.pingHome(myCompositeDisposable);
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

}
