package com.example.chotuve_android_client.ui.home

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
import com.example.chotuve_android_client.apis.DefaultApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class HomeFragment : Fragment() {

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
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        // Aca estaba el texto de fondo de Home. Lo oculte!
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.ping_button).setOnClickListener {
            val homeTextView = view.findViewById<TextView>(R.id.text_home)
            val retrofit = Retrofit.Builder()
                    //TODO sacar URL hardcoded (ver si se puede pasar a gradle profiles)
                .baseUrl("https://chotuve-app-server-production.herokuapp.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            val pingService = retrofit.create(DefaultApi::class.java)
            myCompositeDisposable?.add(pingService.apiPingGet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { serverStatus -> homeTextView.text = "App Server Status:  ${serverStatus.AppServer}" },
                    Throwable::printStackTrace  // TODO manejar error
                ))
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
