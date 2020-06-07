package com.example.chotuve_android_client.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.apis.DefaultApi
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.services.PingService
import com.example.chotuve_android_client.tools.VideoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

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
                it.adapter = VideoAdapter(videos)
            }
        })
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.ping_button).setOnClickListener {
            // TODO: Mutable Live Data in here
            val homeTextView = view.findViewById<TextView>(R.id.text_home)
            val pingService = PingService()
            pingService.pingServer(
                myCompositeDisposable,
                { serverStatus ->
                    homeTextView.text = "App Server Status:  ${serverStatus?.AppServer}\n" +
                            "Media Server Status: ${serverStatus?.MediaServer}\n" +
                            "Auth Server Status: ${serverStatus?.AuthServer}";
                    Log.i("App server", "App Server Status:  ${serverStatus?.AppServer}");
                    Log.i("Media Server", "Media Server Status: ${serverStatus?.MediaServer}");
                    Log.i("Auth Server", "Auth Server Status: ${serverStatus?.AuthServer}");

                },
                Throwable::printStackTrace  // TODO manejar error
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

}
