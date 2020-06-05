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

        // Acá hay que usar el factory, porque HomeViewModel ahora tiene un parametro.
        // Otra aclaración: mi repositorio mañana será un servicio y hoy es una clase que lee un json
        // le paso el contexto porque así estaba hecha y no pienso modificarlo. Obvio que esto
        // rompe el patrón al medio. Pero a futuro no estará así
        val repository = VideoRepository(this.context)
        factory = HomeViewModelFactory(repository)
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
            val homeTextView = view.findViewById<TextView>(R.id.text_home)
//            val retrofit = Retrofit.Builder()
//                    //TODO sacar URL hardcoded (ver si se puede pasar a gradle profiles)
//                .baseUrl("https://chotuve-app-server-production.herokuapp.com/")
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//            val pingService = retrofit.create(DefaultApi::class.java)
//            myCompositeDisposable?.add(pingService.apiPingGet()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({
//                        serverStatus -> homeTextView.text = "App Server Status:  ${serverStatus.AppServer}\n" +
//                                "Media Server Status: ${serverStatus.MediaServer}\n" +
//                                "Auth Server Status: ${serverStatus.AuthServer}" ;
//                        Log.i("App server", "App Server Status:  ${serverStatus.AppServer}");
//                        Log.i("Media Server", "Media Server Status: ${serverStatus.MediaServer}");
//                        Log.i("Auth Server", "Auth Server Status: ${serverStatus.AuthServer}");
//
//                    },
//                    Throwable::printStackTrace  // TODO manejar error
//                ))
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
//                .doAfterSuccess { pet ->
//                    homeTextView.text = "First pet name is " + pet.name
//                }
//                .doOnError { t -> homeTextView.text = t.message }
//                .doFinally { homeTextView.text = "Terminado" }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

}
